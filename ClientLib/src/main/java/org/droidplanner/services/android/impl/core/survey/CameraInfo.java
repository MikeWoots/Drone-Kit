package org.droidplanner.services.android.impl.core.survey;

public class CameraInfo {
	public String name = "Phantom 4";
	public Double sensorWidth = 6.17;
	public Double sensorHeight = 4.55;
	public Double sensorResolution = 12.4;
	public Double focalLength = 3.6;
	public Double overlap = 70.0;
	public Double sidelap = 70.0;
	public boolean isInLandscapeOrientation = true;

	public Double getSensorLateralSize() {
		if (isInLandscapeOrientation) {
			return sensorWidth;
		} else {
			return sensorHeight;
		}
	}

	public Double getSensorLongitudinalSize() {
		if (isInLandscapeOrientation) {
			return sensorHeight;
		} else {
			return sensorWidth;
		}
	}

	@Override
	public String toString() {
		return "Camera:"+name+" ImageWidth:" + sensorWidth + " ImageHeight:" + sensorHeight + " FocalLength:"
				+ focalLength + " Overlap:" + overlap + " Sidelap:" + sidelap
				+ " isInLandscapeOrientation:" + isInLandscapeOrientation;

	}

}