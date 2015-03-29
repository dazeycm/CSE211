package Turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

import org.apache.commons.io.*;

/*
 * TODO:
 * Tests
 * More error checking
 * questions:
 * 		Can I use the external library?
 */

public class TurtleParser {

	final boolean CRAIGDEBUG = true;
	
	String filePath;
	int currentLine;
	int loopCount;
	List<String> fileContents;
	File file;
	DrawableTurtle spud;
	HashMap<String, Integer> vars;
	
	private void match(String in) {
		List<String> parts = Arrays.asList(in.split(" "));
		for(String part : parts)	{
			part = part.trim();
		}
		
		if(!fileContents.get(currentLine).contains(in))	{
			System.out.println(fileContents.get(currentLine));
			System.out.println("Error! Found the part(s): ");
			for(String part : parts)	{
				System.out.println(part);
			}
			System.out.println("and not: " + in);
			System.exit(0);
		} else	{
			currentLine++;
		}
	}
	
	public void program() throws IOException	{
		spud = new DrawableTurtle();
		file = new File(filePath);
		vars = new HashMap<String, Integer>();
		currentLine = 0;
		
		fileContents = (ArrayList<String>) FileUtils.readLines(file);
				
		block();
		match("programEnd");
		spud.draw();
	}

	private void block() {
		match("begin");
		
		statementList();
		
		match("end");
	}

	private void statementList() {
		statement();
		
		if(!fileContents.get(currentLine).equals("end"))
			statementList();
	}
	
	private void statement()	{
		if(fileContents.get(currentLine).contains("loop"))
			loop();
		else if(fileContents.get(currentLine).contains("forward") 	||
				fileContents.get(currentLine).contains("turn")		||
				fileContents.get(currentLine).contains("="))
			command();
	}
	
	private void loop()	{
		match("loop");
		count();
		int resetLine = currentLine;
		for(int i = 0; i < loopCount; i++)	{
			currentLine = resetLine;
			block();
		}
	}
	
	private void command()	{
		if(fileContents.get(currentLine).contains("forward"))	{
			match("forward");
			distance();
		}
		else if(fileContents.get(currentLine).contains("turn"))	{
			match("turn");
			angle();
		}
		else if(fileContents.get(currentLine).contains("="))	{
			match("=");
			assignment();
		}
	}
	
	private void assignment()	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("="));
		int value = Integer.parseInt(parts.get(1).trim());
		
		if(value < 0)	{
			System.out.println("Number must be greater than 0 in assignments!");
			System.exit(0);
		}
		else 
			vars.put(parts.get(0).trim(), value);
	}
	
	private void count()	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+")) 	{
			loopCount = Integer.parseInt(parts.get(1));
		} 
		else if(vars.containsKey(parts.get(1)))	{
			loopCount = vars.get(parts.get(1));
		} 
		else	{
			System.out.println("Times to loop was below 0 or not found among variables");
			System.exit(0);
		}
	}
	
	private void distance()	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+"))	{
			int distance = Integer.parseInt(parts.get(1)); 
			spud.forward(distance);
		}
		else if (vars.containsKey(parts.get(1)))	{
			spud.forward(vars.get(parts.get(1)));
		}
		else	{
			System.out.println("Distance to move was below 0 or not found among variables");
			System.exit(0);
		}
	}
	
	private void angle()	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+"))	{
			int angle = Integer.parseInt(parts.get(1)); 
			spud.turn(angle);
		}
		else if (vars.containsKey(parts.get(1)))	{
			spud.turn(vars.get(parts.get(1)));
		}
		else	{
			System.out.println("Angle to turn was below 0 or not found among variables");
			System.exit(0);
		}
	}
	
	public static void main(String[] args) throws IOException {
		TurtleParser parser = new TurtleParser();
		Scanner kb = new Scanner(System.in);
		
		if(!parser.CRAIGDEBUG)	{
			System.out.println("Please enter a full file path: ");
			parser.filePath = kb.nextLine();
		} else 	{
			parser.filePath = args[0];
		}
		
		parser.program();
	}

}
