/*
 * TCSS 305 - Fall 2018 Assignment 5b - Race Day
 */
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import controlleraction.ToggleAction;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.Timer;
import model.Race;

/**
 * Class for controlling the buttons.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class ButtonControl extends JPanel implements PropertyChangeListener {

    /** Universal version identifier for a Serializable class. */
    private static final long serialVersionUID = 1L;

    /** Value for regular multiplier. */
    private static final int SPEED_REGULAR = 1;

    /** Value for fast multiplier. */
    private static final int SPEED_FAST = 4;

    /** Timer firing an event every 10 milliseconds. */
    private static final int SLIPPAGE_DELAY = 1;

    /** Restart button text. */
    private static final String RESTART_TEXT = "Restart";

    /** Restart button image. */
    private static final String RESTART_ICON = "./images/ic_restart.png";

    /** Play button image. */
    private static final String PLAY_ICON = "./images/ic_play.png";

    /** Pause button image. */
    private static final String PAUSE_ICON = "./images/ic_pause.png";

    /** Times one button image. */
    private static final String TIMES_ONE_ICON = "./images/ic_one_times.png";

    /** Times four button image. */
    private static final String TIMES_FOUR_ICON = "./images/ic_four_times.png";

    /** Repeat button image. */
    private static final String REPEAT_ICON = "./images/ic_repeat.png";

    /** Repeat color button image. */
    private static final String REPEAT_COLOR_ICON = "./images/ic_repeat_color.png";

    /** Clear button text. */
    private static final String CLEAR_TEXT = "Clear";

    /** Clear button image. */
    private static final String CLEAR_ICON = "./images/ic_clear.png";

    /** Exit button text. */
    private static final String EXIT_TEXT = "Exit";

    /** Load race button text. */
    private static final String LOAD_RACE_TEXT = "Load Race...";

    /** Race object in the model package. */
    private final Race myRace;

    /** Timer. */
    private final Timer myTimer;

    /** Tabbed pane in the center of the GUI. */
    private final TabbedPane myTabbedPane;

    /** List of actions for file tab. */
    private final ArrayList<Action> myFileTab;

    /** List of actions for control tab. */
    private final ArrayList<Action> myControlsTab;

    /** List of actions for help tab. */
    private final ArrayList<Action> myHelpTab;

    /** Multiplier for switching the speed. */
    private int myMutiplier;

    /** This is the slider. */
    private Slider mySlider;
    
    /** Boolean for switching the single loop and repeat loop. */
    private boolean myRaceMode;

    /**
     * Constructor to create ButtonControl objects.
     * 
     * @param theRace Race object in the model package.
     * @param theTabbedPane Tabbed pane in the center of the GUI.
     */
    public ButtonControl(final Race theRace, final TabbedPane theTabbedPane) {
        super();
        myRace = theRace;
        myFileTab = new ArrayList<Action>();
        myControlsTab = new ArrayList<Action>();
        myHelpTab = new ArrayList<Action>();
        myTimer = new Timer(SLIPPAGE_DELAY, this::timerSpeed);
        myTabbedPane = theTabbedPane;
        myMutiplier = 1;
        mySlider = new Slider(myRace);
        initActions();
    }

    /**
     * To get actions in the file tab.
     * 
     * @return List of actions in the file tab.
     */
    public ArrayList<Action> getFileTab() {
        return myFileTab;
    }

    /**
     * To get actions in the controls tab.
     * 
     * @return List of actions in the controls tab.
     */
    public ArrayList<Action> getControlsTab() {
        return myControlsTab;
    }

    /**
     * To get actions in the help tab. 
     * 
     * @return List of actions in the help tab.
     */
    public ArrayList<Action> getHelpTab() {
        return myHelpTab;
    }

    /** Helper method to initialize all the buttons to have actions. */
    private void initActions() {
       
        final Action loadFile = new ToggleAction(LOAD_RACE_TEXT, LOAD_RACE_TEXT, "", "",
                                                 this::loadFile, this::loadFile, true);
        myFileTab.add(loadFile);
        myFileTab.add(new ToggleAction(EXIT_TEXT, EXIT_TEXT, "", "", () -> exit(),
            () -> exit(), true));

        final Action reset =
                        new ToggleAction(RESTART_TEXT, RESTART_TEXT, RESTART_ICON,
                                         RESTART_ICON, this::restart, this::restart, true);
        myControlsTab.add(reset);
        myControlsTab.add(new ToggleAction("Play", "Pause", PLAY_ICON, PAUSE_ICON,
            () -> startTimer(), () -> stopTimer(), false));

        myControlsTab.add(new ToggleAction("Times One", "Times Four", TIMES_ONE_ICON,
                                           TIMES_FOUR_ICON, () -> myMutiplier = SPEED_REGULAR,
            () -> myMutiplier = SPEED_FAST, true));

        final ToggleAction repeat = new ToggleAction("Single Race", "Loop Race", REPEAT_ICON,
                                                     REPEAT_COLOR_ICON, () -> repeat(),
            () -> repeat(), true);

        myControlsTab.add(repeat);

        myControlsTab.add(new ToggleAction(CLEAR_TEXT, CLEAR_TEXT, CLEAR_ICON, CLEAR_ICON,
            () -> clear(), () -> clear(), true));
        
        
        for (final Action action : myFileTab) {
            final JButton button = new JButton(action);
            add(button);
        }

        for (final Action action : myControlsTab) {
            final JButton button = new JButton(action);
            add(button);
        }
    }

    /** This method starts the timer.*/
    private void startTimer() {
        myTimer.start();
        mySlider.setEnabled(false);
    }
    
    /** This method stops the timer.*/
    private void stopTimer() {
        myTimer.stop();
        mySlider.setEnabled(true);

    }

    /** This is for loading a file.*/
    private void loadFile() {
        final JFileChooser fileChooser = new JFileChooser("race_files");
        final int result = fileChooser.showOpenDialog(this);
        if (result == JFileChooser.APPROVE_OPTION) {
            try {
                myRace.loadRace(fileChooser.getSelectedFile());

            } catch (final IOException e) {
                JOptionPane.showMessageDialog(this, "Incorrect File Format...",
                                              "Error Loading", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    /**
     * This is for the timer to speed up.
     * 
     * @param theEvent is for the timer.
     */
    private void timerSpeed(final ActionEvent theEvent) {
        myRace.advance(SLIPPAGE_DELAY * myMutiplier);
 
    }

    /** This is for exiting the application. */
    private void exit() {
        System.exit(0);
    }

    /** This is for clearing the data output stream. */
    private void clear() {
        myTabbedPane.clearDataOutput();
        myRace.moveTo(0);
    } 

    /** This is for restarting the timer. */
    private void restart() {
        myRace.moveTo(0);
    }

    /** This is for repeating the timer.*/
    private void repeat() {
        myRaceMode = !myRaceMode;
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            final int endTime = (Integer) theEvent.getNewValue();
            if (myRaceMode && endTime == myRace.getRaceInfo().getTime() - 1) {
                myRace.moveTo(0);
            }
        }
    }

}
