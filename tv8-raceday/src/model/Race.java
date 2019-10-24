/*
 * TCSS 305 - Fall 2018 Assignment 5a - Race Day
 */

package model;

import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeSupport;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

/**
 * Implementation the behaviors of the Race simulation.
 * 
 * @author Tammy Vo
 * @version 11 November 2018
 */

public class Race implements PropertyChangeEnabledRaceControls {

    /** The delimiter used in the ledger file format. */
    private static final String DELIMITER = ":";
    /** The start time. */
    private static final int START = -2;
    /** The error message. */
    private static final String ERROR = "Error";
    /** Expected length of a telemetry after split at the delimiter. */
    private static final int TELEMETRY_CROSSING_SIZE = 5;
    /** The regular expression for an integer. */
    private static final String INTEGER_REGEX = "\\d+";
    /** The regular expression for an floating point number (non scientific notation). */
    private static final String FLOAT_REGEX = "^-?\\\\d*\\\\.{0,1}\\\\d+$";
    /** The error message for an incorrectly formatted file. */
    private static final String FILE_FORMAT_ERROR_MESSAGE = "Error loading file.";
    /** For creating a new line. */
    private static final String NEW_LINE = "\n";
    /** List to hold the race messages. */
    private final ArrayList<ArrayList<Messages>> myRaceMessages;
    // private final Map<Integer, Boolean> myRacerToggle;
    /** List to hold the race participants and their information. */
    private final ArrayList<Racer> myRacersInfo;
    /** The property change support. */
    private final PropertyChangeSupport myPcs;
    /** RaceInfo object to hold information about the race. */
    private RaceInfo myRaceInfo;
    /** Store the Object time (the current time). */
    private int myCurrentTimeStamp;
    /** Store the number of racer ID. */
    private ArrayList<Integer> myRacersID;

    /** Constructor to instantiate the ArrayLists. */
    public Race() {
        super();
        myRaceMessages = new ArrayList<ArrayList<Messages>>();
        // myRacerToggle = new HashMap<Integer, Boolean>();
        myRacersID = new ArrayList<>();
        myRacersInfo = new ArrayList<Racer>();
        myPcs = new PropertyChangeSupport(this);
        myCurrentTimeStamp = START;

    }

    /**
     * Accessor to retrieve a RaceInfo object that holds information about the race.
     * 
     * @return RaceInfo object that holds information about the race.
     */
    public RaceInfo getRaceInfo() {
        return myRaceInfo;
    }

    /**
     * Accessor to retrieve a list of racer objects that holds information about the racers.
     * 
     * @return List of racer objects that hold information about the racers.
     */
    public ArrayList<Racer> getRacersInfo() {
        return myRacersInfo;
    }

    @Override
    public void advance() {
        advance(1);
    }

    @Override
    public void advance(final int theMillisecond) {
        changeTime(myCurrentTimeStamp + theMillisecond);
        if (myCurrentTimeStamp + theMillisecond < myRaceMessages.size()) {
            myPcs.firePropertyChange(PROPERTY_LOAD, 0,
                                     myRaceMessages.get(myCurrentTimeStamp + theMillisecond));
        }

    }

    /** 
     * Helper method to create a String to be printed on the data output.
     * 
     * @param theTime int representing the time to print.
     * @return a String to be printed on the data output.
     */
    private String dataOutputStream(final int theTime) {
        final StringBuffer result = new StringBuffer();

        for (int i = theTime; i <= theTime; i++) {
            for (int j = 0; j < myRaceMessages.get(i).size(); j++) {
                result.append(myRaceMessages.get(i).get(j).toString() + NEW_LINE);
            }
        }
        return result.toString();
    }

    @Override
    public void moveTo(final int theMillisecond) {
        if (theMillisecond < 0) {
            throw new IllegalArgumentException(ERROR);
        }
        changeTime(theMillisecond);

        if (myCurrentTimeStamp == 1) {
            sendMessage(dataOutputStream(myCurrentTimeStamp - 1));
        } else {
            sendMessage(dataOutputStream(myCurrentTimeStamp));
        }

    }

    /**
     * This method sends the message and fires a property change.
     * 
     * @param theNewValue is the new value.
     */
    private void sendMessage(final String theNewValue) {
        myPcs.firePropertyChange(PROPERTY_MESSAGE, 0, theNewValue);
    }

    /**
     * This method changes the time.
     * 
     * @param theMillisecond the milliseconds.
     */
    private void changeTime(final int theMillisecond) {
        final int old = myCurrentTimeStamp;
        myCurrentTimeStamp = theMillisecond;
        if (myCurrentTimeStamp < myRaceInfo.getTime()) {
            myPcs.firePropertyChange(PROPERTY_TIME, old, myCurrentTimeStamp);
        }
    }

