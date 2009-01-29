package com.jcb.tools.ant;

import java.io.File;
import java.io.FileFilter;
import java.io.FilenameFilter;

class SFAFilter implements FilenameFilter, FileFilter {
	public boolean accept(File dir, String name) {
		if (name.startsWith("."))
			return false;
		return true;
	}

	public boolean accept(File f) {
		return accept(f, f.getName());
	}
}
