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
				String[] disAttach=input.poll().split("\n");
				Set<String> hashee = new HashSet<String>(); //create a new hash set
				List<String> disAttachToList=Arrays.asList(disAttach); //convert disAttach to a list
				hashee.addAll(disAttachToList); //add disAttach, the list, into the hash set
				output.add(hashee.toString()); //put hash set content into output
			}
		} else {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(line));

		}
		return output.toString().substring(1, output.toString().length() - 1);
	}

}
