package edu.sjsu.openstack.service.impl;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;
import java.util.concurrent.TimeUnit;

import javax.annotation.PostConstruct;
import javax.ws.rs.NotFoundException;
import javax.xml.bind.ValidationException;

import org.apache.log4j.Logger;
import org.openstack4j.api.Builders;
import org.openstack4j.api.OSClient;
import org.openstack4j.api.exceptions.AuthenticationException;
import org.openstack4j.model.common.Identifier;
import org.openstack4j.model.compute.Action;
import org.openstack4j.model.compute.BDMDestType;
import org.openstack4j.model.compute.BDMSourceType;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Image;
import org.openstack4j.model.compute.Server;
import org.openstack4j.model.compute.ServerCreate;
import org.openstack4j.model.compute.builder.BlockDeviceMappingBuilder;
import org.openstack4j.model.compute.builder.ServerCreateBuilder;
import org.openstack4j.model.network.AttachInterfaceType;
import org.openstack4j.model.network.IPVersionType;
import org.openstack4j.model.network.Network;
import org.openstack4j.model.network.Subnet;
import org.openstack4j.openstack.OSFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import com.jcraft.jsch.Channel;
import com.jcraft.jsch.ChannelExec;
import com.jcraft.jsch.JSch;
import com.jcraft.jsch.JSchException;
import com.jcraft.jsch.Session;

import edu.sjsu.openstack.dao.NetworkConfigDao;
import edu.sjsu.openstack.dao.ProjectDao;
import edu.sjsu.openstack.dao.UserDao;
import edu.sjsu.openstack.dao.VMMetadataDao;
import edu.sjsu.openstack.model.NetworkConfig;
import edu.sjsu.openstack.model.Project;
import edu.sjsu.openstack.model.Snapshot;
import edu.sjsu.openstack.model.User;
import edu.sjsu.openstack.model.VMMetadata;
import edu.sjsu.openstack.request.CreateProjectRequest;
import edu.sjsu.openstack.request.SignUpRequest;
import edu.sjsu.openstack.response.GetProjectsResponse;
import edu.sjsu.openstack.response.GetServerListResponse;
import edu.sjsu.openstack.response.OpenStackMetrics;
import edu.sjsu.openstack.response.ProjectResponse;
import edu.sjsu.openstack.response.UserResponse;
import edu.sjsu.openstack.response.VMMetadataResponse;
import edu.sjsu.openstack.service.OpenStackAPIService;

@Service
public class OpenStackAPIServiceImpl implements OpenStackAPIService {

	private static final Logger LOG = Logger.getLogger(OpenStackAPIServiceImpl.class);

	private Map<String, String> propertiesMap = new HashMap<String, String>();

	@Autowired
	UserDao userDao;

	@Autowired
	VMMetadataDao vmMetadataDao;

	@Autowired
	NetworkConfigDao networkConfigDao;

	@Autowired
	ProjectDao projectDao;

