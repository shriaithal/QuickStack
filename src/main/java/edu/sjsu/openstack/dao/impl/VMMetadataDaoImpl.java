package edu.sjsu.openstack.dao.impl;

import java.util.List;

import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import edu.sjsu.openstack.dao.VMMetadataDao;
import edu.sjsu.openstack.model.VMMetadata;

@Repository
public class VMMetadataDaoImpl implements VMMetadataDao {

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public void insert(VMMetadata vmMetadata) {
		mongoOperations.insert(vmMetadata);
	}

	@Override
	public VMMetadata getVMMetadataByServerName(String serverName) {
		Criteria criteria = Criteria.where("serverName").is(serverName);
		Query query = new Query(criteria);
		return mongoOperations.findOne(query, VMMetadata.class);
	}

	@Override
	public void updateVMMetadata(VMMetadata vmMetadata) {
		mongoOperations.save(vmMetadata);
	}

	@Override
	public void deleteVMMetadata(String serverName) {
		Criteria criteria = Criteria.where("serverName").is(serverName);
		Query query = new Query(criteria);
		mongoOperations.findAndRemove(query, VMMetadata.class);
	}

	@Override
	public List<VMMetadata> getVMMetadataByUserName(String userName) {
		Criteria criteria = Criteria.where("userName").is(userName);
		Query query = new Query(criteria);
		return mongoOperations.find(query, VMMetadata.class);
	}

	@Override
	public List<VMMetadata> getAllVMMetadata() {
		return mongoOperations.findAll(VMMetadata.class);
	}

	@Override
	public List<VMMetadata> getVMMetadata(String projectId, String userName) {
		Criteria criteria = Criteria.where("project._id").is(new ObjectId(projectId))
				.andOperator(Criteria.where("userName").is(userName));
		Query query = new Query(criteria);
		return mongoOperations.find(query, VMMetadata.class);
	}

	@Override
	public boolean isProjectCreated(String userName, String projectId) {
		Criteria criteria = Criteria.where("project._id").is(new ObjectId(projectId))
				.andOperator(Criteria.where("userName").is(userName));
		Query query = new Query(criteria);
		List<VMMetadata> list = mongoOperations.find(query, VMMetadata.class);
		if (!CollectionUtils.isEmpty(list)) {
			return true;
		}
		return false;
	}

}
