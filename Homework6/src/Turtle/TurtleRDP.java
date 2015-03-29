package Turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Arrays;
import java.util.Scanner;

//C:\Users\Craig\Desktop\test.txt

public class TurtleRDP {
	
	String current;
	String filePath;
	Scanner kb = new Scanner(System.in);
	Scanner fileInput;
	DrawableTurtle spud;
	boolean inLoop = false;
	boolean alreadyMatchedBegin = false;
	boolean alreadyMatchedEnd = false;
	
	public void match(String in)	{
		if(!current.equals(in))	{
			System.out.println("Error! Found: " + current + " instead of: " + in);
			System.exit(0);
		}
	}
	
	public void program()	{
		spud = new DrawableTurtle();
		
		try {
			fileInput = new Scanner(new File(filePath));
		} catch (FileNotFoundException e) {
			System.out.println("Invalid filename");
			System.exit(0);
		}
		
		block();
		current = fileInput.nextLine();
		match("programEnd");
		spud.draw();
	}
	
	public void block()	{
		if(!inLoop && !alreadyMatchedBegin)	{
			current = fileInput.nextLine();
			match("begin");
			alreadyMatchedBegin = true;
		}
		
		current = fileInput.nextLine();
		statementList();
		
		if(!inLoop && !alreadyMatchedBegin)	{
			match("end");
			alreadyMatchedEnd = true;
		}
	}
	
	public void statementList()	{
		statement();
		current = fileInput.nextLine();
		if(!current.equals("end"))	{
			statementList();
		}
		
	}
	
	public void statement()	{
		if(current.contains("loop")){
			count();
		}
		else {
			command();
		}
		
	}
	
	public void command()	{
		if(current.contains("forward"))	{
			distance();	
		}
		else if(current.contains("turn"))	{
			angle();
		}
		else	{
			System.out.println("Error! Found: " + current + " instead of: forward, turn, loop, or end");
			System.exit(0);
		}
	}
	
	public void distance()	{
		String[] parts = current.split("\t");
		int distance = number(parts[1]);
		
		if (distance < 0)	{
			System.out.println("Distance must be greater than 0");
			System.exit(0);
		}
		spud.forward(distance);
	}
	
	public void angle()	{
		String[] parts = current.split("\t");
		int angle = number(parts[1]);
		
		if (angle < 0)	{
			System.out.println("Angle must be greater than 0");
			System.exit(0);
		}
		spud.turn(angle);
	}
	
	public void count()	{
		String[] parts = current.split("\t");
		int count = number(parts[1]);
		inLoop = true;
		for(int i = 0; i < count; i++)	{
			block();
		}
		inLoop = false;
		alreadyMatchedBegin = false;
		alreadyMatchedEnd = false;
	}
	public int number(String num)	{
		int ret = Integer.parseInt(num);
		if(ret < 0)	{
			System.out.println("Number must be greater than 0");
			System.exit(0);
		}
		return ret;
	}
	
	public static void main(String[] args) {
		TurtleRDP parse = new TurtleRDP();
		System.out.println("Please enter a full file path: ");
		parse.filePath = parse.kb.nextLine();
		parse.program();
	}

}
