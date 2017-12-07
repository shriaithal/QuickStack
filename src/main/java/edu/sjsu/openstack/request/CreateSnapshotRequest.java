package edu.sjsu.openstack.request;

import java.io.Serializable;

public class CreateSnapshotRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	String serverId;
	String snapshotName;
	String serverName;

	public CreateSnapshotRequest() {
	}

	public CreateSnapshotRequest(String serverId, String snapshotName, String serverName) {
		super();
		this.serverId = serverId;
		this.snapshotName = snapshotName;
		this.serverName = serverName;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public String getSnapshotName() {
		return snapshotName;
	}

	public void setSnapshotName(String snapshotName) {
		this.snapshotName = snapshotName;
	}

	@Override
	public String toString() {
		return "CreateSnapshotRequest [serverId=" + serverId + ", snapshotName=" + snapshotName + ", serverName="
				+ serverName + "]";
	}

}
