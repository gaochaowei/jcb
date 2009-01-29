package com.jcb.tools.ant;

import java.io.File;

import org.apache.tools.ant.Task;

public class DiffTask extends Task {
	private String dir1;
	private String dir2;
	private String dir3;
	private String logFile;

	public String getDir3() {
		return dir3;
	}

	public void setDir3(String dir3) {
		this.dir3 = dir3;
	}

	public void execute() {
		try {
			Diff diff = new Diff();
			diff.setDirNew(new File(dir1));
			diff.setDirOld(new File(dir2));
			diff.setDirOut(new File(dir3));
			diff.setLogFile(logFile);
			diff.compare();
		} catch (Exception e) {
			System.out.println("Exception occurs");
			e.printStackTrace();
		}
	}

	public String getDir1() {
		return dir1;
	}

	public void setDir1(String dir1) {
		this.dir1 = dir1;
	}

	public String getDir2() {
		return dir2;
	}

	public void setDir2(String dir2) {
		this.dir2 = dir2;
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

}
