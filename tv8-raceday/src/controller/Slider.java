
package controller;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;


import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import javax.swing.JSlider;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import model.Race;

/**
 * Class to create the slider.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class Slider extends JSlider implements ChangeListener, PropertyChangeListener {

    /** Universal version identifier for a Serializable class. */
    private static final long serialVersionUID = 1L;
    /** Major tick spacing on the slider. */
    private static final int MAJOR_TICK = 60000;
    /** Minor tick spacing on the slider. */
    private static final int MINOR_TICK = 10000;
    /** Race object in the model package. */
    private final Race myRace;

    /**
     * Constructor to create the slider for the GUI.
     *  
     * @param theRace 
     */
    public Slider(final Race theRace) {
        super();
        myRace = theRace;
        initialSlider();
        addChangeListener(this);
    }

    /** Method that initializes the Slider. */
    private void initialSlider() {
        setEnabled(false);
        setPaintTicks(false);
        setMajorTickSpacing(0);
        setValue(0);
    }

    @Override
    public void stateChanged(final ChangeEvent theEvent) {
        final JSlider source = (JSlider) theEvent.getSource();
        if (source.getValueIsAdjusting()) {
            myRace.moveTo(source.getValue());
        }
 
    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            setEnabled(true);
            setMaximum(myRace.getRaceInfo().getTime());
            setMajorTickSpacing(MAJOR_TICK);
            setMinorTickSpacing(MINOR_TICK);
            setPaintTicks(true);
            setValue((Integer) theEvent.getNewValue());
        }
       
    }

}
