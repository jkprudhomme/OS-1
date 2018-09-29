package cs131.pa1.filter.sequential;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.*;
import java.util.LinkedList;
import java.util.Scanner;

import cs131.pa1.filter.Message;

public class write extends SequentialFilter {
	private String line;
	
	public write(String line){
		this.line = line;
	}
	
	@Override
	public void process(){
		/*
		 * creates Path using line and writes using input
		 */
		Path togo = Paths.get(SequentialREPL.currentWorkingDirectory + "\\" + line.replaceAll("> ", "").trim());
		String send = input.poll();
		if (Files.exists(togo)) {
			writeTo(togo.toString(), send);
		} else {
			create(togo.toString(), send);
		}
//		return null; // no ouput
	}

	private static void create(String address, String content) {
		/*
		 * For a new file
		 */
		FileWriter writer = null;
		try {
			writer = new FileWriter(address);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.print(content);
		printWriter.close();
	}

	private static void writeTo(String putter, String content) {
		/*
		 * for overwriting a file
		 */
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(putter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter.print(content);
		printWriter.close();
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}

}
		PrintWriter printWriter = new PrintWriter(writer);
		printWriter.print(content);
		printWriter.close();
	}

	private static void write(String putter, String content) {
		/*
		 * for overwriting a file
		 */
		PrintWriter printWriter = null;
		try {
			printWriter = new PrintWriter(putter);
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		printWriter.print(content);
		printWriter.close();
	}

}
