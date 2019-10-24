
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_CONTROL;


import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import model.Race;

/**
 * Class to create the menu bar for the GUI.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 */
public class MenuBar extends JMenuBar implements ActionListener, PropertyChangeListener {

    /** Universal version identifier for a Serializable class. */
    private static final long serialVersionUID = 1L;

    /** String constant for making new lines. */
    private static final String NEW_LINE = "\n";

    /** List to hold JMenuItems. */
    private final ArrayList<JMenuItem> myMenuItems;

    /** JMenuItem for information about the race. */
    private JMenuItem myAboutRace;

    /** JMenuItem for information about the user. */
    private JMenuItem myAboutUser;

    /** RaceInfo object that holds information about the race after parsing. */
    private final Race myRace;

    /**
     * Constructor to create the menu bar for the GUI.
     * 
     * @param theFile thefile. 
     * @param theControl theControl.
     * @param theRace theRace.
     */
    public MenuBar(final ArrayList<Action> theFile, final ArrayList<Action> theControl,
                   final Race theRace) {
        super();
        myMenuItems = new ArrayList<JMenuItem>();
        initMenuItems(theFile, theControl);
        myRace = theRace;
    }

    /**
     * Helper method to create the JMenuItems.
     * 
     * @param theFile the file.
     * @param theControl he controls.
     */
    private void initMenuItems(final ArrayList<Action> theFile,
                               final ArrayList<Action> theControl) {
        
        
        final JMenu fileTab = new JMenu("File");
        final JMenu controlsTab = new JMenu("Controls");
        final JMenu helpTab = new JMenu("Help");

        fileTab.addSeparator();

        for (final Action action : theFile) {
            final JMenuItem fileItem = new JMenuItem(action);
            fileTab.add(fileItem);
        }

        for (final Action action : theControl) {
            final JMenuItem controlItem = new JMenuItem(action);
            controlItem.setEnabled(false);
            final char mnemonic = controlItem.getText().toUpperCase().charAt(0);
            controlItem.setMnemonic((int) mnemonic);
            controlsTab.add(controlItem);
            myMenuItems.add(controlItem);
        }

        myAboutRace = new JMenuItem("Race Info...");
        myAboutRace.setEnabled(false);
        myAboutRace.addActionListener(this);
        myMenuItems.add(myAboutRace);

        myAboutUser = new JMenuItem("About...");
        myAboutUser.addActionListener(this);

        helpTab.add(myAboutRace);
        helpTab.add(myAboutUser);

        add(fileTab);
        add(controlsTab);
        add(helpTab);
    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        if (theEvent.getSource().equals(myAboutRace)) {
            JOptionPane.showMessageDialog(this,
                                          myRace.getRaceInfo().getRaceName() + NEW_LINE
                                                + "Track type: "
                                                + myRace.getRaceInfo().getTrack() + NEW_LINE
                                                + "Total time: "
                                                + myRace.getRaceInfo().getTime() + NEW_LINE
                                                + "Lap distance: "
                                                + myRace.getRaceInfo().getDistance());
        }

        if (theEvent.getSource().equals(myAboutUser)) {
            JOptionPane.showMessageDialog(this, "The Race Day!\n" + "Author: Tammy Vo\n"
                                                + "TCSS 305 C\n" + "Fall 2018\n");
        }
    }

    
    
    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_CONTROL.equals(theEvent.getPropertyName())) {
            for (int i = 0; i < myMenuItems.size(); i++) {
                myMenuItems.get(i).setEnabled(true);
            }
        }
    }
}
