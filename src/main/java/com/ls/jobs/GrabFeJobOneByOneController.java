package com.ls.jobs;

public class GrabFeJobOneByOneController {

	private static Boolean isGrabFEJobRunning = false;

	public synchronized static Boolean checkIfSomeOtherJobIsRunning() {

		return isGrabFEJobRunning;
	}

	public synchronized static boolean releaseControll() {

		isGrabFEJobRunning = false;

		return true;
	}

	public synchronized static boolean requestControll() {

		if (isGrabFEJobRunning) {
			
			//request fail
			return false;
		}
		isGrabFEJobRunning = true;

		return isGrabFEJobRunning;
	}
}
