
package model;

/**
 * Class to create telemetry messages and hold information about them.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class Telemetry extends AbstractMessage {
   
    /** Semicolon for toString(). */
    private static final String SEMICOLON = ":";
    /** The time of the race. */
    private int myTime;
    /** The racer ID.*/
    private int myRacerID;
    /** The distance traveled by racer. */
    private double myDistance;
    /** The number laps traveled by racer. */
    private int myLaps;

    /**
     * Constructor for the Telemetry.
     * 
     * @param theTime theTime.
     * @param theRacerID theRacerID.
     * @param theDistance theDistance.
     * @param theLaps theLaps.
     */
    public Telemetry(final int theTime, final int theRacerID, final double theDistance,
                     final int theLaps) {
        myTime = theTime;
        myRacerID = theRacerID;
        myDistance = theDistance;
        myLaps = theLaps;
    }

    @Override
    public int getTimeStamp() {
        return myTime;
    }

    /**
     * Returns the racer ID.
     * 
     * @return ID of racer.
     */
    public int getRacerID() {
        return myRacerID;
    }

    /**
     * Returns the distance.
     * 
     * @return the distance.
     */
    public double getDistance() {
        return myDistance;
    }

    /**
     * Returns the laps.
     * 
     * @return the laps.
     */
    public int getLaps() {
        return myLaps;
    }

    @Override
    public String toString() {
        return "$T:" + getTimeStamp() + SEMICOLON + getRacerID() + SEMICOLON + getDistance()
               + SEMICOLON + getLaps();
    }

}
