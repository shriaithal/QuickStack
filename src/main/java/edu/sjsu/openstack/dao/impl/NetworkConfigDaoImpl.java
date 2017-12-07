package edu.sjsu.openstack.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;
import org.springframework.util.CollectionUtils;

import edu.sjsu.openstack.dao.NetworkConfigDao;
import edu.sjsu.openstack.model.NetworkConfig;

@Repository
public class NetworkConfigDaoImpl implements NetworkConfigDao {

	@Autowired
	MongoOperations mongoOperations;

	/**
	 * Get last assigned network
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public NetworkConfig getLastAssignedNetwork() {
		NetworkConfig networkConfig = null;
		Query query = new Query();
		query.with(new Sort(Sort.Direction.DESC, "createdTime"));
		List<NetworkConfig> list = mongoOperations.find(query, NetworkConfig.class);
		if (!CollectionUtils.isEmpty(list)) {
			networkConfig = list.get(0);
		}
		return networkConfig;
	}

	/**
	 * insert network config
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public void insertRecord(NetworkConfig newNetworkConfig) {
		mongoOperations.insert(newNetworkConfig);
	}

	/**
	 * delete networks
	 * 
	 * @author Anushri Srinath Aithal
	 */
	@Override
	public void deleteNetwork(String networkName) {
		Query query = new Query(Criteria.where("networkName").is(networkName));
		mongoOperations.remove(query, NetworkConfig.class);

	}

}
