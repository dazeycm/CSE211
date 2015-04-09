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

/*
 * Craig Dazey
 * Dr. Kiper
 * 4-08-2015
 * CSE 211
 * Tester class for turtle parser.
 * Assignment 6
 */

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
		assertEquals("program", tp.program());
	}
	
	@Test
	public void testBlock()	{
		tp.currentLine = 2;
 		assertEquals("block", tp.block());
	}
	
	@Test
	public void testStatementList()	{
		tp.currentLine = 3;
		assertEquals("statement", tp.statementList());
		
	}
	
	@Test
	public void testStatementLoop()	{
		tp.currentLine = 8;
		assertEquals("loop", tp.statement());
	}
	
	@Test
	public void testStatementCommand()	{
		tp.currentLine = 5;
		assertEquals("command", tp.statement());
	}
	
	@Test
	public void testLoop()	{
		tp.currentLine = 8;
		assertEquals("looping", tp.loop());
	}
	
	@Test
	public void testCommandForward()	{
		tp.currentLine = 4;
		assertEquals("forward", tp.command());
	}
	
	@Test
	public void testCommandTurn()	{
		tp.currentLine = 5;
		assertEquals("turn", tp.command());
	}
	
	@Test
	public void testCommandAssignment()	{
		tp.currentLine = 7;
		assertEquals("assignment", tp.command());
	}
	
	@Test
	public void testAssignment()	{
		tp.currentLine = 7;
		assertEquals("assigned", tp.assignment());
	}
	
	@Test
	public void testCount()	{
		tp.currentLine = 9;
		assertEquals("loop", tp.count());
	}
	
	@Test
	public void testDistance()	{
		tp.currentLine = 5;
		assertEquals(10, tp.distance());
	}
	
	@Test
	public void testAngle()	{
		tp.currentLine = 6;
		assertEquals(90, tp.angle());
	}

}
