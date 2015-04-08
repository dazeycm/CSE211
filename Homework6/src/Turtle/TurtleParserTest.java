package Turtle;

import static org.junit.Assert.*;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;

import org.apache.commons.io.FileUtils;
import org.junit.BeforeClass;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class TurtleParserTest {

	static TurtleParser tp;
	
	 @Rule
	    public ExpectedException exception = ExpectedException.none();
	
	@BeforeClass
	public static void setUpBeforeClass() throws Exception {
		tp = new TurtleParser();
		tp.file = new File("C:\\Users\\Craig\\Desktop\\CSE211\\Homework6\\src\\Turtle\\test");
		tp.fileContents = (ArrayList<String>) FileUtils.readLines(new File("C:\\Users\\Craig\\Desktop\\CSE211\\Homework6\\src\\Turtle\\test"));
		tp.filePath = "C:\\Users\\Craig\\Desktop\\CSE211\\Homework6\\src\\Turtle\\test";
		tp.currentLine = 0;
		tp.spud = new DrawableTurtle();
		tp.vars = new HashMap<String, Integer>();
	}

	@Test
	public void testMatch() {
		tp.currentLine = 0;
		assertTrue(tp.match("hello"));
	}
	
	@Test
	public void testProgram()	{
		tp.currentLine = 2;
		try {
			assertEquals("program", tp.program());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testBlock()	{
		tp.currentLine = 2;
		try	{
 			assertEquals("block", tp.block());
		} catch (Exception e)	{
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStatementList()	{
		tp.currentLine = 3;
		try {
			assertEquals("statement", tp.statementList());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testStatementLoop()	{
		tp.currentLine = 8;
		try {
			assertEquals("loop", tp.statement());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testStatementCommand()	{
		tp.currentLine = 5;
		try {
			assertEquals("command", tp.statement());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testLoop()	{
		tp.currentLine = 8;
		try {
			assertEquals("looping", tp.loop());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCommandForward()	{
		tp.currentLine = 4;
		try {
			assertEquals("forward", tp.command());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCommandTurn()	{
		tp.currentLine = 5;
		try {
			assertEquals("turn", tp.command());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testCommandAssignment()	{
		tp.currentLine = 7;
		try {
			assertEquals("assignment", tp.command());
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	}
	
	@Test
	public void testAssignment()	{
		tp.currentLine = 7;
		try {
			assertEquals("assigned", tp.assignment());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testCount()	{
		tp.currentLine = 9;
		try {
			assertEquals("loop", tp.count());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testDistance()	{
		tp.currentLine = 5;
		try {
			assertEquals(10, tp.distance());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Test
	public void testAngle()	{
		tp.currentLine = 6;
		try {
			assertEquals(90, tp.angle());
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
