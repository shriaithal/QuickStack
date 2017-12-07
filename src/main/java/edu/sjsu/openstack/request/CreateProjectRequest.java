package edu.sjsu.openstack.request;

import java.io.Serializable;

public class CreateProjectRequest implements Serializable {

	private static final long serialVersionUID = 1L;

	private String projectName;
	private int noOfInstances;
	private String description;
	private String flavor;
	private String imageName;
	private Integer costPerHour;

	public CreateProjectRequest() {
	}

	public CreateProjectRequest(String projectName, int noOfInstances, String description, String flavor,
			String imageName, Integer costPerHour) {
		super();
		this.projectName = projectName;
		this.noOfInstances = noOfInstances;
		this.description = description;
		this.flavor = flavor;
		this.imageName = imageName;
		this.costPerHour = costPerHour;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public String getProjectName() {
		return projectName;
	}

	public void setProjectName(String projectName) {
		this.projectName = projectName;
	}

	public int getNoOfInstances() {
		return noOfInstances;
	}

	public void setNoOfInstances(int noOfInstances) {
		this.noOfInstances = noOfInstances;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getFlavor() {
		return flavor;
	}

	public void setFlavor(String flavor) {
		this.flavor = flavor;
	}

	public Integer getCostPerHour() {
		return costPerHour;
	}

	public void setCostPerHour(Integer costPerHour) {
		this.costPerHour = costPerHour;
	}

	@Override
	public String toString() {
		return "CreateProjectRequest [projectName=" + projectName + ", noOfInstances=" + noOfInstances
				+ ", description=" + description + ", flavor=" + flavor + ", imageName=" + imageName + ", costPerHour="
				+ costPerHour + "]";
	}

}
