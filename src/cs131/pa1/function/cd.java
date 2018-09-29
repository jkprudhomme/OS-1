package cs131.pa1.function;



import java.io.File;
import java.util.Arrays;

import cs131.pa1.filter.sequential.*;import cs131.pa1.filter.*;


public class cd extends SequentialFilter{
	private String[] inputs;
	private String currentDir;

	@Override
	protected String processLine(String line) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public cd(String[] inputs, String currentDir){
		this.inputs = inputs;
		this.currentDir = currentDir;
	}
	
	public String moveDir(){
		if(inputs.length != 2){
			return Message.REQUIRES_PARAMETER.with_parameter("cd");
		}
		String newDir = this.inputs[1];
		
		if(newDir.equals("..")){
			String[] dir = currentDir.split(Filter.FILE_SEPARATOR);
			return String.join(Filter.FILE_SEPARATOR, Arrays.copyOfRange(dir, 0, dir.length-1)) ;
			
		}else if(newDir.equals(".")){
			return currentDir;
		}else{
//			String[] dirChanges = this.inputs[1].split(Filter.FILE_SEPARATOR);
			newDir = currentDir + Filter.FILE_SEPARATOR + inputs[1];
			File real = new File(newDir);
			if(real.exists()){
				return newDir;
			}else{
				return Message.DIRECTORY_NOT_FOUND.with_parameter("cd" + inputs[1]);
			}
		}

		
		
		

	}

	

}
