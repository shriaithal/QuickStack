package edu.sjsu.openstack.controller;

import java.util.List;

import org.apache.log4j.Logger;
import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import edu.sjsu.openstack.request.CreateProjectRequest;
import edu.sjsu.openstack.request.CreateServerProjectRequest;
import edu.sjsu.openstack.request.CreateSnapshotRequest;
import edu.sjsu.openstack.request.LoginRequest;
import edu.sjsu.openstack.request.RestoreSnapshotRequest;
import edu.sjsu.openstack.request.SignUpRequest;
import edu.sjsu.openstack.response.GenericResponse;
import edu.sjsu.openstack.response.GetProjectsResponse;
import edu.sjsu.openstack.response.GetServerListResponse;
import edu.sjsu.openstack.response.OpenStackMetrics;
import edu.sjsu.openstack.response.UserResponse;
import edu.sjsu.openstack.service.OpenStackAPIService;

@Controller
@SuppressWarnings("rawtypes")
public class OpenStackAPIController {

	private static final Logger LOG = Logger.getLogger(OpenStackAPIController.class);

	@Autowired
	OpenStackAPIService apiService;

	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String startPage(ModelMap model) {
		return "index";
	}

	@RequestMapping(value = "/user", method = RequestMethod.GET)
	public String userPage(ModelMap model) {
		return "user";
	}