	/**
	 * Load application.properties file
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@PostConstruct
	private void loadPropertFiles() {
		Properties properties = new Properties();
		InputStream input = this.getClass().getClassLoader().getResourceAsStream("/config/application.properties");
		if (input != null) {

			try {
				properties.load(input);
			} catch (IOException e) {
				LOG.error("I/O exception", e);
			} catch (IllegalArgumentException e) {
				LOG.error("Malfunction properties format", e);
			}

			for (String key : properties.stringPropertyNames()) {
				String value = properties.getProperty(key);
				propertiesMap.put(key, value);
			}
		} else {
			LOG.error("I/O exception");
		}
	}

	/**
	 * Get Auth token from Keystone
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private OSClient getOSAuthToken() {
		OSClient os = null;
		try {
			OSFactory.enableHttpLoggingFilter(true);
			OSFactory.enableLegacyEndpointHandling(true);
			os = OSFactory.builderV3().endpoint(propertiesMap.get("keystone.endpoint"))
					.credentials(propertiesMap.get("username"), propertiesMap.get("password"),
							Identifier.byId(propertiesMap.get("domain")))
					.scopeToProject(Identifier.byName(propertiesMap.get("username")),
							Identifier.byId(propertiesMap.get("domain")))
					.authenticate();
		} catch (AuthenticationException e) {
			LOG.error(e);
		}
		return os;
	}

	/**
	 * List all Servers
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public List<? extends Server> getNovaList() {
		List<? extends Server> computeServers = getOSAuthToken().compute().servers().list();
		return computeServers;
	}

	private String executeCommand(String command) throws JSchException, IOException {
		Session session = getSession();
		Channel channel = session.openChannel("exec");
		try {
			((ChannelExec) channel).setCommand(command);
			channel.setInputStream(null);
			((ChannelExec) channel).setErrStream(System.err);

			InputStream in = channel.getInputStream();
			channel.connect();
			byte[] tmp = new byte[1024];
			while (true) {
				while (in.available() > 0) {
					int i = in.read(tmp, 0, 1024);
					if (i < 0)
						break;
					String response = new String(tmp, 0, i);
					LOG.info(response);
					return response;
					// System.out.print(new String(tmp, 0, i));
				}
				if (channel.isClosed()) {
					throw new Error("Failed with exit-status" + channel.getExitStatus());
				}
				try {
					Thread.sleep(1000);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		} finally {
			channel.disconnect();
			session.disconnect();
		}
	}

	/**
	 * Open SSH channel
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private Session getSession() throws JSchException {
		String host = propertiesMap.get("controller.endpoint");
		String user = propertiesMap.get("controller.username");
		String password = propertiesMap.get("controller.password");
		int port = 2230;
		java.util.Properties config = new java.util.Properties();
		config.put("StrictHostKeyChecking", "no");
		JSch jsch = new JSch();
		Session session = jsch.getSession(user, host, port);
		session.setPassword(password);
		session.setConfig(config);
		session.connect();
		return session;
	}

	/**
	 * Executing environment variables to run admin commands
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private String generateOpenstackShellScript() {
		StringBuffer command = new StringBuffer();
		command.append("export OS_AUTH_URL=\"http://controller:5000/v3/\";");
		String projectId = propertiesMap.get("project.id");
		command.append("export OS_PROJECT_ID=" + projectId);
		command.append("export OS_PROJECT_NAME=\"admin\";");
		command.append("export OS_USER_DOMAIN_NAME=\"Default\";");
		command.append("export OS_USERNAME=\"admin\";");
		command.append("export OS_PASSWORD=\"admin_user_secret\";");
		command.append("export OS_REGION_NAME=\"RegionOne\";");
		command.append("export OS_INTERFACE=\"public\";");
		command.append("export OS_IDENTITY_API_VERSION=3;");
		return command.toString();
	}

	@Override
	public String createSnapshot(String serverId, String snapshotName, String serverName) {
		VMMetadata metadata = vmMetadataDao.getVMMetadataByServerName(serverName);
		String imageId = getOSAuthToken().compute().servers().createSnapshot(serverId, snapshotName);
		List<Snapshot> snapShots = metadata.getSnapShots();
		if (CollectionUtils.isEmpty(snapShots)) {
			snapShots = new ArrayList<Snapshot>();
		}
		Snapshot snapshot = new Snapshot();
		snapshot.setSnapShotName(snapshotName);
		snapshot.setImageId(imageId);
		snapShots.add(snapshot);
		metadata.setSnapShots(snapShots);
		vmMetadataDao.updateVMMetadata(metadata);
		return imageId;
	}

	@Override
	public OpenStackMetrics getDiagnosticsFromCLI() {
		OpenStackMetrics response = new OpenStackMetrics();
		String command = generateOpenstackShellScript();
		command = command + "openstack host show compute1";
		try {
			String commandResponse = executeCommand(command);
			LOG.info(commandResponse);

			String lines[] = commandResponse.split("\\r?\\n");
			String totOutput = lines[3];
			String[] totSplitString = totOutput.split("[\\|\\s]+");

			String totCpuNumb = totSplitString[3];
			String totMemoryMB = totSplitString[4];
			String totDiskGB = totSplitString[5];

			response.setTotCpuNumb(totCpuNumb);
			response.setTotDiskGB(totDiskGB);
			response.setTotMemoryMB(totMemoryMB);

			String output = lines[4];
			String[] splitString = output.split("[\\|\\s]+");

			String cpuNumb = splitString[3];
			String memoryMB = splitString[4];
			String diskGB = splitString[5];

			response.setCpuNumb(cpuNumb);
			response.setMemoryMB(memoryMB);
			response.setDiskGB(diskGB);
			response.setStatusCode(HttpStatus.OK.toString());
		} catch (Exception e) {
			e.printStackTrace();
			response.setMessage(e.getMessage());
			response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR.toString());
		}
		return response;
	}

	/**
	 * Login API
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public UserResponse login(String userName, String password) {
		UserResponse response = new UserResponse();
		User user = userDao.getUserByNameAndPassowrd(userName, password);
		if (user == null) {
			throw new UsernameNotFoundException(userName);
		}
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setDepartment(user.getDepartment());
		response.setUserName(userName);
		return response;
	}

	/**
	 * Network, Subnet and IP creation automation
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private Map<String, String> createAndAssignNetworks() {

		NetworkConfig networkConfig = networkConfigDao.getLastAssignedNetwork();
		String networkName = null;
		String subnetName = null;
		String fixedIP = null;
		String subnetIPPool = null;

		if (null == networkConfig) {
			networkName = "CMPE-283-NET-1";
			subnetName = "CMPE-283-SNET-1";
			subnetIPPool = "41.41.41";
			fixedIP = "41.41.41.2";
		} else {
			networkName = generateDynamicNetworkName(networkConfig.getNetworkName());
			subnetName = generateDynamicNetworkName(networkConfig.getSubNetName());
			subnetIPPool = generateDynamicIPPool(networkConfig.getSubnetIPPool());
			fixedIP = generateDynamicFixedIP(subnetIPPool);
		}
		Network network = createNetwork(networkName);
		createSubnet(networkName, subnetName, subnetIPPool);

		NetworkConfig newNetworkConfig = new NetworkConfig();
		newNetworkConfig.setNetworkName(networkName);
		newNetworkConfig.setSubNetName(subnetName);
		newNetworkConfig.setSubnetIPPool(subnetIPPool);
		newNetworkConfig.setFixedIP(fixedIP);
		newNetworkConfig.setCreatedTime(System.currentTimeMillis());
		networkConfigDao.insertRecord(newNetworkConfig);

		Map<String, String> networkMap = new HashMap<String, String>();
		networkMap.put("NETWORK_NAME", networkName);
		networkMap.put("NETWORK_ID", network.getId());
		networkMap.put("FIXED_IP", fixedIP);
		return networkMap;
	}

	/**
	 * Generating Fixed IP
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private String generateDynamicFixedIP(String subnetIPPool) {
		int maximum = 253;
		int minimum = 2;
		Random rn = new Random();
		int range = maximum - minimum + 1;
		Integer randomNum = rn.nextInt(range) + minimum;

		String fixedIP = subnetIPPool + "." + randomNum.toString();
		return fixedIP;
	}

	/**
	 * Generating first 3 octets of Subnet
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private String generateDynamicIPPool(String subnetIPPool) {
		String[] arr = subnetIPPool.split("\\.");
		Integer num = Integer.parseInt(arr[0]) + 1;
		String newSubnetIPPool = num.toString() + "." + arr[1] + "." + arr[2];
		return newSubnetIPPool;
	}

	/**
	 * Generating Network Name
	 * 
	 * @author Anushri Srinath Aithal
	 */
	private String generateDynamicNetworkName(String name) {
		String[] arr = name.split("-");
		Integer num = Integer.parseInt(arr[3]);
		num += 1;
		String newName = arr[0] + "-" + arr[1] + "-" + arr[2] + "-" + num.toString();
		return newName;
	}

