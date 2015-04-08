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
 * Craig Dazey
 * Dr. Kiper
 * 4-08-2015
 * CSE 211
 * Writing specifications and tests, as well as a recursive descent parser. 
 * Assignment 6
 */

/*
 * TODO:
 * Tests
 * More error checking
 * "Prolog comments"
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
	
	/**
	 * 
	 * @param in is a string that you want to match
	 * @return true if the currentline of the file contains a the string in. false if the currentline
	 * does not contains the string in.
	 * @modifies nothing
	 * 
	 * Match looks for a string inside of the currentLine
	 */
	public boolean match(String in) {
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
			System.out.println("and not: " + in + " on line " + currentLine);
			return false;
		} else	{
			currentLine++;
			return true;
		}
	}
	
	/**
	 * 
	 * @return the string "block" if the method reached its end.
	 * @throws Exception if "programEnd" is not matched in the currentLine
	 * @modifies fileContents
	 */
	public String program() throws Exception	{
		spud = new DrawableTurtle();
		file = new File(filePath);
		vars = new HashMap<String, Integer>();
		currentLine = 0;
		
		fileContents = (ArrayList<String>) FileUtils.readLines(file);
				
		block();
		if (!match("programEnd"))
			throw new Exception("Syntax error");
		spud.draw();
		return "block";
	}

	/**
	 * 
	 * @return the string "statementlist" is the method reaches its end
	 * @throws Exception if it fails to match "being" or "end" in the currentline
	 */
	public String block() throws Exception {
		if(!match("begin"))
			throw new Exception("Syntax error");
		
		statementList();
		
		if(!match("end"))
			throw new Exception("Syntax error");
		return "statementlist";
	}

	public String statementList() throws Exception {
		statement();
		
		if(!fileContents.get(currentLine).equals("end"))	{
			statementList();
		}
		
		return "statement";
	}
	
	public String statement() throws Exception	{
		if(fileContents.get(currentLine).contains("loop"))	{
			loop();
			return "loop";
		}
		else if(fileContents.get(currentLine).contains("forward") 	||
				fileContents.get(currentLine).contains("turn")		||
				fileContents.get(currentLine).contains("="))	{
					command();
					return "command";
		}
		else	{
			throw new Exception("Expected statement on " + currentLine + " but didn't find one");
		}
	}
	
	public String loop() throws Exception	{
		if(!match("loop"))
			throw new Exception("Syntax error");
		count();
		int resetLine = currentLine;
		for(int i = 0; i < loopCount; i++)	{
			currentLine = resetLine;
			block();
		}
		return "looping";
	}
	
	public String command() throws Exception	{
		if(fileContents.get(currentLine).contains("forward"))	{
			if(!match("forward"))
				throw new Exception("Syntax error");
			distance();
			return "forward";
		}
		else if(fileContents.get(currentLine).contains("turn"))	{
			if(!match("turn"))
				throw new Exception("Syntax error");
			angle();
			return "turn";
		}
		else if(fileContents.get(currentLine).contains("="))	{
			if(!match("="))
				throw new Exception("Syntax error");
			assignment();
			return "assignment";
		}
		else	{
			throw new Exception("Expected to find command on line " + currentLine + " but didn't.");
		}
	}
	
	public String assignment() throws Exception	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("="));
		int value = Integer.parseInt(parts.get(1).trim());
		
		if(value < 0)	{
			throw new Exception("Number in assignment on line " + (currentLine - 1) + " must be greater than 0");
		}
		else {
			vars.put(parts.get(0).trim(), value);
			return "assigned";
		}
	}
	
	public String count() throws Exception	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+")) 	{
			loopCount = Integer.parseInt(parts.get(1));
			return "looping";
		} 
		else if(vars.containsKey(parts.get(1)))	{
			loopCount = vars.get(parts.get(1));
			return "looping";
		} 
		else	{
			throw new Exception("Times to loop was below 0 or not found among variables on line " + (currentLine - 1));
		}
	}
	
	public int distance() throws Exception	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+"))	{
			int distance = Integer.parseInt(parts.get(1)); 
			spud.forward(distance);
			return distance;
		}
		else if (vars.containsKey(parts.get(1)))	{
			spud.forward(vars.get(parts.get(1)));
			return vars.get(parts.get(1));
		}
		else	{
			throw new Exception("Distance to move was below 0 or not found among variables on line " + (currentLine - 1));
		}
	}
	
	public int angle() throws Exception	{
		List<String> parts = Arrays.asList(fileContents.get(currentLine - 1).split("\t"));
		
		if(parts.get(1).matches("[0-9]+"))	{
			int angle = Integer.parseInt(parts.get(1)); 
			spud.turn(angle);
			return angle;
		}
		else if (vars.containsKey(parts.get(1)))	{
			spud.turn(vars.get(parts.get(1)));
			return vars.get(parts.get(1));
		}
		else	{
			throw new Exception("Angle to turn was below 0 or not found among variables on line " + (currentLine - 1));
		}
	}
	
	public static void main(String[] args) throws Exception {
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
