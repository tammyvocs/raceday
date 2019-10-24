package model;
/**
 * A common interface to be shared between leaderboard, crossing,
 * and telemetry messages. Will be necessary to save those three
 * types of messages in an ArrayList.
 *
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public interface Messages {
    /**
     * To retrieve the time.
     * 
     * @return the time.
     */
    int getTimeStamp();
}
