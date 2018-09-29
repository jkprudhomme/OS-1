package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class uniq extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		/*
		 * @see grep.java
		 * same idea just filters out duplicates
		 */
		output = new LinkedList<String>();
		String parts[] = line.trim().split(" ");
		if (parts.length == 1) {
			if (input.isEmpty()) {
				System.out.print(Message.REQUIRES_INPUT.with_parameter(line));
			} else {
				String disAttach[]=input.poll().split("\r\n");
				for (int i=0; i<disAttach.length;i++){
					if (!output.contains(disAttach[i])) {
						output.add(disAttach[i]);
					}
				}
			}
		} else {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(line));

		}
		return output.toString().substring(1, output.toString().length() - 1);
	}

}
