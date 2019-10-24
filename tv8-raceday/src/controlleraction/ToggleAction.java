
package controlleraction;

import java.awt.Image;
import java.awt.event.ActionEvent;
import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.ImageIcon;

/**
 * This class is for the toggle action.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class ToggleAction extends AbstractAction {

    /** A generated serial version UID for object Serialization.*/
    private static final long serialVersionUID = 3772900050945544969L;

    /** Determines the state of the "toggle". */
    protected boolean myFlag;

    /** Stores the text of this Action in the original state. */
    private final String myFirstText;

    /** Stores the icon of this Action in the original state. */
    private final String mySecondText;

    /** Stores the text of this Action in the toggled state. */
    private final String myFirstIcon;

    /** Stores the icon of this Action in the toggled state. */
    private final String mySecondIcon;
    
    /** The behavior to run when the toggle is true. */
    private final Runnable myFirstAction;
    
    /** The behavior to run when the toggle is false. */
    private final Runnable mySecondAction;
    
    /** The boolean for the toggle buttons. */
    private final boolean myBool;
    /**
     * Creates a ToggleAction.
     * 
     * @param theFirstText the text of this Action in the original state
     * @param theSecondText the icon of this Action in the original state
     * @param theFirstIcon the text of this Action in the toggled state
     * @param theSecondIcon the icon of this Action in the toggled state
     * @param theFirstAction the first action the toggled state
     * @param theSecondAction the second action the toggled state
     * @param theBool the boolean of the action is in the toggled state.
     */
    public ToggleAction(final String theFirstText, final String theSecondText,
                        final String theFirstIcon, final String theSecondIcon,
                        final Runnable theFirstAction, final Runnable theSecondAction, 
                        final boolean theBool) {
        super(theFirstText);
        myFirstText = theFirstText;
        mySecondText = theSecondText;
        myFirstIcon = theFirstIcon;
        mySecondIcon = theSecondIcon;
        myFirstAction = theFirstAction;
        mySecondAction = theSecondAction;
        myBool = theBool;
        
        setIcon(new ImageIcon(myFirstIcon));
        myFlag = true;

    }

    @Override
    public void actionPerformed(final ActionEvent theEvent) {
        if (myFlag) {
            if (myBool) {
                mySecondAction.run();
            } else {
                myFirstAction.run();
            }
           
            putValue(Action.NAME, mySecondText);
            setIcon(new ImageIcon(mySecondIcon));
        } else {
            if (myBool) {
                myFirstAction.run();
            } else {
                mySecondAction.run();
            }
            putValue(Action.NAME, myFirstText);
            setIcon(new ImageIcon(myFirstIcon));
        }
        myFlag = !myFlag;

    }

    /**
     * Helper to set the Icon to both the Large and Small Icon values.
     * 
     * @param theIcon the icon to set for this Action
     */
    private void setIcon(final ImageIcon theIcon) {
        final ImageIcon icon = (ImageIcon) theIcon;
        final Image largeImage =
                        icon.getImage().getScaledInstance(24, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon largeIcon = new ImageIcon(largeImage);
        putValue(Action.LARGE_ICON_KEY, largeIcon);

        final Image smallImage =
                        icon.getImage().getScaledInstance(12, -1, java.awt.Image.SCALE_SMOOTH);
        final ImageIcon smallIcon = new ImageIcon(smallImage);
        putValue(Action.SMALL_ICON, smallIcon);
    }
    
}



