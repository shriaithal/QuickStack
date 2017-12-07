package edu.sjsu.openstack.request;

import java.io.Serializable;

public class SignUpRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String username;
	private String firstname;
	private String lastname;
	private String department;
	private String password;

	public SignUpRequest() {
	}

	public SignUpRequest(String username, String firstname, String lastname, String department, String password) {
		super();
		this.username=username;
		this.firstname = firstname;
		this.lastname = lastname;
		this.department = department;
		this.password = password;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getFirstname() {
		return firstname;
	}

	public void setFirstname(String firstname) {
		this.firstname = firstname;
	}

	public String getLastname() {
		return lastname;
	}

	public void setLastname(String lastname) {
		this.lastname = lastname;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	@Override
	public String toString() {
		return "SignUpRequest [username=" + username + ", firstname=" + firstname + ", lastname=" + lastname + ", department=" + department
				+ ", password=" + password + "]";
	}
}
