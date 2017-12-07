package edu.sjsu.openstack.response;

import java.util.List;

public class GetProjectsResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	List<ProjectResponse> projectResponse;

	public GetProjectsResponse() {

	}

	public List<ProjectResponse> getProjectResponse() {
		return projectResponse;
	}

	public void setProjectResponse(List<ProjectResponse> projectResponse) {
		this.projectResponse = projectResponse;
	}

	@Override
	public String toString() {
		return "GetProjectsResponse [projectResponse=" + projectResponse + "]";
	}

}
