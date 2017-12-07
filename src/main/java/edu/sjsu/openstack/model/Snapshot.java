package edu.sjsu.openstack.model;

public class Snapshot {

	private String snapShotName;
	private String imageId;

	public String getSnapShotName() {
		return snapShotName;
	}

	public void setSnapShotName(String snapShotName) {
		this.snapShotName = snapShotName;
	}

	public String getImageId() {
		return imageId;
	}

	public void setImageId(String imageId) {
		this.imageId = imageId;
	}

}
