package edu.sjsu.openstack.service;

import java.util.List;

import javax.xml.bind.ValidationException;

import org.openstack4j.model.compute.Flavor;
import org.openstack4j.model.compute.Server;

import edu.sjsu.openstack.request.CreateProjectRequest;
import edu.sjsu.openstack.request.SignUpRequest;
import edu.sjsu.openstack.response.GetProjectsResponse;
import edu.sjsu.openstack.response.GetServerListResponse;
import edu.sjsu.openstack.response.OpenStackMetrics;
import edu.sjsu.openstack.response.UserResponse;

public interface OpenStackAPIService {

	/**
	 * Method to retrieve all servers
	 * 
	 * @author Anushri Srinath Aithal
	 */
	List<? extends Server> getNovaList();

	String createSnapshot(String serverId, String snapshotName, String serverName);

	void restoreSnapshot(String projectId, String userName, String snapshotName, String serverName);

	OpenStackMetrics getDiagnosticsFromCLI();

	/**
	 * Login to application
	 * 
	 * @author Anushri Srinath Aithal
	 */
	UserResponse login(String userName, String password);

	void startServer(String serverName);

	void stopServer(String serverName);

	void deleteServer(String serverName, String userName);

	Integer generateBill(String serverName, String userName);

	UserResponse signUp(SignUpRequest request) throws ValidationException;

	List<? extends Flavor> getFlavorList();

	/**
	 * Get Servers per User
	 * 
	 * @author Anushri Srinath Aithal
	 */
	GetServerListResponse getServerList(String userName);

	// For Creation of Project.
	Boolean createProject(CreateProjectRequest createProjectRequest);

	/**
	 * Get all server details
	 * 
	 * @author Anushri Srinath Aithal
	 */
	GetServerListResponse getAllServersList();

	GetProjectsResponse getAllProjects(String userName);

	/**
	 * Get Server details based on project
	 * 
	 * @author Anushri Srinath Aithal
	 */
	GetServerListResponse getProjectServerDetails(String projectId, String userName);

	void createServer(String projectId, String userName);

}
