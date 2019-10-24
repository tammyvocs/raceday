
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_CONTROL;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE;
//import static model.PropertyChangeEnabledRaceControls.PROPERTY_ID;

import java.awt.GridLayout;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JCheckBox;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import model.Messages;
import model.Race;

/**
 * Class created for TabbedPane.
 *
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class TabbedPane extends JTabbedPane implements PropertyChangeListener {
  
    /** Universal version identifier for a Serializable class. */
    private static final long serialVersionUID = 1L;
    /** Number of rows. */
    private static final int TEXT_AREA_ROWS = 10;
    /** Number of columns. */
    private static final int TEXT_AREA_COLUMNS = 50;
    /** List for check box. */
    private final List<JCheckBox> myCheckBoxList;
    /** JTextArea to display race participants. */
    private final JPanel myRaceParticipants;
    /** JTextArea to display data output. */
    private final JTextArea myDataOutput;
    /** Race object in the model package. */
    private final Race myRace;
    /** Check box created. */
    private JCheckBox myCheckBox;
    

    
    
    
    //private List<Integer> myRacers;

    /**
     * Constructor to create the tabbed panes for the GUI.
     * @param theRace the race object.
     */
    public TabbedPane(final Race theRace) {
        super();
        myRace = theRace;
        myCheckBoxList = new ArrayList<JCheckBox>();
        myDataOutput = new JTextArea(TEXT_AREA_ROWS, TEXT_AREA_COLUMNS);
        myRaceParticipants = new JPanel();
        configTabbedPane();
 
    }
    
    /** Initial state of the participant checkbox. */
    private void intiParticipantsCheckbox() {
        myRaceParticipants.
        setLayout(new GridLayout(myRace.getRaceInfo().getParticipants() + 1, 1));
        myCheckBoxList.add(new JCheckBox("Select All"));
        for (int i = 0; i < myRace.getRaceInfo().getParticipants(); i++) {
            myCheckBox = new JCheckBox(myRace.getRacersInfo().get(i).getRacerName());
            //myCheckBox.addActionListener(theEvent -> setRacer(myCheckBox, 
            //myRace.getRacersInfo().get(i).getRacerID()));
            myCheckBoxList.add(myCheckBox);
        }
        
        for (int i = 0; i < myCheckBoxList.size(); i++) {
            myRaceParticipants.add(myCheckBoxList.get(i));
        }
    }
    
    /** Method to configurate the tabbed pane.*/
    private void configTabbedPane() {
        final JScrollPane dataOutputScroll = new JScrollPane(myDataOutput);
        dataOutputScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        dataOutputScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);
        myDataOutput.setEditable(false);

        final JScrollPane racersScroll = new JScrollPane(myRaceParticipants);
        racersScroll.setVerticalScrollBarPolicy(JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED);
        racersScroll.setHorizontalScrollBarPolicy(JScrollPane.HORIZONTAL_SCROLLBAR_NEVER);

        addTab("Data Output Stream", dataOutputScroll);
        addTab("Race Participants", racersScroll);
        setEnabledAt(1, false);
    }

    /** Clear the data output tab. */
    public void clearDataOutput() { 
        myDataOutput.setText("");
    }
    
//    private void setRacer(final JCheckBox theCheckBox, final int theID) {
//        myRace.toggleParticipant(theID, theCheckBox.isSelected());
//    }

    @SuppressWarnings("unchecked")
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
//        if (PROPERTY_ID.equals(theEvent.getPropertyName())) {
//            myRacers = (List<Integer>) theEvent.getNewValue();
//        }
        
        if (PROPERTY_CONTROL.equals(theEvent.getPropertyName())) {
            myDataOutput.append("File load: Complete!\n");
            setEnabledAt(1, true);
            intiParticipantsCheckbox();
            myRaceParticipants.add(myCheckBox);
        }
        if (PROPERTY_MESSAGE.equals(theEvent.getPropertyName())) {
            myDataOutput.append(theEvent.getNewValue().toString());
        }
        
        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            final List<Messages> messages = (List<Messages>) theEvent.getNewValue();
            for (Messages message : messages) {
                myDataOutput.append(message.toString() + "\n");
            }
        }

    }

}
