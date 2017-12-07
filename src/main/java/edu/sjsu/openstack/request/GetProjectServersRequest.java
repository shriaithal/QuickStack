package edu.sjsu.openstack.request;

import java.io.Serializable;

public class GetProjectServersRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectId;
	private String userName;

	public GetProjectServersRequest() {
	}

	public GetProjectServersRequest(String projectId, String userName) {
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

}
