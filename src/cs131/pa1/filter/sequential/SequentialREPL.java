package cs131.pa1.filter.sequential;
import java.util.List;
import java.util.Scanner;

import cs131.pa1.filter.*;


public class SequentialREPL {

	static String currentWorkingDirectory = System.getProperty("user.dir");
	static String command;
	
	public static void main(String[] args){
//		Intro statements for user on runtime
//		System.out.print(Message.NEWCOMMAND);
		System.out.print(Message.WELCOME);
		Scanner scan = new Scanner(System.in);
		
//		Start command line loop with scanner
		while(true){
			System.out.print(Message.NEWCOMMAND);
			command = scan.nextLine();
			
//			check for exit command and break from loop if present
			if(command.equals("exit")){
				break;
			}
			
//			check for cd
//			if(command.split(" ")[0].equals("cd")){
//				Cd currentCom = new Cd();
//			}else{
////			Check for error in rest of commands
//				List<SequentialFilter> filters = SequentialCommandBuilder.legalSubcommands(command, currentWorkingDirectory);
//			
////			Run each subcommand and output final result
//				SequentialCommandBuilder.getOutput(filters);
//			}
		}
		
//		End session
		scan.close();
		System.out.print(Message.GOODBYE);
		
	}
		public static void setWorkingDirectory(String s) {
		currentWorkingDirectory = s;
	}


}
