package Turtle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

//C:\Users\Craig\Desktop\test.txt

//Dont actually call block in loop. Is this okay? 
//Test
//Header comments

public class TurtleRDP {
	
	final boolean CRAIGDEBUG = true;
	
	String current;
	String filePath;
	Scanner fileInput;
	Scanner kb = new Scanner(System.in);
	DrawableTurtle spud;
	boolean inLoop = false;
	ArrayList<String> statements = new ArrayList<String>();
	
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
		current = fileInput.nextLine();
		match("begin");
		
		statementList();
		
		match("end");
	}
	
	public void statementList()	{
		current = fileInput.nextLine();
		statement();
		current = fileInput.nextLine();
		if(!current.equals("end"))	{
			statementList();
		}
		
	}
	
	public void statement()	{
		if(current.contains("loop")){
			doStatements();
			inLoop = true;
			count();
		}
		else {
			command();
		}
		
	}
	
	private void doStatements() {
		//do statements here
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
	
	public void count()	{
		String[] parts = current.split("\t");
		int count = number(parts[1]);
		for(int i = 0; i < count; i++)	{
			block();
		}
	}
	
	public void distance()	{
		String[] parts = current.split("\t");
		int distance = number(parts[1]);
		
		if (distance < 0)	{
			System.out.println("Distance must be greater than 0");
			System.exit(0);
		}
		statements.add("forward " + distance);
	}
	
	public void angle()	{
		String[] parts = current.split("\t");
		int angle = number(parts[1]);
		
		if (angle < 0)	{
			System.out.println("Angle must be greater than 0");
			System.exit(0);
		}
		statements.add("turn " + angle);
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
		if(!parse.CRAIGDEBUG)	{
			System.out.println("Please enter a full file path: ");
			parse.filePath = parse.kb.nextLine();
		} else 	{
			parse.filePath = args[0];
		}
		parse.program();
	}

}
