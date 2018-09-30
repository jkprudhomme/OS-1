package cs131.pa1.filter.sequential;

import java.io.File;
import java.util.*;

import cs131.pa1.filter.*;

public class SequentialCommandBuilder {

	private static String checkInput(String command, Boolean input, Boolean need){
		if(input == need){
			return "";
		}else if(!need){
			return Message.CANNOT_HAVE_INPUT.with_parameter(command);
		}else{
			return Message.REQUIRES_INPUT.with_parameter(command);
		}
	}
	
	private static String checkFile(String[] commands, String command, String currentDir){
		if(new File(currentDir + Filter.FILE_SEPARATOR + commands[1]).exists()){
			return "";
		}else{
			return Message.FILE_NOT_FOUND.with_parameter(command);
		}
	}
	
	private static String checkDir(String[] commands, String command, String currentDir){
		if(new File(currentDir + Filter.FILE_SEPARATOR + commands[commands.length-1]).exists()){
			return "";
		}else{
			return Message.DIRECTORY_NOT_FOUND.with_parameter(command);
		}
	}
	
	private static String checkNoOutput(String command, Boolean output){
		if(!output){
			return "";
		}else{
			return Message.CANNOT_HAVE_OUTPUT.with_parameter(command);
		}
		
	}
	
	private static String checkArgs(String[] commands, String command, int input, int args){
		if(commands.length >= args+1 - input){
			return "";
		}else{
			return Message.REQUIRES_PARAMETER.with_parameter(command);
		}
	}
	
	
	public static List<SequentialFilter> legalSubcommands(String command, String currentDir){
//		List of filters to return at end of method
		List<SequentialFilter> filters = new ArrayList<SequentialFilter>();
		
//		Make formatting changes to string and split command
		command = command.replace(" >", " | >");
		String[] commands = command.split("\\|");
		
//		Current error message if any
		String error = "";
		
//		Create a filter for each command
		for(int i = 0; i<commands.length;i++){
			
//			Split up command and parameters
			String[] subcommands = commands[i].trim().split(" ");
		
//			Set piping input and output booleans for command	
			Boolean output = false;
			Boolean input = false;
			if(i<commands.length-1){
				output = true;
			}
			if(i > 0){
				input = true;
			}
			
//			Identify command, check for possible errors, and create a filter if clear
			switch(subcommands[0])
			{
				case "pwd":
					error = checkInput(commands[i],input,false);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					pwd displayp = new pwd(currentDir);
					displayp.output = new LinkedList<String>();
					filters.add(displayp);
					break;
				case "ls":
					error = checkInput(commands[i],input,false);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					ls displayl = new ls(currentDir);
					displayl.output = new LinkedList<String>();
					filters.add(displayl); 
					
					break;
				case "cd":
					error = checkInput(commands[i],input,false);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkNoOutput(commands[i],output);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkArgs(subcommands,commands[i],input ? 1:0,1);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkDir(subcommands,commands[i],currentDir);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}

					cd displaycd = new cd(subcommands[1]);
					displaycd.output = new LinkedList<String>();
					filters.add(displaycd); 
					
					break;
				case "cat":
					error = checkInput(commands[i],input,false);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkArgs(subcommands,commands[i],input ? 1:0,1);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkFile(subcommands,commands[i],currentDir);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					
					cat displayc = new cat(subcommands[1]);
					displayc.input = new LinkedList<String>();
					displayc.output = new LinkedList<String>();
					filters.add(displayc);
					break;
				case "grep":
					error = checkInput(commands[i],input,true);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkArgs(subcommands,commands[i],input ? 1:0,2);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					grep displayg = new grep(commands[i]);
					displayg.input = new LinkedList<String>();
					filters.add(displayg); 
					break;
				case "wc":
					error = checkInput(commands[i],input,true);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					wc displayw = new wc(commands[i]);
					displayw.input = new LinkedList<String>();
					displayw.output = new LinkedList<String>();
					filters.add(displayw);  
					break;
				case "uniq":
					uniq displayu = new uniq(commands[i]);
					displayu.input = new LinkedList<String>();
					displayu.output = new LinkedList<String>();
					filters.add(displayu); 
					break;
				case ">":
					error = checkInput(commands[i],input,true);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkArgs(subcommands,commands[i],input ? 1:0,2);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					error = checkNoOutput(commands[i], output);
					if(!error.isEmpty()){
						System.out.print(error);
						return null;
					}
					write displaywr = new write(subcommands[1]);
					displaywr.input = new LinkedList<String>();
					displaywr.output = new LinkedList<String>();
					filters.add(displaywr);  
					break;
				default:
					System.out.print(Message.COMMAND_NOT_FOUND.with_parameter(commands[i]));
					return null;
			}
		}
		return filters;
	}
	
//	Process and link filters to get output
	public static void getOutput(List<SequentialFilter> filters){
		if(filters.size() == 1){
			filters.get(0).process();
			Queue<String> out = filters.get(filters.size()-1).output;
			for(String lines : out){
				System.out.println(lines);
			}
		}else{
			for(int i = 0;i < filters.size()-1;i++){
				filters.get(i).process();
				filters.get(i).setNextFilter(filters.get(i+1));
			}
			filters.get(filters.size()-1).process();
			
			Queue<String> out = filters.get(filters.size()-1).output;
			for(String lines : out){
				System.out.println(lines);
			}
		}
	}
}