	private void saveVMMetadata(String serverName, String networkName, String fixedIP, String userName, Project project,
			String networkId, String fIp) {
		VMMetadata vmMetadata = new VMMetadata();
		vmMetadata.setServerName(serverName);
		vmMetadata.setNetworkName(networkName);
		vmMetadata.setIpAddress(fixedIP);
		vmMetadata.setFlavorName(project.getFlavor().getName());
		vmMetadata.setUserName(userName);
		vmMetadata.setStartTime(System.currentTimeMillis());
		vmMetadata.setImageName(project.getImageName());
		vmMetadata.setCreatedTime(System.currentTimeMillis());
		vmMetadata.setProject(project);
		vmMetadata.setNetworkId(networkId);
		vmMetadata.setFixedIP(fIp);

		vmMetadataDao.insert(vmMetadata);
	}

	@Override
	public void startServer(String serverName) {
		String id = getServerId(serverName);
		VMMetadata vmMetadata = vmMetadataDao.getVMMetadataByServerName(serverName);
		getOSAuthToken().compute().servers().action(id, Action.START);
		vmMetadata.setStartTime(System.currentTimeMillis());
		vmMetadataDao.updateVMMetadata(vmMetadata);
	}

	@Override
	public void stopServer(String serverName) {
		String id = getServerId(serverName);
		VMMetadata vmMetadata = vmMetadataDao.getVMMetadataByServerName(serverName);

		getOSAuthToken().compute().servers().action(id, Action.STOP);

		Long startTime = vmMetadata.getStartTime();
		Long upTime = (System.currentTimeMillis() - startTime);
		if (vmMetadata.getUpTime() != null) {
			upTime += vmMetadata.getUpTime();
		}
		vmMetadata.setUpTime(upTime);
		vmMetadata.setStopped(true);
		vmMetadataDao.updateVMMetadata(vmMetadata);

	}

