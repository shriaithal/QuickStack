package edu.sjsu.openstack.response;

import java.io.Serializable;
import java.util.List;

import edu.sjsu.openstack.model.Project;
import edu.sjsu.openstack.model.Snapshot;

public class VMMetadataResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String serverName;
	private String networkName;
	private String ipAddress;
	private String flavorName;
	private String imageName;
	private String status;
	private String serverId;
	private Project project;
	private List<Snapshot> snapShots;

	public VMMetadataResponse() {
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getIpAddress() {
		return ipAddress;
	}

	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}

	public String getFlavorName() {
		return flavorName;
	}

	public void setFlavorName(String flavorName) {
		this.flavorName = flavorName;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getServerId() {
		return serverId;
	}

	public void setServerId(String serverId) {
		this.serverId = serverId;
	}

	public Project getProject() {
		return project;
	}

	public void setProject(Project project) {
		this.project = project;
	}

	public List<Snapshot> getSnapShots() {
		return snapShots;
	}

	public void setSnapShots(List<Snapshot> snapShots) {
		this.snapShots = snapShots;
	}

	@Override
	public String toString() {
		return "VMMetadataResponse [id=" + id + ", serverName=" + serverName + ", networkName=" + networkName
				+ ", ipAddress=" + ipAddress + ", flavorName=" + flavorName + ", imageName=" + imageName + ", status="
				+ status + ", serverId=" + serverId + ", project=" + project + "]";
	}

}
