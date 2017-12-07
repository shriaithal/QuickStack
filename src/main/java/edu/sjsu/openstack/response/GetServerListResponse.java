package edu.sjsu.openstack.response;

import java.util.List;

public class GetServerListResponse extends GenericResponse {

	private static final long serialVersionUID = 1L;

	List<VMMetadataResponse> vmMetadata;

	public GetServerListResponse() {
	}

	public List<VMMetadataResponse> getVmMetadata() {
		return vmMetadata;
	}

	public void setVmMetadata(List<VMMetadataResponse> vmMetadata) {
		this.vmMetadata = vmMetadata;
	}

	@Override
	public String toString() {
		return "GetServerListResponse [vmMetadata=" + vmMetadata + "]";
	}

}
