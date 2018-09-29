package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Message;

public class ls extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		File address = new File(line);
		String[] children = address.list(); //java finds all inside files/folders in directory
		if (children == null) { 
			System.out.println(Message.CANNOT_HAVE_OUTPUT.with_parameter(line));
		} else {
			for (int i = 0; i < children.length; i++) {
				output.add(children[i]); 
			}
		}
		return output.toString().substring(1, output.toString().length()-1);
	}

}
