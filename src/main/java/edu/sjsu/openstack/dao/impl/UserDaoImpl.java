package edu.sjsu.openstack.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import edu.sjsu.openstack.dao.UserDao;
import edu.sjsu.openstack.model.User;

@Repository
public class UserDaoImpl implements UserDao {

	@Autowired
	MongoOperations mongoOperations;

	@Override
	public User getUserByNameAndPassowrd(String userName, String password) {
		Criteria criteria = Criteria.where("userName").is(userName)
				.andOperator(Criteria.where("password").is(password));
		Query query = new Query(criteria);
		User user = mongoOperations.findOne(query, User.class);
		return user;
	}

	@Override
	public void signUpUser(User newuser) {
		mongoOperations.insert(newuser);
	}

}
