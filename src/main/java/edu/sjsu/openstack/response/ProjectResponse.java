package edu.sjsu.openstack.response;

import java.io.Serializable;

import org.openstack4j.model.compute.Flavor;

public class ProjectResponse implements Serializable {

	private static final long serialVersionUID = 1L;
	private String id;
	private String projectName;
	private int noOfInstances;
	private String description;
	private Flavor flavor;
	private String imageName;
	private boolean projectCreated;
	private Integer costPerHour;

	public ProjectResponse() {

	}

	public ProjectResponse(String id, String projectName, int noOfInstances, String description, Flavor flavor,
			String imageName, boolean projectCreated, Integer costPerHour) {
		super();
		this.id = id;
		this.projectName = projectName;
		this.noOfInstances = noOfInstances;
		this.description = description;
		this.flavor = flavor;
		this.imageName = imageName;
		this.projectCreated = projectCreated;
		this.costPerHour = costPerHour;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
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

	public Flavor getFlavor() {
		return flavor;
	}

	public void setFlavor(Flavor flavor) {
		this.flavor = flavor;
	}

	public boolean isProjectCreated() {
		return projectCreated;
	}

	public void setProjectCreated(boolean projectCreated) {
		this.projectCreated = projectCreated;
	}

	public String getImageName() {
		return imageName;
	}

	public void setImageName(String imageName) {
		this.imageName = imageName;
	}

	public Integer getCostPerHour() {
		return costPerHour;
	}

	public void setCostPerHour(Integer costPerHour) {
		this.costPerHour = costPerHour;
	}

	@Override
	public String toString() {
		return "ProjectResponse [id=" + id + ", projectName=" + projectName + ", noOfInstances=" + noOfInstances
				+ ", description=" + description + ", flavor=" + flavor + ", imageName=" + imageName
				+ ", projectCreated=" + projectCreated + ", costPerHour=" + costPerHour + "]";
	}

}
