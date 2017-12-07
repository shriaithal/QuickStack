package edu.sjsu.openstack.request;

import java.io.Serializable;

public class CreateServerRequest implements Serializable {

	private static final long serialVersionUID = 1L;
	String serverName;
	String flavorName;
	String imageName;
	String userName;

	public CreateServerRequest() {
	}

	public String getServerName() {
		return serverName;
	}

	public void setServerName(String serverName) {
		this.serverName = serverName;
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

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	@Override
	public String toString() {
		return "CreateServerRequest [serverName=" + serverName + ", flavorName=" + flavorName + ", imageName="
				+ imageName + ", userName=" + userName + "]";
	}

}
