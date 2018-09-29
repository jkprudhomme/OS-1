package cs131.pa1.filter.sequential;

import java.io.IOException;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class wc extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		/*
		 * @see grep.java & uniq.java
		 * @warning: long runtime
		 */
		output = new LinkedList<String>();
		int LC = 0, WC = 0, CC = 0;
		String parts[] = line.trim().split(" ");
		if (parts.length ==1) {
			if (input.isEmpty()) {
				System.out.print(Message.REQUIRES_INPUT.with_parameter(line));
			} else {
				while (!input.isEmpty()) {
//					String[] countLines = input.poll().split("\n"); //WILL COUNT .txt, etc.
//					LC = countLines.length;
//					for (int i = 0; i < LC; i++) {
//						String[] countWords = countLines[i].split(" ");
//						WC += countWords.length;
//						for (int j = 0; i < WC; i++) {
//							CC += countWords[i].length();
//						}
//					}
					String take = input.poll();
					for (int i = 0; i < input.poll().length(); i++) {
						if (take.charAt(i) == '\n') {
							LC++;
						} else if (take.charAt(i) == ' ') {
							WC++;
						} else {
							CC++;
						}
					}
				}
				output.add(LC + " " + WC + " " + CC);
			}
		} else {
			System.out.print(Message.INVALID_PARAMETER.with_parameter(line));

		}
		return output.toString().substring(1, output.toString().length() - 1);
	}


}
