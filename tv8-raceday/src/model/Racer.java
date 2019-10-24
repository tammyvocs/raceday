
package model;

import java.awt.Color;
import java.util.Random;

/**
 * Class to create the Racer.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class Racer {

    /** For the random colors. */
    private static final Random RAND = new Random();
    /** The ID of a racer. */
    private int myRacerID;
    /** The name of a racer. */
    private String myRacerName;
    /** The initial starting position of the racer. */
    private double myRacerInitialPos;
    /** The colors for the racers. */
    private Color myColor;

    /**
     * Constructor to create a Racer object that stores information about the racer.
     * 
     * @param theRacerID the ID of the racer.
     * @param theRacerName the name of the racer.
     * @param theRacerInitialPos the initial starting position of the racer.
     */
    public Racer(final int theRacerID, final String theRacerName,
                 final double theRacerInitialPos) {
        myRacerID = theRacerID;
        myRacerName = theRacerName;
        myRacerInitialPos = theRacerInitialPos;
        myColor = setColor();
    }

    /**
     * Accessor for the racer ID.
     * 
     * @return the ID of the racer
     */
    public int getRacerID() {
        return myRacerID;
    }

    /**
     * Accessor for the racer name.
     * 
     * @return the name of the racer
     */
    public String getRacerName() {
        return myRacerName;
    }

    /**
     * Accessor for the initial position of the racer.
     * 
     * @return the initial position of the racer
     */
    public double getRacerInitialPos() {
        return myRacerInitialPos;
    }

    /** 
     * Sets the color.
     * @return the color.
     */
    public Color setColor() {
        final int r = RAND.nextInt(155) + 100;
        final int b = RAND.nextInt(155) + 100;
        final int g = RAND.nextInt(155) + 100;

        return new Color(r, b, g);
    }

    /** 
     * Gets the color.
     * @return the color.
     */
    public Color getColor() {
        return myColor;

    }
}
