package cs131.pa1.filter.sequential;

import java.nio.file.*;

import cs131.pa1.filter.Message;

public class cd extends SequentialFilter {
	private String line;

	public cd(String line) {
		this.line = line;
	}

	public void process() {
		int lastFileSeparator = -1;
		for (int i = 0; i < SequentialREPL.currentWorkingDirectory.length(); i++) {
			/*
			 * Find the place of the last file separator to use it when entering a new
			 * address
			 * 
			 */
			String some = "" + SequentialREPL.currentWorkingDirectory.charAt(i);
			if (some.equals(FILE_SEPARATOR)) {
				lastFileSeparator = i;
			}
		}
		if (line.equals("")) { // depends on the command builder implementation
			/*
			 * goes back to parent directory
			 */
			SequentialREPL.setWorkingDirectory(SequentialREPL.currentWorkingDirectory.substring(0, lastFileSeparator));
		} else

		{
			Path togo = Paths.get(SequentialREPL.currentWorkingDirectory + FILE_SEPARATOR + line);
			/*
			 * creates a path with the address given and checks if it exists in the
			 * directory
			 */
			if (Files.exists(togo)) {
				SequentialREPL.setWorkingDirectory(SequentialREPL.currentWorkingDirectory += FILE_SEPARATOR + line);
			} else {
				System.out.print(Message.DIRECTORY_NOT_FOUND.with_parameter(line));
			}
		}
	}

	@Override
	protected String processLine(String line) {
		return null;
	}

}
