package com.ls.jobs;

public class GrabFeJobOneByOneController {

	private static Boolean isGrabFEJobRunning = false;

	public synchronized static Boolean checkIfSomeOtherJobIsRunning() {

		return isGrabFEJobRunning;
	}

	public synchronized static void releaseControll() {

		isGrabFEJobRunning = false;
	}

	public synchronized static void requestControll() {

		isGrabFEJobRunning = true;
	}
}
