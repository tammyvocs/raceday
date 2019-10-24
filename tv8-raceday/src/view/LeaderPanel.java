
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_CONTROL;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.BorderFactory;
import javax.swing.JLabel;
import javax.swing.JPanel;
import model.Leaderboard;
import model.Messages;
import model.Race;

/**
 * A class to create the leader board panel.
 * 
 * @author Tammy Vo
 * @version 08 December 2018
 */
public class LeaderPanel extends JPanel implements PropertyChangeListener {

    /** A generated serial version UID for object Serialization. */
    private static final long serialVersionUID = 1L;
    /** The size of the Leader board Panel. */
    private static final Dimension LEADER_SIZE = new Dimension(200, 400);
    /** The size of the text inside the panel. */
    private static final int TEXT_SIZE = 12;
    /** A collection of the messages. */
    private ArrayList<Messages> myMessages;
    /** Map for the leader boards. */
    private Map<Integer, JPanel> myLeadBoards;
    /** The race. */
    private Race myRace;

    /** 
     * Construct a Leader board Panel. 
     * @param theRace the race.
     * */
    public LeaderPanel(final Race theRace) {
        setPreferredSize(LEADER_SIZE);
        setBackground(Color.WHITE);
        myMessages = new ArrayList<Messages>();
        myRace = theRace;
        myLeadBoards = new HashMap<Integer, JPanel>();

    }

    /** Set up the components for the leader board panel. */
    private void setupComponents() {
        setLayout(new GridLayout(myRace.getRaceInfo().getParticipants(), 1));

        for (int i = 0; i < myRace.getRaceInfo().getParticipants(); i++) {

            final JPanel racerPanel = new JPanel();
            final JLabel racerLabel =
                            new JLabel("RACER: " + myRace.getRacersInfo().get(i).getRacerID()
                                       + " " + myRace.getRacersInfo().get(i).getRacerName());
            racerLabel.setFont(new Font("Courier New", Font.BOLD, TEXT_SIZE));
            racerPanel.add(racerLabel);
            racerPanel.setBackground(myRace.getRacersInfo().get(i).getColor());
            racerPanel.setBorder(BorderFactory.createLineBorder(Color.black));

            myLeadBoards.put(myRace.getRacersInfo().get(i).getRacerID(), racerPanel);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {

        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            for (int i = 0; i < myMessages.size(); i++) {
                if (myMessages.get(i).getClass() == Leaderboard.class) {
                    removeAll();
                    final Leaderboard leaderboard = (Leaderboard) myMessages.get(i);
                    for (int racerID : leaderboard.getRacerID()) {
                        add(myLeadBoards.get(racerID));
                    }
                    revalidate();
                }

            }

        }

        if (PROPERTY_CONTROL.equals(theEvent.getPropertyName())) {
            setupComponents();
            for (JPanel panel : myLeadBoards.values()) {
                add(panel);
            }
            revalidate();
        }

        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            myMessages = (ArrayList<Messages>) theEvent.getNewValue();

        }

    }

}
