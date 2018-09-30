package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Filter;
import cs131.pa1.filter.Message;

public class write extends SequentialFilter {
	private String line;

	public write(String line) {
		this.line = line;
	}

	@Override
	public void process() {
		/*
		 * creates Path using line and writes using input
		 */

		// Path togo = Paths.get(SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR
		// + line.replaceAll("> ", "").trim());
		// String send = input.poll();
		// if (Files.exists(togo)) {
		// writeTo(togo.toString(), send);
		// } else {
		// create(togo.toString(), send);
		// }
		while (!input.isEmpty()) {
			String send = input.poll();
			File fileNeeded = new File(
					SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR + line.replaceAll("> ", "").trim());
			boolean exists = fileNeeded.exists();
			if (exists) {
				writeTo(fileNeeded, send);
			} else {
				create(fileNeeded, send);
			}
		}
		// return null; // no ouput
	}

	private static void create(File address, String content) {
		/*
		 * For a new file
		 */
		FileWriter fileWriter = null;
		try {
			address.createNewFile();
			fileWriter = new FileWriter(address);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void writeTo(File putter, String content) {

		/*
		 * for overwriting a file
		 */
		try {
			FileWriter fileWriter = new FileWriter(putter);
			fileWriter.write(content);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
