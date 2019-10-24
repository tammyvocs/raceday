
package view;

import static model.PropertyChangeEnabledRaceControls.PROPERTY_TIME;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_CONTROL;
import static model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.RenderingHints;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javax.swing.JPanel;
import model.Messages;
import model.Race;
import model.Telemetry;
import track.VisibleRaceTrack;

/**
 * A class to create the track panel.
 * 
 * @author Tammy Vo
 * @author Charles Bryan
 * @version Autumn 2015
 */
public class TrackPanel extends JPanel implements PropertyChangeListener {

    /**
     * A generated serial version UID for object Serialization.
     * http://docs.oracle.com/javase/7/docs/api/java/io/Serializable.html
     */
    private static final long serialVersionUID = 8385732728740430466L;

    /** The size of the Race Track Panel. */
    private static final Dimension TRACK_SIZE = new Dimension(500, 400);

    /** The x and y location of the Track. */
    private static final int OFF_SET = 40;

    /** The stroke width in pixels. */
    private static final int STROKE_WIDTH = 25;

    /** The size of participants moving around the track. */
    private static final int OVAL_SIZE = 20;
    
    /** The visible track. */
    private VisibleRaceTrack myTrack;

    /** The circle moving around the track. */
    private Map<Integer, Ellipse2D> myCircles;
   
    /** A collection of the messages. */
    private ArrayList<Messages> myMessages;
    
    /** Collection of IDs. */
    private final ArrayList<Integer> myIDs;
    
    /** The race. */
    private Race myRace;


    /** 
     * Construct a Track Panel. 
     * @param theRace the race.
     * */
    public TrackPanel(final Race theRace) {
        super();
        setPreferredSize(TRACK_SIZE);
        setBackground(Color.WHITE);
        myMessages = new ArrayList<Messages>();
        myCircles = new HashMap<Integer, Ellipse2D>();
        myRace = theRace;
        myIDs = new ArrayList<Integer>();
    }

    /** Setup and layout components. */
    private void setupComponents() {

        final int width = (int) TRACK_SIZE.getWidth() - (OFF_SET * 2);
        final int height = ((int) TRACK_SIZE.getWidth() - 2 * OFF_SET) / 5 * 2;
        final int x = OFF_SET;
        final int y = (int) TRACK_SIZE.getHeight() / 2 - height / 2;
        myTrack = new VisibleRaceTrack(x, y, width, height,
                                       myRace.getRaceInfo().getDistance());

        for (int i = 0; i < myRace.getRaceInfo().getParticipants(); i++) {

            final Point2D start = myTrack.getPointAtDistance(myRace.getRacersInfo().get(i).
                                                             getRacerInitialPos());

            final Ellipse2D circle = new Ellipse2D.Double(start.getX() - OVAL_SIZE / 2,

                                                          start.getY() - OVAL_SIZE / 2,
                                                          OVAL_SIZE, OVAL_SIZE);

            myCircles.put(myRace.getRacersInfo().get(i).getRacerID(), circle);

            myIDs.add(myRace.getRacersInfo().get(i).getRacerID());
        }
    }

    /**
     * Paints the VisibleTrack with a single ellipse moving around it.
     * 
     * @param theGraphics The graphics context to use for painting.
     */
    @Override
    public void paintComponent(final Graphics theGraphics) {

        super.paintComponent(theGraphics);
        final Graphics2D g2d = (Graphics2D) theGraphics;

        // for better graphics display
        g2d.setRenderingHint(RenderingHints.KEY_ANTIALIASING,
                             RenderingHints.VALUE_ANTIALIAS_ON);

        if (myTrack != null) {
            g2d.setPaint(Color.BLACK);
            g2d.setStroke(new BasicStroke(STROKE_WIDTH));
            g2d.draw(myTrack);
        }
        
        for (int i = 0; i < myRace.getRacersInfo().size(); i++) {
            g2d.setPaint(myRace.getRacersInfo().get(i).getColor());
            g2d.setStroke(new BasicStroke(1));
            g2d.fill(myCircles.get(myIDs.get(i)));
            
        }

    }

    @Override
    public void propertyChange(final PropertyChangeEvent theEvent) {
        if (PROPERTY_TIME.equals(theEvent.getPropertyName())) {
            for (int i = 0; i < myMessages.size(); i++) {
                if (myMessages.get(i).getClass() == Telemetry.class) {
                    final Telemetry telemetry = (Telemetry) myMessages.get(i);

                    final Point2D current =
                                    myTrack.getPointAtDistance(telemetry.getDistance());
                    myCircles.get(telemetry.getRacerID()).
                        setFrame(current.getX() - OVAL_SIZE / 2,
                                              current.getY() - OVAL_SIZE / 2, OVAL_SIZE,
                                              OVAL_SIZE);

                }

            }
            repaint();
        }

        if (PROPERTY_CONTROL.equals(theEvent.getPropertyName())) {
            setupComponents();
            repaint();
        }

        if (PROPERTY_LOAD.equals(theEvent.getPropertyName())) {
            myMessages = (ArrayList<Messages>) theEvent.getNewValue();

        }

    }
}