    @Override
    public void toggleParticipant(final int theParticpantID, final boolean theToggle) {
        // if (myRacerToggle.containsKey(theParticpantID)) {
        // myRacerToggle.put(theParticpantID, theToggle);
        // }

        if (theToggle) {
            if (!myRacersID.contains(theParticpantID)) {
                myRacersID.add(theParticpantID);
            }
        } else {
            myRacersID.remove((Object) theParticpantID);
        }

    }

    @Override
    public void loadRace(final File theRaceFile) throws IOException {
        final Scanner input;
        final FileReader reader = new FileReader(theRaceFile);
        input = new Scanner(reader);

        myRacersInfo.clear();
        if (theRaceFile.getName().endsWith(".rce")) {
            loadHeader(input);
            loadMessages(input);

            for (int i = 0; i < myRacersInfo.size(); i++) {
                myRacersID.add(myRacersInfo.get(i).getRacerID());
            }

            myPcs.firePropertyChange(PROPERTY_CONTROL, 1, 2);
            myPcs.firePropertyChange(PROPERTY_ID, 0, myRacersID);

            // myPcs.firePropertyChange();
            input.close();

        } else {
            input.close();
            throw new IOException();

        }
    }

    /**
     * This method loads the header.
     * 
     * @param theScanner scans the line.
     * @throws IOException throws an IOException.
     */
    private void loadHeader(final Scanner theScanner) throws IOException {
        String raceName = "";
        String trackType = "";
        int trackWidth = 0;
        int trackHeight = 0;
        int lapDistance = 0;
        int raceTime = 0;
        int raceParticipants = 0;

        while (theScanner.hasNextLine()) {
            final String line = theScanner.nextLine();
            if (line.startsWith("#RACE")) {
                raceName = parseString(line, 2, 1);
            } else if (line.startsWith("#TRACK")) {
                trackType = parseString(line, 2, 1);
            } else if (line.startsWith("#WIDTH")) {
                trackWidth = parseInteger(line, 2, 1);
            } else if (line.startsWith("#HEIGHT")) {
                trackHeight = parseInteger(line, 2, 1);
            } else if (line.startsWith("#DISTANCE")) {
                lapDistance = parseInteger(line, 2, 1);
            } else if (line.startsWith("#TIME")) {
                raceTime = parseInteger(line, 2, 1);
            } else if (line.startsWith("#PARTICIPANTS")) {
                raceParticipants = parseInteger(line, 2, 1);
                parseRaceParticipants(theScanner, raceParticipants);
                break;
            } else {
                throw new IOException();
            }
        }

        myRaceInfo = new RaceInfo(raceName, trackType, trackWidth, trackHeight, lapDistance,
                                  raceTime, raceParticipants);

    }

    /**
     * This method loads the messages.
     * 
     * @param theScanner scans the line.
     * @throws IOException throws an IOException.
     */
    public void loadMessages(final Scanner theScanner) throws IOException {
        for (int i = 0; i < myRaceInfo.getTime(); i++) {
            myRaceMessages.add(new ArrayList<Messages>());
        }

        while (theScanner.hasNextLine()) {
            final String line = theScanner.nextLine();
            if (line.startsWith("$L")) {
                final String[] leaderboardArray = line.split(DELIMITER);
                final int leaderboardTime = parseInteger(line, myRacersInfo.size() + 2, 1);
                final int[] racerArray = new int[myRacersInfo.size()];
                if (leaderboardArray.length == myRacersInfo.size() + 2) {
                    int index = 0;
                    for (int i = 2; i < leaderboardArray.length; i++) {
                        racerArray[index] = Integer.parseInt(leaderboardArray[i]);
                        index++;
                    }
                }
                myRaceMessages.get(leaderboardTime).
                    add(new Leaderboard(leaderboardTime, racerArray));

            } else if (line.startsWith("$T")) {
                int time = parseInteger(line, TELEMETRY_CROSSING_SIZE, 1);
                final int racerID = parseInteger(line, TELEMETRY_CROSSING_SIZE, 2);
                final double distance = parseDouble(line, TELEMETRY_CROSSING_SIZE, 3);
                final int lap = parseInteger(line, TELEMETRY_CROSSING_SIZE, 4);
                if (time == myRaceMessages.size()) {
                    time = time - 1;
                    myRaceMessages.get(time).
                        add(new Telemetry(time + 1, racerID, distance, lap));
                } else {
                    myRaceMessages.get(time).add(new Telemetry(time, racerID, distance, lap));
                }
            } else if (line.startsWith("$C")) {
                final int crossingTime = parseInteger(line, TELEMETRY_CROSSING_SIZE, 1);
                final int racerID = parseInteger(line, TELEMETRY_CROSSING_SIZE, 2);
                final int newLap = parseInteger(line, TELEMETRY_CROSSING_SIZE, 3);
                final boolean isFinish = parseBoolean(line, TELEMETRY_CROSSING_SIZE, 4);
                myRaceMessages.get(crossingTime).
                    add(new Crossing(crossingTime, racerID, newLap, isFinish));
            } else {
                throw new IOException();
            }

        }

    }