	@Override
	public void deleteServer(String projectId, String userName) {
		List<VMMetadata> vmMetadataList = vmMetadataDao.getVMMetadata(projectId, userName);
		for (VMMetadata vmMetadata : vmMetadataList) {
			String id = getServerId(vmMetadata.getServerName());
			getOSAuthToken().compute().servers().delete(id);
			deleteNetwork(vmMetadata.getNetworkName());
			vmMetadataDao.deleteVMMetadata(vmMetadata.getServerName());
		}
	}

	/**
	 * Call openstack API to create network
	 * 
	 * @author Anushri Srinath Aithal
	 */
	public Network createNetwork(String networkName) {
		Network network = getOSAuthToken().networking().network()
				.create(Builders.network().name(networkName).adminStateUp(true).build());

		return network;

	}

	/**
	 * Invoke openstack REST API to create subnet and attach to router
	 * 
	 * @author Anushri Srinath Aithal
	 */
	public void createSubnet(String networkName, String subnetName, String ipPool) {
		String id = getNetworkId(networkName);

		Subnet subnet = getOSAuthToken().networking().subnet().create(Builders.subnet().name(subnetName).networkId(id)
				// .tenantId("tenantId")
				.addPool(ipPool + ".2", ipPool + ".254").ipVersion(IPVersionType.V4).cidr(ipPool + ".0/24").build());

		String routerId = propertiesMap.get("router.id");
		getOSAuthToken().networking().router().attachInterface(routerId, AttachInterfaceType.SUBNET, subnet.getId());
	}

	public void deleteNetwork(String networkName) {
		Network network = getNetwork(networkName);
		String routerId = propertiesMap.get("router.id");
		for (String subnet : network.getSubnets()) {
			getOSAuthToken().networking().router().detachInterface(routerId, subnet, null);
		}
		getOSAuthToken().networking().network().delete(network.getId());
		networkConfigDao.deleteNetwork(networkName);
	}

	public void deleteSubnet(String subnetName) {
		String sid = getSubnetId(subnetName);
		getOSAuthToken().networking().subnet().delete(sid);

	}

	private Network getNetwork(String networkName) {
		List<? extends Network> networks = getOSAuthToken().networking().network().list();
		Network network = null;

		for (Network n : networks) {
			if (n.getName().equals(networkName)) {
				network = n;
				break;
			}
		}

		return network;
	}

	private String getNetworkId(String networkName) {
		List<? extends Network> networks = getOSAuthToken().networking().network().list();
		String id = "";

		for (Network n : networks) {
			if (n.getName().equals(networkName)) {
				id = n.getId();
				break;
			}
		}

		return id;
	}

	private String getSubnetId(String subNetName) {
		List<? extends Subnet> subNets = getOSAuthToken().networking().subnet().list();
		String sid = "";
		for (Subnet s : subNets) {
			if (s.getName().equals(subNetName)) {
				sid = s.getId();
				break;
			}
		}

		return sid;
	}

