package edu.sjsu.openstack.response;

public class UserResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;
	private String userName;
	private String firstName;
	private String lastName;
	private String department;

	public UserResponse() {
	}

	public UserResponse(String userName, String firstName, String lastName, String department) {
		super();
		this.userName = userName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.department = department;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	@Override
	public String toString() {
		return "UserResponse [userName=" + userName + ", firstName=" + firstName + ", lastName=" + lastName
				+ ", department=" + department + "]";
	}

}
