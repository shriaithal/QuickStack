package edu.sjsu.openstack.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "networkconfig")
public class NetworkConfig {

	@Id
	private String id;
	private String networkName;
	private String subNetName;
	private String fixedIP;
	private Long createdTime;
	private String subnetIPPool;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getNetworkName() {
		return networkName;
	}

	public void setNetworkName(String networkName) {
		this.networkName = networkName;
	}

	public String getSubNetName() {
		return subNetName;
	}

	public void setSubNetName(String subNetName) {
		this.subNetName = subNetName;
	}

	public String getFixedIP() {
		return fixedIP;
	}

	public void setFixedIP(String fixedIP) {
		this.fixedIP = fixedIP;
	}

	public Long getCreatedTime() {
		return createdTime;
	}

	public void setCreatedTime(Long createdTime) {
		this.createdTime = createdTime;
	}

	public String getSubnetIPPool() {
		return subnetIPPool;
	}

	public void setSubnetIPPool(String subnetIPPool) {
		this.subnetIPPool = subnetIPPool;
	}
}