	private String getServerId(String serverName) {

		List<? extends Server> servers = getOSAuthToken().compute().servers().list();
		String sid = "";
		for (Server s : servers) {
			if (s.getName().equals(serverName)) {
				sid = s.getId();
				break;
			}
		}
		return sid;
	}

	public String getImageId(String imageName) {

		List<? extends Image> images = getOSAuthToken().compute().images().list();

		String iid = "";
		for (Image i : images) {
			if (i.getName().equals(imageName)) {
				iid = i.getId();
				break;
			}
		}
		return iid;
	}

	private Flavor getFlavorByName(String flavorName) {
		List<? extends Flavor> flavors = getOSAuthToken().compute().flavors().list();
		Flavor flavor = null;
		for (Flavor f : flavors) {
			if (f.getName().equals(flavorName)) {
				flavor = f;
				break;
			}
		}
		return flavor;
	}

	@Override
	public Integer generateBill(String projectId, String userName) {
		List<VMMetadata> vmMetadataList = vmMetadataDao.getVMMetadata(projectId, userName);
		Long totalUpTime = 0L;
		for (VMMetadata vmMetadata : vmMetadataList) {
			Long upTime = vmMetadata.getUpTime();
			if (!vmMetadata.isStopped()) {
				Long startTime = vmMetadata.getStartTime();
				upTime = (System.currentTimeMillis() - startTime);
				if (vmMetadata.getUpTime() != null) {
					upTime += vmMetadata.getUpTime();
				}
			}
			totalUpTime += upTime;
		}

		Long amount = (totalUpTime * vmMetadataList.get(0).getProject().getCostPerHour()) / TimeUnit.HOURS.toMillis(1);
		if (amount == 0L) {
			amount = Long.valueOf(vmMetadataList.get(0).getProject().getCostPerHour());
		} else if (amount < vmMetadataList.get(0).getProject().getCostPerHour()) {
			amount += Long.valueOf(vmMetadataList.get(0).getProject().getCostPerHour());
		}
		return amount.intValue();
	}

	@Override
	public UserResponse signUp(SignUpRequest request) throws ValidationException {
		User existinguser = userDao.getUserByNameAndPassowrd(request.getUsername(), request.getPassword());
		if (null != existinguser) {
			throw new ValidationException("User already exists");
		}
		User user = new User();
		user.setUserName(request.getUsername());
		user.setFirstName(request.getFirstname());
		user.setLastName(request.getLastname());
		user.setDepartment(request.getDepartment());
		user.setPassword(request.getPassword());
		userDao.signUpUser(user);
		UserResponse response = new UserResponse();
		response.setFirstName(user.getFirstName());
		response.setLastName(user.getLastName());
		response.setDepartment(user.getDepartment());
		response.setUserName(user.getUserName());
		return response;
	}

	public List<? extends Flavor> getFlavorList() {
		List<? extends Flavor> flavors = getOSAuthToken().compute().flavors().list();
		return flavors;
	}

