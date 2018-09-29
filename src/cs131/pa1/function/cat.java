package cs131.pa1.function;

import java.io.File;

import cs131.pa1.filter.sequential.SequentialFilter;

public class cat extends SequentialFilter {
	private String file;
	private String[] inputs;
	
	public cat(String[] inputs){
		this.inputs = inputs;
	}

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public int errorCheck(){
		Boolean empty = input.isEmpty();
		return 0;
	}
	
	public void process(){
		File output;
		if(input.isEmpty()){
			output = new File(inputs[1]);
		}else{
			output = new File(input.poll());
		}
		while (!input.isEmpty()){
			String line = input.poll();
			String processedLine = processLine(line);
			if (processedLine != null){
				output.add(processedLine);
			}
		}	
	}

}
