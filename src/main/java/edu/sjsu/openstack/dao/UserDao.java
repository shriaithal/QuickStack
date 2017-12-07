package edu.sjsu.openstack.dao;

import edu.sjsu.openstack.model.User;

/**
 * Mongo Layer to operate on User object
 * @author Anushri Srinath Aithal
 */
public interface UserDao {

	User getUserByNameAndPassowrd(String userName, String password);

	void signUpUser(User user);

}
