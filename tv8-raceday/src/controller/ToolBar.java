
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_CONTROL;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JToolBar;

/**
 * Class to create the tool bar for the GUI.
 *
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class ToolBar extends JToolBar implements PropertyChangeListener {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    
    /** Store the JButtons to add to toolbar. */
    private final ArrayList<JButton> myToolBarButtons;

    /**
     * Constructor to create the toolbar for the GUI.
     * 
     * @param theAction for the buttons.
     */
    public ToolBar(final ArrayList<Action> theAction) {
        super();
        myToolBarButtons = new ArrayList<JButton>();
        for (final Action action : theAction) {
            final JButton button = new JButton(action);
            button.setEnabled(false);
            myToolBarButtons.add(button);
            button.setHideActionText(true);
            add(button);
        }
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_CONTROL.equals(theEvent.getPropertyName())) {
            for (int i = 0; i < myToolBarButtons.size(); i++) {
                myToolBarButtons.get(i).setEnabled(true);
            }
        }
    }
}
