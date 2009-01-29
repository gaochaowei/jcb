package com.jcb.tools.ant;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.FilenameFilter;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import org.apache.commons.io.FileUtils;

public class Diff {

	public static FilenameFilter filter = new SFAFilter();
	private File dirNew;
	private File dirOld;
	private File dirOut;
	private String logFile;
	private boolean print = true;

	private int dirNewLength;
	private int dirOldLength;
	private BufferedWriter log;

	private enum Operation {

		Add(" + "), Delete(" - "), Update(" U ");

		private String op;

		Operation(String op) {
			this.op = op;
		}

		public String getOp() {
			return op;
		}
	};

	public void compare() throws IOException {
		System.out.println(" N " + dirNew.getCanonicalPath());
		System.out.println(" O " + dirOld.getCanonicalPath());
		System.out.println(" D "
				+ (dirOut == null ? null : dirOut.getCanonicalPath()));
		dirNewLength = dirNew.getCanonicalPath().length() + 1;
		dirOldLength = dirOld.getCanonicalPath().length() + 1;
		if (logFile != null) {
			log = new BufferedWriter(new FileWriter(logFile));
		}
		if (dirOut != null) {
			FileUtils.deleteDirectory(dirOut);
		}
		_compare(dirNew, dirOld);
		if (log != null) {
			log.close();
		}
	}

	private void _compare(File _dirNew, File _dirOld) throws IOException {
		if (_dirNew.isFile()) {
			if (!FileUtils.contentEquals(_dirNew, _dirOld)) {
				process(_dirNew, Operation.Update);
			}
		} else if (_dirNew.isDirectory()) {
			List<String> fnsNew = Arrays.asList(_dirNew.list(filter));
			List<String> fnsOld = Arrays.asList(_dirOld.list(filter));
			for (String fnNew : fnsNew) {
				File fileNew = new File(_dirNew, fnNew);
				if (fnsOld.contains(fnNew)) {
					File fileOld = new File(_dirOld, fnNew);
					_compare(fileNew, fileOld);
				} else {
					process(fileNew, Operation.Add);
				}
			}
			for (String fnOld : fnsOld) {
				if (!fnsNew.contains(fnOld)) {
					File fileOld = new File(_dirOld, fnOld);
					process(fileOld, Operation.Delete);
				}
			}
		}
	}

	private void process(File file, Operation o) throws IOException {
		String relativePath;
		if (o == Operation.Delete)
			relativePath = file.getCanonicalPath().substring(dirOldLength);
		else
			relativePath = file.getCanonicalPath().substring(dirNewLength);

		String logInfo = o.getOp() + relativePath;
		if (print)
			System.out.println(logInfo);
		if (log != null) {
			log.write(logInfo);
			log.newLine();
		}
		if (dirOut != null) {
			String f = dirOut.getCanonicalPath() + File.separator
					+ relativePath;
			switch (o) {
			case Add:
			case Update:
				File newFile = new File(f);
				if (file.isDirectory())
					newFile.mkdirs();
				else if (file.isFile())
					FileUtils.copyFile(file, newFile);
				break;
			case Delete:
				break;
			}
		}
		if (file.isDirectory()) {
			List<String> fns = Arrays.asList(file.list(filter));
			for (String fn : fns) {
				File f = new File(file, fn);
				process(f, o);
			}
		}
	}

	public String getLogFile() {
		return logFile;
	}

	public void setLogFile(String logFile) {
		this.logFile = logFile;
	}

	public boolean isPrint() {
		return print;
	}

	public void setPrint(boolean print) {
		this.print = print;
	}

	public File getDirNew() {
		return dirNew;
	}

	public void setDirNew(File dirNew) {
		this.dirNew = dirNew;
	}

	public File getDirOld() {
		return dirOld;
	}

	public void setDirOld(File dirOld) {
		this.dirOld = dirOld;
	}

	public File getDirOut() {
		return dirOut;
	}

	public void setDirOut(File dirOut) {
		this.dirOut = dirOut;
	}

}
