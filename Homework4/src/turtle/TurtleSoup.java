/* Copyright (c) 2007-2014 MIT 6.005 course staff, all rights reserved.
 * Redistribution of original or derived work requires permission of course staff.
 */
package turtle;

import java.lang.Math;
import java.util.List;
import java.util.ArrayList;

public class TurtleSoup {

    /**
     * Draw a square.
     * 
     * @param turtle the turtle context
     * @param sideLength length of each side
     */
    public static void drawSquare(Turtle turtle, int sideLength) {
        for(int i = 0; i < 4; i++)	{
        	turtle.forward(sideLength);
        	turtle.turn(90);
        }
    }
    
    /**
     * Draws a circle that is about the size of the diameter.
     * 
     * @param turtle the turtle to move
     * @param diameter is not actually the diameter, but it's pretty close
     */
    public static void drawCircle(Turtle turtle, int diameter)	{
    	for (int i = 0; i < 360/diameter; i++)	{
    		turtle.forward(1);
    		turtle.turn(diameter);
    	}
    }

    /**
     * Determine inside angles of a regular polygon.
     * 
     * There is a simple formula for calculating the inside angles of a polygon;
     * you should derive it and use it here.
     * 
     * @param sides number of sides, where sides must be > 2
     * @return angle in degrees, where 0 <= angle < 360
     */
    public static double calculateRegularPolygonAngle(int sides) {
        return (double)180 * (sides - 2)/sides;
    }

    /**
     * Determine number of sides given the size of interior angles of a regular polygon.
     * 
     * There is a simple formula for this; you should derive it and use it here.
     * Make sure you *properly round* the answer before you return it (see java.lang.Math).
     * HINT: it is easier if you think about the exterior angles.
     * 
     * @param angle size of interior angles in degrees
     * @return the integer number of sides
     */
    public static int calculatePolygonSidesFromAngle(double angle) {
        double extAngle = 180 - angle;
        return (int) Math.round(360/extAngle);
        
    }

    /**
     * Given the number of sides, draw a regular polygon.
     * 
     * (0,0) is the lower-left corner of the polygon; use only right-hand turns to draw.
     * 
     * @param turtle the turtle context
     * @param sides number of sides of the polygon to draw
     * @param sideLength length of each side
     */
    public static void drawRegularPolygon(Turtle turtle, int sides, int sideLength) {
    	double angle = calculateRegularPolygonAngle(sides);
    	for(int i = 0; i < sides; i++)	{
    		turtle.forward(sideLength);
    		turtle.turn(180 - angle);
    	}
    }

    /**
     * Given the current direction, current location, and a target location, calculate the heading
     * towards the target point.
     * 
     * The return value is the angle input to turn() that would point the turtle in the direction of
     * the target point (targetX,targetY), given that the turtle is already at the point
     * (currentX,currentY) and is facing at angle currentHeading. The angle must be expressed in
     * degrees, where 0 <= angle < 360. 
     *
     * HINT: look at http://en.wikipedia.org/wiki/Atan2 and Java's math libraries
     * 
     * @param currentHeading current direction as clockwise from north
     * @param currentX currentY current location
     * @param targetX targetY target point
     * @return adjustment to heading (right turn amount) to get to target point,
     *         must be 0 <= angle < 360.
     */
    public static double calculateHeadingToPoint(double currentHeading, int currentX, int currentY,
                                                 int targetX, int targetY) {
    	double theta = Math.toDegrees(Math.atan2(targetY - currentY, targetX - currentX));
    	double adjustedTheta = 90 - theta;		//subtract 90 because the heading points north and not east
    	double correctedTheta = (360 + (adjustedTheta - currentHeading)) % 360;		//take into account current heading
    	return correctedTheta;
    }

    /**
     * Given a sequence of points, calculate the heading adjustments needed to get from each point
     * to the next.
     * 
     * Assumes that the turtle starts at the first point given, facing up (i.e. 0 degrees).
     * For each subsequent point, assumes that the turtle is still facing in the direction it was
     * facing when it moved to the previous point.
     * You should use calculateHeadingToPoint() to implement this function.
     * 
     * @param xCoords list of x-coordinates (must be same length as yCoords)
     * @param yCoords list of y-coordinates (must be same length as xCoords)
     * @return list of heading adjustments between points, of size (# of points) - 1.
     */
    public static List<Double> calculateHeadings(List<Integer> xCoords, List<Integer> yCoords) {
        ArrayList<Double> headings = new ArrayList<Double>();
        for(int i = 0; i < xCoords.size() - 1; i++)	{
        	if(i == 0)	{
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
     * Many interesting images can be drawn using the simple implementation of a turtle.  For this
     * function, draw something interesting; the complexity can be as little or as much as you want.
     * We'll be peer-voting on the different images, and the highest-rated one will win a prize. 
     * 
     * @param turtle the turtle context
     */
    public static void drawPersonalArt(Turtle turtle) {
        throw new RuntimeException("implement me!");
    }

    /**
     * Main method.
     * 
     * This is the method that runs when you run "java TurtleSoup".
     */
    public static void main(String args[]) {
        DrawableTurtle turtle = new DrawableTurtle();
        
        
        //drawSquare(turtle, 40);
        drawCircle(turtle, 10);
        drawCircle(turtle, 10);
        drawCircle(turtle, 10);
        drawCircle(turtle, 10);
        drawCircle(turtle, 10);
        //drawRegularPolygon(turtle, 30, 40);

        // draw the window
        turtle.draw();
    }

}