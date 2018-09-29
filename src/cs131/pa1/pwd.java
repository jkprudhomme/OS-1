package cs131.pa1.filter.sequential;

import java.io.File;

import cs131.pa1.filter.Message;

public class pwd extends SequentialFilter {
	@Override
	protected String processLine(String line) {
		output.add(line);
		return output.toString().substring(1, output.toString().length()-1);
	}

}
