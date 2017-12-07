package edu.sjsu.openstack.model;

import org.openstack4j.model.compute.Flavor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "project")
public class Project {

	@Id
	private String id;
	private String projectName;
	private int noOfInstances;
	private String description;
	private Flavor flavor;
	private String imageName;
	private Integer costPerHour;

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

}
