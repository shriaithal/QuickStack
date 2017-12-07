package edu.sjsu.openstack.dao;

import java.util.List;

import edu.sjsu.openstack.model.VMMetadata;

public interface VMMetadataDao {

	void insert(VMMetadata vmMetadata);

	VMMetadata getVMMetadataByServerName(String serverName);

	void updateVMMetadata(VMMetadata vmMetadata);

	void deleteVMMetadata(String serverName);

	List<VMMetadata> getVMMetadataByUserName(String userName);

	List<VMMetadata> getAllVMMetadata();

	List<VMMetadata> getVMMetadata(String projectId, String userName);

	boolean isProjectCreated(String userName, String id);

}
