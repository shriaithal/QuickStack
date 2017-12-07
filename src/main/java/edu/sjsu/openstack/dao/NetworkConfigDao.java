package edu.sjsu.openstack.dao;

import edu.sjsu.openstack.model.NetworkConfig;

/**
 * Mongo Layer to save and retrieve network config details
 * 
 * @author Anushri Srinath Aithal
 */
public interface NetworkConfigDao {

	NetworkConfig getLastAssignedNetwork();

	void insertRecord(NetworkConfig newNetworkConfig);

	void deleteNetwork(String networkName);

}
