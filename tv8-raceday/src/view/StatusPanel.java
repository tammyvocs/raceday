
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DecimalFormat;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 * A class to create the status panel.
 * 
 * @author Tammy Vo
 * @version 08 December 2018
 */
public class StatusPanel extends JPanel implements PropertyChangeListener {

    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** The size of the status panel. */
    private static final Dimension TRACK_SIZE = new Dimension(500, 20);

    /** The separator for formatted. */
    private static final String SEPARATOR = ":";

    /** The number of milliseconds in a second. */
    private static final int MILLIS_PER_SEC = 100;

    /** The number of seconds in a minute. */
    private static final int SEC_PER_MIN = 60;

    /** The number of minute in a hour. */
    private static final int MIN_PER_HOUR = 60;

    /** A formatter to require at least 2 digits, leading 0s. */
    private static final DecimalFormat TWO_DIGIT_FORMAT = new DecimalFormat("00");

    /** A formatter to require at least 3 digits, leading 0s. */
    private static final DecimalFormat THREE_DIGIT_FORMAT = new DecimalFormat("000");

    /** A label to display time. */
    private final JLabel myLabel;

    /** Construct a Status Panel. */
    public StatusPanel() {
        super();
        myLabel = new JLabel("Time: " + formatTime(0));
        setupComponents();
    }

    /** Method to setup components for the status panel. */
    private void setupComponents() {
        setPreferredSize(TRACK_SIZE);
        setBorder(BorderFactory.createLineBorder(Color.black));
        setBackground(Color.DARK_GRAY);

        final JLabel statusLabel = new JLabel("Participants:");

        setLayout(new BorderLayout());

        statusLabel.setForeground(Color.WHITE);
        myLabel.setForeground(Color.WHITE);
        add(myLabel, BorderLayout.EAST);
        add(statusLabel, BorderLayout.WEST);

    }
    /** 
     * Formats the time. 
     * @param theTime the time.
     * @return the format of the time.
     */
    public static String formatTime(final long theTime) {
        long time = theTime;
        final long milliseconds = time % MILLIS_PER_SEC;
        time /= MILLIS_PER_SEC;
        final long seconds = time % SEC_PER_MIN;
        time /= SEC_PER_MIN;
        final long min = time % MIN_PER_HOUR;
        time /= MIN_PER_HOUR;
        return TWO_DIGIT_FORMAT.format(min) + SEPARATOR + TWO_DIGIT_FORMAT.format(seconds)
               + SEPARATOR + THREE_DIGIT_FORMAT.format(milliseconds);
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            myLabel.setText(formatTime((Integer) theEvent.getNewValue()));
        }
        
        

    }

}
