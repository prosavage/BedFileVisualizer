package net.prosavage.bedfilevisualizer.bedfileclient;

import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

public class BedFileClient {

	private List<File> files;
	private boolean useMultiThreading;
	private Runtime runtime;

	public BedFileClient(List<File> files) {
		this.files = files;
		useMultiThreading = files.size() > 2;
		runtime = Runtime.getRuntime();
	}


	public File runIntersect(File outputFile) {
		try {
			ProcessBuilder processBuilder = new ProcessBuilder();
			processBuilder.directory(this.files.get(0).getParentFile());
			processBuilder.redirectOutput(outputFile);
			if (useMultiThreading) {
				List<String> command = Arrays.asList(
						  "python",
						  "/home/prosavage/Projects/School/HudsonAlphaTechChallenge/rum.py",
						  files.size() + ""
				);
				files.forEach((file -> command.add(file.getName())));
				processBuilder.start();
				return outputFile;
			}
			processBuilder.command("bedtools", "intersect", "-a", this.files.get(0).getAbsolutePath(), "-b", files.get(0).getAbsolutePath());
			processBuilder.start();

		} catch (IOException ex) {
			ex.printStackTrace();
		}
		return outputFile;
	}


}
