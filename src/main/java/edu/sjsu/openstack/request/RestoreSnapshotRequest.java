package edu.sjsu.openstack.request;

import java.io.Serializable;

public class RestoreSnapshotRequest implements Serializable {
	
	private static final long serialVersionUID = 1L;

	String projectId;
	String userName;
	String snapshotName;
	String serverName;
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
	public String getSnapshotName() {
		return snapshotName;
	}
	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}
	public String getServerName() {
		return serverName;
	}
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	@Override
	public String toString() {
		return "RestoreSnapshotRequest [projectId=" + projectId + ", userName=" + userName + ", snapshotName="
				+ snapshotName + ", serverName=" + serverName + "]";
	}
	
	

}
