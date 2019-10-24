package model;

/**
 * Abstract class created of the messages.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 */
public abstract class AbstractMessage implements Messages {
    
    /** The time stamp.*/
    private int myTimeStamp;

    /**
     * Returns the time stamp.
     * 
     * @return the time stamp.
     */
    public int getTimeStamp() {
        return myTimeStamp;
    }

}