	/**
	 * Method to retrieve all servers
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/nova/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<? extends Server>> getNovaList() {
		ResponseEntity<List<? extends Server>> responseEntity = null;
		try {
			List<? extends Server> response = apiService.getNovaList();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/compute/diagnostics/cli", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<OpenStackMetrics> getDiagnosticsFromCLI() {
		ResponseEntity<OpenStackMetrics> responseEntity = null;
		try {
			OpenStackMetrics response = apiService.getDiagnosticsFromCLI();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
		}
		return responseEntity;
	}

	@RequestMapping(value = "/snapshot", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GenericResponse> createSnapshot(@RequestBody CreateSnapshotRequest request) {
		ResponseEntity<GenericResponse> responseEntity = null;
		GenericResponse response = new GenericResponse();
		try {
			String msg = apiService.createSnapshot(request.getServerId(), request.getSnapshotName(),
					request.getServerName());
			response.setMessage(msg);
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<GenericResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/restore", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GenericResponse> restoreSnapshot(@RequestBody RestoreSnapshotRequest request) {
		ResponseEntity<GenericResponse> responseEntity = null;
		GenericResponse response = new GenericResponse();
		try {
			apiService.restoreSnapshot(request.getProjectId(), request.getUserName(), request.getSnapshotName(),
					request.getServerName());
			return new ResponseEntity(HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			response.setStatusCode(HttpStatus.EXPECTATION_FAILED.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Login to application
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/login/action", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserResponse> login(@RequestBody LoginRequest request) {
		ResponseEntity<UserResponse> responseEntity = null;
		UserResponse response = new UserResponse();
		try {
			response = apiService.login(request.getUserName(), request.getPassword());
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage("Invalid User Name and Password");
			responseEntity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Login to admin application
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/admin/login", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserResponse> adminLogin(@RequestBody LoginRequest request) {
		ResponseEntity<UserResponse> responseEntity = null;
		UserResponse response = new UserResponse();
		try {
			if (request.getUserName().equals("CMPE-283") && request.getPassword().equals("CMPE-283")) {
				response.setStatusCode(HttpStatus.OK.toString());
				responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
			} else {
				response.setMessage("Invalid User Name and Password");
				responseEntity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
			}
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage("Invalid User Name and Password");
			responseEntity = new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/compute/createServer/project", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity createServerFromProject(@RequestBody CreateServerProjectRequest request) {
		apiService.createServer(request.getProjectId(), request.getUserName());
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/compute/startServer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity startServer(@RequestParam String serverName) {
		apiService.startServer(serverName);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/compute/stopServer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity stopServer(@RequestParam String serverName) {
		apiService.stopServer(serverName);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/compute/deleteServer", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity deleteServer(@RequestParam String projectId, String userName) {
		apiService.deleteServer(projectId, userName);
		return new ResponseEntity(HttpStatus.OK);
	}

	@RequestMapping(value = "/generate/bill", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<Integer> generateBill(@RequestParam String projectId, String userName) {
		ResponseEntity<Integer> responseEntity = null;
		Integer response = apiService.generateBill(projectId, userName);
		responseEntity = new ResponseEntity<Integer>(response, HttpStatus.OK);
		return responseEntity;
	}

	@RequestMapping(value = "/signup", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserResponse> signup(@RequestBody SignUpRequest request) {
		ResponseEntity<UserResponse> responseEntity = null;
		UserResponse response = new UserResponse();
		try {
			response = apiService.signUp(request);
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<UserResponse>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage("User already Exists");
			responseEntity = new ResponseEntity<UserResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/compute/flavorlist", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<List<? extends Flavor>> getFlavorList() {
		ResponseEntity<List<? extends Flavor>> responseEntity = null;
		try {
			List<? extends Flavor> response = apiService.getFlavorList();
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e);
		}
		return responseEntity;
	}

	/**
	 * Get Servers per User
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/server/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GetServerListResponse> getServerList(@RequestParam String userName) {
		ResponseEntity<GetServerListResponse> responseEntity = null;
		GetServerListResponse response = new GetServerListResponse();
		try {
			response = apiService.getServerList(userName);
			response.setMessage("SUCCESS");
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<GetServerListResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Get all server details
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/all/server/list", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GetServerListResponse> getAllServerList() {
		ResponseEntity<GetServerListResponse> responseEntity = null;
		GetServerListResponse response = new GetServerListResponse();
		try {
			response = apiService.getAllServersList();
			response.setMessage("SUCCESS");
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<GetServerListResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	@RequestMapping(value = "/create/project", method = RequestMethod.POST, produces = "application/json")
	@ResponseBody
	public ResponseEntity<UserResponse> createProject(@RequestBody CreateProjectRequest createProject) {

		Boolean result = apiService.createProject(createProject);
		UserResponse response = new UserResponse();
		if (result) {
			response.setStatusCode(HttpStatus.OK.toString());
			return new ResponseEntity<>(response, HttpStatus.OK);

		} else {
			response.setStatusCode(HttpStatus.CONFLICT.toString());
			return new ResponseEntity<>(response, HttpStatus.EXPECTATION_FAILED);

		}
	}

	@RequestMapping(value = "/all/projects", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GetProjectsResponse> getAllProjects(@RequestParam String userName) {
		ResponseEntity<GetProjectsResponse> responseEntity = null;
		GetProjectsResponse response = new GetProjectsResponse();
		try {
			response = apiService.getAllProjects(userName);
			response.setMessage("SUCCESS");
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<GetProjectsResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

	/**
	 * Get Server details based on project
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@RequestMapping(value = "/project/server/details", method = RequestMethod.GET, produces = "application/json")
	@ResponseBody
	public ResponseEntity<GetServerListResponse> getProjectServerDetails(@RequestParam String projectId,
			String userName) {
		ResponseEntity<GetServerListResponse> responseEntity = null;
		GetServerListResponse response = new GetServerListResponse();
		try {
			response = apiService.getProjectServerDetails(projectId, userName);
			response.setMessage("SUCCESS");
			response.setStatusCode(HttpStatus.OK.toString());
			responseEntity = new ResponseEntity<>(response, HttpStatus.OK);
		} catch (Exception e) {
			LOG.error(e.getMessage());
			response.setMessage(e.getMessage());
			responseEntity = new ResponseEntity<GetServerListResponse>(response, HttpStatus.EXPECTATION_FAILED);
		}
		return responseEntity;
	}

}
