package edu.sjsu.openstack.request;

import java.io.Serializable;

public class CreateServerProjectRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	String projectId;
	String userName;

	public CreateServerProjectRequest() {
	}

	public CreateServerProjectRequest(String projectId, String userName) {
		super();
		this.projectId = projectId;
		this.userName = userName;
	}

	public String getProjectId() {
		return projectId;
	}

	public void setProjectId(String projectId) {
		this.projectId = projectId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CreateServerProjectRequest [projectId=" + projectId + ", userName=" + userName + "]";
	}

}
