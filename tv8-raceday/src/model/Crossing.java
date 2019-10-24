
package model;

/**
 * Class to create crossing messages and hold information about them.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class Crossing implements Messages {

    /** Semicolon for toString(). */
    private static final String SEMICOLON = ":";
    /** The time of the race. */
    private int myTime;
    /** The int array to hold the id of racers. */
    private int myRacerID;
    /** The int of the new lap. */
    private int myNewLap;
    /** The boolean for when the race is finished. */
    private boolean myFinish;

    /**
     * Constructor for crossing objects.
     * 
     * @param theTime the time of the crossing message.
     * @param theRacerID the ID of the racer.
     * @param theNewLap the new lap of the racer.
     * @param theFinish if the race is finished or not.
     */
    public Crossing(final int theTime, final int theRacerID, final int theNewLap,
                    final boolean theFinish) {
        myTime = theTime;
        myRacerID = theRacerID;
        myNewLap = theNewLap;
        myFinish = theFinish;
    }

    @Override
    public int getTimeStamp() {
        return myTime;
    }

    /**
     * Returns the racer ID.
     * 
     * @return the racer ID.
     */
    public int getRacerID() {
        return myRacerID;
    }

    /**
     * Returns the new lap.
     * 
     * @return the new lap.
     */
    public int getNewLap() {
        return myNewLap;
    }

    /**
     * 
     * @return if the race is finished.
     */
    public boolean isFinished() {
        return myFinish;
    }

    @Override
    public String toString() {
        return "$C:" + getTimeStamp() + SEMICOLON + getRacerID() + SEMICOLON + getNewLap()
               + SEMICOLON + isFinished();
    }
}
