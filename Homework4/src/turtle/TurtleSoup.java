/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */

/**
 * Craig Dazey
 * Dr. Kiper
 * 2-23-2015
 * CSE 211
 * Follow requirements set by others to implement code, and also use existing documentation to use provide methods.
 * Assignment 4
 */

package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class TurtleSoup {

	/**
	 * Draw a square.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawSquare(Turtle turtle, int sideLength) {
		for (int i = 0; i < 4; i++) {
			turtle.forward(sideLength);
			turtle.turn(90);
		}
	}

	/**
	 * Draws a circle of an arbitrary size.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the circle will
	 *            shrink.
	 */
	public static void drawCircle(Turtle turtle, int shrinkFactor) {
		for (int i = 0; i < 360 / shrinkFactor; i++) {
			turtle.forward(1);
			turtle.turn(shrinkFactor);
		}
	}

	/**
	 * Draws a semi-circle of an arbitrary size.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the semi-circle
	 *            will shrink.
	 */
	public static void drawHalfCircle(Turtle turtle, int shrinkFactor) {
		for (int i = 0; i < 180 / shrinkFactor; i++) {
			turtle.forward(1);
			turtle.turn(shrinkFactor);
		}
		turtle.turn(270);
	}

	/**
	 * Draws a quarter-circle of an arbitrary size.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the quarter-circle
	 *            will shrink.
	 */
	public static void drawQuarterCircle(Turtle turtle, int shrinkFactor) {
		for (int i = 0; i < 90 / shrinkFactor; i++) {
			turtle.forward(1);
			turtle.turn(shrinkFactor);
		}
		turtle.turn(270);
	}

	/**
	 * Determine inside angles of a regular polygon.
	 * 
	 * There is a simple formula for calculating the inside angles of a polygon;
	 * you should derive it and use it here.
	 * 
	 * @param sides
	 *            number of sides, where sides must be > 2
	 * @return angle in degrees, where 0 <= angle < 360
	 */
	public static double calculateRegularPolygonAngle(int sides) {
		return (double) 180 * (sides - 2) / sides;
	}

	/**
	 * Determine number of sides given the size of interior angles of a regular
	 * polygon.
	 * 
	 * There is a simple formula for this; you should derive it and use it here.
	 * Make sure you *properly round* the answer before you return it (see
	 * java.lang.Math). HINT: it is easier if you think about the exterior
	 * angles.
	 * 
	 * @param angle
	 *            size of interior angles in degrees
	 * @return the integer number of sides
	 */
	public static int calculatePolygonSidesFromAngle(double angle) {
		double extAngle = 180 - angle;
		return (int) Math.round(360 / extAngle);

	}

	/**
	 * Given the number of sides, draw a regular polygon.
	 * 
	 * (0,0) is the lower-left corner of the polygon; use only right-hand turns
	 * to draw.
	 * 
	 * @param turtle
	 *            the turtle context
	 * @param sides
	 *            number of sides of the polygon to draw
	 * @param sideLength
	 *            length of each side
	 */
	public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
		double angle = calculateRegularPolygonAngle(sides);
		for (int i = 0; i < sides; i++) {
			turtle.forward(sideLength);
			turtle.turn(180 - angle);
		}
	}

	/**
	 * Given the current direction, current location, and a target location,
	 * calculate the heading towards the target point.
	 * 
	 * The return value is the angle input to turn() that would point the turtle
	 * in the direction of the target point (targetX,targetY), given that the
	 * turtle is already at the point (currentX,currentY) and is facing at angle
	 * currentHeading. The angle must be expressed in degrees, where 0 <= angle
	 * < 360.
	 *
	 * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math
	 * libraries
	 * 
	 * @param currentHeading
	 *            current direction as clockwise from north
	 * @param currentX
	 *            currentY current location
	 * @param targetX
	 *            targetY target point
	 * @return adjustment to heading (right turn amount) to get to target point,
	 *         must be 0 <= angle < 360.
	 */
	public static double calculateHeadingToPoint(double currentHeading,
			int currentX, int currentY, int targetX, int targetY) {
		double theta = Math.toDegrees(Math.atan2(targetY - currentY, targetX - currentX));
		double normalizeTheta = 90 - theta; // subtract 90 because the initial heading points north and not east
		double correctedTheta = (360 + (normalizeTheta - currentHeading)) % 360; // take into account current heading
		return correctedTheta;
	}

	/**
	 * Given a sequence of points, calculate the heading adjustments needed to
	 * get from each point to the next.
	 * 
	 * Assumes that the turtle starts at the first point given, facing up (i.e.
	 * 0 degrees). For each subsequent point, assumes that the turtle is still
	 * facing in the direction it was facing when it moved to the previous
	 * point. You should use calculateHeadingToPoint() to implement this
	 * function.
	 * 
	 * @param xCoords
	 *            list of x-coordinates (must be same length as yCoords)
	 * @param yCoords
	 *            list of y-coordinates (must be same length as xCoords)
	 * @return list of heading adjustments between points, of size (# of points)
	 *         - 1.
	 */
	public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
		ArrayList<Double> headings = new ArrayList<Double>();
		for (int i = 0; i < xCoords.size() - 1; i++) {
			if (i == 0) {
				headings.add(calculateHeadingToPoint(0, xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1)));
			} else {
				headings.add(calculateHeadingToPoint(headings.get(i - 1), xCoords.get(i), yCoords.get(i), xCoords.get(i + 1), yCoords.get(i + 1)));
			}
		}
		return headings;
	}

	/**
	 * Draw your personal, custom art.
	 * 
	 * Many interesting images can be drawn using the simple implementation of a
	 * turtle. For this function, draw something interesting; the complexity can
	 * be as little or as much as you want. We'll be peer-voting on the
	 * different images, and the highest-rated one will win a prize.
	 * 
	 * @param turtle
	 *            the turtle context
	 */
	public static void drawPersonalArt(Turtle turtle) {
		int numClouds = 5;
		int degrees = 90;

		Random rand = new Random();
		for (int i = 0; i < numClouds; i++) {
			turtle.color(PenColor.GRAY);
			drawRandomCloud(turtle);
			turtle.turn(degrees);
			turtle.color(PenColor.YELLOW);
			degrees += 90;
			turtle.forward(120);
		}
	}

	/**
	 * Draws a random cloud from one of four preset clouds.
	 * 
	 * @param turtle
	 *            the turtle to move
	 */
	public static void drawRandomCloud(Turtle turtle) {
		Random rand = new Random();
		int chance = rand.nextInt(100);
		int shrinkFactor = 5;

		if (chance > 75)
			drawCloud1(turtle, shrinkFactor);
		else if (chance > 50)
			drawCloud2(turtle, shrinkFactor);
		else if (chance > 25)
			drawCloud3(turtle, shrinkFactor);
		else
			drawCloud4(turtle, shrinkFactor);
	}

	/**
	 * Draws a cloud.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the circle will
	 *            shrink.
	 */
	public static void drawCloud4(Turtle turtle, int shrinkFactor) {
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
	}

	/**
	 * Draws a cloud.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the circle will
	 *            shrink.
	 */
	public static void drawCloud3(Turtle turtle, int shrinkFactor) {
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
	}

	/**
	 * Draws a cloud.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the circle will
	 *            shrink.
	 */
	public static void drawCloud2(Turtle turtle, int shrinkFactor) {
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
	}

	/**
	 * Draws a cloud.
	 * 
	 * @param turtle
	 *            the turtle to move
	 * @param shrinkFactor
	 *            is an arbitrary number. As it gets bigger, the circle will
	 *            shrink.
	 */
	public static void drawCloud1(Turtle turtle, int shrinkFactor) {
		drawHalfCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawHalfCircle(turtle, shrinkFactor);
		drawQuarterCircle(turtle, shrinkFactor);
	}

	/**
	 * Main method.
	 * 
	 * This is the method that runs when you run "java TurtleSoup".
	 */
	public static void main(String args[]) {
		DrawableTurtle turtle = new DrawableTurtle();

		// drawSquare(turtle, 40);
		// drawRegularPolygon(turtle, 30, 40);
		drawPersonalArt(turtle);

		// draw the window
		turtle.draw();
	}

}
