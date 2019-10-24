
package model;

/**
 * Class to create leaderboard messages and hold information about them.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class Leaderboard implements Messages {

    /** Semicolon for toString(). */
    private static final String SEMICOLON = ":";
    /** The time of the race. */
    private int myTime;
    /** The int array to hold the id of racers. */
    private int[] myRacerID;

    /**
     * Constructor for leaderboard message object.
     * 
     * @param theTime for the Race.
     * @param theRacerID for the Race.
     */
    public Leaderboard(final int theTime, final int[] theRacerID) {
        myTime = theTime;
        myRacerID = theRacerID;
    }

    /** 
     * Get the racer ID.
     * @return the racer ID.
     */
    public int[] getRacerID() {
        return myRacerID;
    }

    @Override
    public int getTimeStamp() {
        return myTime;
    }

    @Override
    public String toString() {
        final StringBuffer result = new StringBuffer("$L:" + getTimeStamp());
        for (int i = 0; i < myRacerID.length; i++) {
            result.append(SEMICOLON + myRacerID[i]);
        }
        return result.toString();
    }
}
