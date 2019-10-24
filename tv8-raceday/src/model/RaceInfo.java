
package model;

/**
 * Class for race info.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class RaceInfo {

    /** The race name.*/
    private String myRaceName;
    /** The race track geometry.*/
    private String myTrack;
    /** The race track width ratio.*/
    private int myWidth;
    /** The race track height ratio.*/
    private int myHeight;
    /** The distance of a single lap.*/
    private int myDistance;
    /** The total race time in milliseconds.*/
    private int myTime;
    /** The number of participants.*/
    private int myParticipants;

    /**
     * Constructor of RaceInfo. 
     * 
     * @param theRaceName the name of the race. 
     * @param theTrack the type of the track.
     * @param theWidth the width of the track.
     * @param theHeight the height of the track.
     * @param theDistance the distance of a single lap of the race. 
     * @param theTime the race time. 
     * @param theParticipants the of racers. 
     */
    public RaceInfo(final String theRaceName, final String theTrack, final int theWidth, 
                    final int theHeight, final int theDistance, final int theTime,
                    final int theParticipants) {
        
        myRaceName = theRaceName;
        myTrack = theTrack;
        myWidth = theWidth;
        myHeight = theHeight;
        myDistance = theDistance;
        myTime = theTime;
        myParticipants = theParticipants;

    }
    /**
     * @return returns the race name.
     */
    public String getRaceName() {
        return myRaceName;
    }
    /**
     * @return returns the track.
     */
    public String getTrack() {
        return myTrack;
    }
    /**
     * @return returns the width of the track.
     */
    public int getWidth() {
        return myWidth;
    }
    /**
     * @return returns the height of the track.
     */
    public int getHeight() {
        return myHeight;
    }
    /**
     * @return returns the distance of the race.
     */
    public int getDistance() {
        return myDistance;
    }
    /**
     * @return returns the time of the race.
     */
    public int getTime() {
        return myTime;
    }
    /**
     * @return returns the number of participants.
     */
    public int getParticipants() {
        return myParticipants;
    }
}