    /**
     * Parse a BigDecimal from a String. The String is expected to split on the DELIMITER ":"
     * and find a floating point value at theIndex in the resulting String[].
     * 
     * @param theLine the String to parse
     * @param theExpectedLength the number of expected String the Line should split into
     * @param theIndex the expected location of the floating point value
     * @return the parsed floating point value as a BigDecimal
     * @throws IOException if a floating point value is not found at the given index
     */
    private double parseDouble(final String theLine, final int theExpectedLength,
                               final int theIndex)
                    throws IOException {
        final String[] parts = theLine.split(DELIMITER);
        if (parts.length != theExpectedLength && parts[theIndex].matches(FLOAT_REGEX)) {
            throw new IOException(FILE_FORMAT_ERROR_MESSAGE);

        }
        return Double.parseDouble(parts[theIndex]);
    }

    /**
     * Parse a String from a String. The String is expected to split on the DELIMITER ":" and
     * find a string value at theIndex in the resulting String[].
     * 
     * @param theLine the String to parse
     * @param theExpectedLength the number of expected String the Line should split into
     * @param theIndex the expected location of the String
     * @return the parsed String
     * @throws IOException if a String value is not found at the given index
     */
    private String parseString(final String theLine, final int theExpectedLength,
                               final int theIndex)
                    throws IOException {
        final String[] parts = theLine.split(DELIMITER);
        if (parts.length != theExpectedLength) {
            throw new IOException(FILE_FORMAT_ERROR_MESSAGE);
        }
        return parts[theIndex];
    }

    /**
     * Parse an int from a String. The String is expected to split on the DELIMITER ":" and
     * find a int value at theIndex in the resulting String[].
     * 
     * @param theLine the int to parse
     * @param theExpectedLength the number of expected String the Line should split into
     * @param theIndex the expected location of the int value
     * @return the parsed integer value as an int
     * @throws IOException if a int value is not found at the given index
     */
    private int parseInteger(final String theLine, final int theExpectedLength,
                             final int theIndex)
                    throws IOException {
        final String[] parts = theLine.split(DELIMITER);
        if (parts.length != theExpectedLength || !parts[theIndex].matches(INTEGER_REGEX)) {
            throw new IOException(FILE_FORMAT_ERROR_MESSAGE);
        }
        return Integer.parseInt(parts[theIndex]);
    }

    /**
     * Parse an boolean from a String. The String is expected to split on the DELIMITER ":" and
     * find a boolean value at theIndex in the resulting String[].
     * 
     * @param theLine the boolean to parse
     * @param theExpectedLength the number of expected String the Line should split into
     * @param theIndex the expected location of the boolean value
     * @return the parsed integer value as an boolean
     * @throws IOException if a boolean value is not found at the given index
     */
    private boolean parseBoolean(final String theLine, final int theExpectedLength,
                                 final int theIndex)
                    throws IOException {
        final String[] parts = theLine.split(DELIMITER);
        if (parts.length != theExpectedLength) {
            throw new IOException(FILE_FORMAT_ERROR_MESSAGE);
        }
        return Boolean.parseBoolean(parts[theIndex]);
    }

    /**
     * Method to parse the race participants and add them to the list myRacerInfo.
     * 
     * @param theScanner a scanner open on the race file
     * @param theRaceParticipants the number of race participants
     * @throws IOException if the race file is in the incorrect format
     */
    private void parseRaceParticipants(final Scanner theScanner, final int theRaceParticipants)
                    throws IOException {
        for (int i = 0; i < theRaceParticipants; i++) {
            final String line = theScanner.nextLine();

            final int racerID = parseInteger(line.substring(1, line.length()), 3, 0);
            final String racerName = parseString(line, 3, 1);
            final double racerInitialPos = parseDouble(line, 3, 2);

            final Racer racer = new Racer(racerID, racerName, racerInitialPos);

            // myRacerToggle.put(racerID, true);
            myRacersInfo.add(racer);
        }

    }

    @Override
    public void addPropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(theListener);
    }

    @Override
    public void addPropertyChangeListener(final String thePropertyName,
                                          final PropertyChangeListener theListener) {
        myPcs.addPropertyChangeListener(thePropertyName, theListener);
    }

    @Override
    public void removePropertyChangeListener(final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(theListener);

    }

    @Override
    public void removePropertyChangeListener(final String thePropertyName,
                                             final PropertyChangeListener theListener) {
        myPcs.removePropertyChangeListener(thePropertyName, theListener);

    }

}
