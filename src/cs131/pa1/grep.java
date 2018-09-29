package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class grep extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		output = new LinkedList<String>(); 
		String parts[] = line.trim().split(" ");
		if (parts.length <= 1) { //( grep ) alone does not work
			System.out.print(Message.REQUIRES_PARAMETER.with_parameter(line));
		} else if (parts.length == 2) {
			if (input.isEmpty()) {
				System.out.print(Message.REQUIRES_INPUT.with_parameter(line));
			} else {
				/*
				 * separates into lines and find the line in the input that 
				 * contains what we want
				 */
				while (!input.isEmpty()) {
					String[] found = input.poll().split("\r\n");
					for (int i = 0; i < found.length; i++) {
						if (found[i].contains(parts[1].trim())) {
							output.add(found[i]);
						}
					}
				}
			}
		} else { 
			/*
			 * Can use this if ( grep a hello_world.txt ) is an allowable command
			 */
			/*cat display = new cat();
			display.output = new LinkedList<String>();
			display.processLine(parts[2].trim());
			while (!display.output.isEmpty()) {
				String[] found = display.output.poll().split("\r\n");
				for (int i = 0; i < found.length; i++) {
					if (found[i].contains(parts[1].trim())) {
						output.add(found[i]);
					}
				}
			}*/ 
			System.out.print(Message.INVALID_PARAMETER.with_parameter(line));
			
		}
		return output.toString().substring(1, output.toString().length() - 1);
	}


}
