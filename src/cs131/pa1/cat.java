package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.nio.file.*;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class cat extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		/*
		 * create a new address/path using the given line.
		 * 
		 * @issue is that if path is illegal (i.e. includes symbols) a java error will
		 * be issued
		 */
		Path togo = Paths.get(SequentialREPL.currentWorkingDirectory + "\\" + line);

		if (Files.exists(togo)) {
			/*
			 * add the content of the file to the output
			 */
			try {
				output.add(new String(Files.readAllBytes(togo)));
			} catch (IOException e) {
			}
		} else {
			System.out.print(Message.FILE_NOT_FOUND.with_parameter("cat " + line));
		}
		return output.toString().substring(1, output.toString().length() - 1);
	}

}