	/**
	 * Get all server details from mongo
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public GetServerListResponse getServerList(String userName) {
		GetServerListResponse response = new GetServerListResponse();
		List<VMMetadataResponse> vmMetadataResp = new ArrayList<VMMetadataResponse>();
		response.setVmMetadata(vmMetadataResp);
		List<VMMetadata> vmMeatadataList = vmMetadataDao.getVMMetadataByUserName(userName);
		if (!CollectionUtils.isEmpty(vmMeatadataList)) {
			for (VMMetadata data : vmMeatadataList) {
				VMMetadataResponse resp = new VMMetadataResponse();
				vmMetadataResp.add(resp);

				resp.setId(data.getId());
				resp.setServerName(data.getServerName());
				resp.setImageName(data.getImageName());
				resp.setIpAddress(data.getIpAddress());
				resp.setNetworkName(data.getNetworkName());
				resp.setFlavorName(data.getFlavorName());
				Server server = getServerByName(data.getServerName());
				resp.setStatus(server.getStatus().toString());
				resp.setServerId(server.getId());
				resp.setProject(data.getProject());
				resp.setSnapShots(data.getSnapShots());
			}
		} else {
			throw new NotFoundException();
		}
		return response;
	}

	private Server getServerByName(String serverName) {

		List<? extends Server> servers = getOSAuthToken().compute().servers().list();
		Server server = null;
		for (Server s : servers) {
			if (s.getName().equals(serverName)) {
				server = s;
				break;
			}
		}
		return server;
	}

	@Override
	public Boolean createProject(CreateProjectRequest createProjectRequest) {
		Project existinguser = projectDao.getProjectByName(createProjectRequest.getProjectName());
		if (null != existinguser) {
			try {
				throw new ValidationException("Project already exists");
			} catch (ValidationException e) {
				LOG.error(e);
			}
		}
		Flavor flavor = getFlavorByName(createProjectRequest.getFlavor());

		Project project = new Project();
		project.setProjectName(createProjectRequest.getProjectName());
		project.setNoOfInstances(createProjectRequest.getNoOfInstances());
		project.setDescription(createProjectRequest.getDescription());
		project.setImageName(createProjectRequest.getImageName());
		project.setFlavor(flavor);
		project.setCostPerHour(createProjectRequest.getCostPerHour());

		projectDao.insertRecord(project);
		return true;

	}

	/**
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public GetServerListResponse getAllServersList() {
		GetServerListResponse response = new GetServerListResponse();
		List<VMMetadataResponse> vmMetadataResp = new ArrayList<VMMetadataResponse>();
		response.setVmMetadata(vmMetadataResp);
		List<VMMetadata> vmMeatadataList = vmMetadataDao.getAllVMMetadata();
		if (!CollectionUtils.isEmpty(vmMeatadataList)) {
			for (VMMetadata data : vmMeatadataList) {
				VMMetadataResponse resp = new VMMetadataResponse();
				vmMetadataResp.add(resp);

				resp.setId(data.getId());
				resp.setServerName(data.getServerName());
				resp.setImageName(data.getImageName());
				resp.setIpAddress(data.getIpAddress());
				resp.setNetworkName(data.getNetworkName());
				resp.setFlavorName(data.getFlavorName());
				Server server = getServerByName(data.getServerName());
				resp.setStatus(server.getStatus().toString());
				resp.setServerId(server.getId());
				resp.setProject(data.getProject());
				resp.setSnapShots(data.getSnapShots());
			}
		} else {
			throw new NotFoundException();
		}
		return response;
	}

	@Override
	public GetProjectsResponse getAllProjects(String userName) {
		GetProjectsResponse response = new GetProjectsResponse();
		List<ProjectResponse> projects = new ArrayList<ProjectResponse>();
		response.setProjectResponse(projects);

		List<Project> projectList = projectDao.getProjects();
		if (!CollectionUtils.isEmpty(projectList)) {
			for (Project proj : projectList) {
				ProjectResponse project = new ProjectResponse();
				project.setProjectName(proj.getProjectName());
				project.setDescription(proj.getDescription());
				project.setFlavor(proj.getFlavor());
				project.setId(proj.getId());
				project.setNoOfInstances(proj.getNoOfInstances());
				project.setImageName(proj.getImageName());
				project.setCostPerHour(proj.getCostPerHour());

				boolean projectCreated = vmMetadataDao.isProjectCreated(userName, proj.getId());
				project.setProjectCreated(projectCreated);
				projects.add(project);
			}
		} else {
			throw new NotFoundException();
		}
		return response;
	}

	/**
	 * Get server details per project
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public GetServerListResponse getProjectServerDetails(String projectId, String userName) {
		GetServerListResponse response = new GetServerListResponse();
		List<VMMetadataResponse> vmMetadataResp = new ArrayList<VMMetadataResponse>();
		response.setVmMetadata(vmMetadataResp);
		List<VMMetadata> vmMeatadataList = vmMetadataDao.getVMMetadata(projectId, userName);
		if (!CollectionUtils.isEmpty(vmMeatadataList)) {
			for (VMMetadata data : vmMeatadataList) {
				VMMetadataResponse resp = new VMMetadataResponse();
				vmMetadataResp.add(resp);

				resp.setId(data.getId());
				resp.setServerName(data.getServerName());
				resp.setImageName(data.getImageName());
				resp.setIpAddress(data.getIpAddress());
				resp.setNetworkName(data.getNetworkName());
				resp.setFlavorName(data.getFlavorName());
				Server server = getServerByName(data.getServerName());
				resp.setStatus(server.getStatus().toString());
				resp.setServerId(server.getId());
				resp.setProject(data.getProject());
				resp.setSnapShots(data.getSnapShots());
			}
		} else {
			throw new NotFoundException();
		}
		return response;
	}

	@Override
	public void createServer(String projectId, String userName) {
		Project project = projectDao.getProjectById(projectId);
		ServerCreate serverCreate = null;
		String flavorId = project.getFlavor().getId();
		String imageId = getImageId(project.getImageName());

		for (int i = 0; i < project.getNoOfInstances(); i++) {

			String serverName = userName + "_" + project.getProjectName() + "_" + i;
			ServerCreateBuilder serverCreateBuilder = Builders.server().name(serverName).flavor(flavorId).image(imageId)
					.addPersonality("/etc/motd", "Welcome to the new VM! Restricted access only");

			BlockDeviceMappingBuilder blockDeviceMappingBuilder = Builders.blockDeviceMapping().uuid(imageId)
					.sourceType(BDMSourceType.IMAGE).volumeSize(new Integer(1)).deviceName("/dev/vda").bootIndex(0)
					.destinationType(BDMDestType.LOCAL).deleteOnTermination(true);

			serverCreateBuilder = serverCreateBuilder.blockDevice(blockDeviceMappingBuilder.build());
			serverCreate = serverCreateBuilder.build();

			Map<String, String> networkMap = createAndAssignNetworks();
			String networkName = networkMap.get("NETWORK_NAME");
			String fixedIP = networkMap.get("FIXED_IP");

			String networkId = networkMap.get("NETWORK_ID");
			// Network network = getOSAuthToken().networking().network().get(networkId);
			serverCreate.addNetwork(networkId, fixedIP);

			getOSAuthToken().compute().servers().boot(serverCreate);
			saveVMMetadata(serverName, networkName, fixedIP, userName, project, networkId, fixedIP);

		}
	}

	@Override
	public void restoreSnapshot(String projectId, String userName, String snapshotName, String serverName) {

		// get old server details
		VMMetadata metadata = vmMetadataDao.getVMMetadataByServerName(serverName);

		String flavorId = getFlavorByName(metadata.getFlavorName()).getId();
		String networkName = metadata.getNetworkName();
		String fixedIP = metadata.getFixedIP();
		String networkId = metadata.getNetworkId();
		// get new image id
		String snapshotImageId = getImageId(snapshotName);

		// delete the server, not the network
		deleteServer(serverName);

		try {
			Thread.sleep(1000);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
		// create server with new details
		createServer(serverName, flavorId, snapshotImageId, networkName, fixedIP, networkId);

		// save metadata info
		metadata.setImageName(snapshotName);
		// delete and recreate data
		vmMetadataDao.deleteVMMetadata(serverName);
		vmMetadataDao.insert(metadata);

	}

	private void deleteServer(String serverName) {
		String id = getServerId(serverName);
		getOSAuthToken().compute().servers().delete(id);
	}

	private void createServer(String serverName, String flavorId, String imageId, String networkName, String fixedIP,
			String networkId) {
		ServerCreate serverCreate = null;

		ServerCreateBuilder serverCreateBuilder = Builders.server().name(serverName).flavor(flavorId).image(imageId)
				.addPersonality("/etc/motd", "Welcome to the new VM! Restricted access only");

		BlockDeviceMappingBuilder blockDeviceMappingBuilder = Builders.blockDeviceMapping().uuid(imageId)
				.sourceType(BDMSourceType.IMAGE).volumeSize(new Integer(1)).deviceName("/dev/vda").bootIndex(0)
				.destinationType(BDMDestType.LOCAL).deleteOnTermination(true);

		serverCreateBuilder = serverCreateBuilder.blockDevice(blockDeviceMappingBuilder.build());
		serverCreate = serverCreateBuilder.build();

		// Network network = getOSAuthToken().networking().network().get(networkId);
		serverCreate.addNetwork(networkId, fixedIP);

		getOSAuthToken().compute().servers().boot(serverCreate);
	}

}
