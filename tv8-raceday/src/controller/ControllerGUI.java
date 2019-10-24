
package controller;


import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Toolkit;
import javax.swing.BorderFactory;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.border.TitledBorder;
import model.Race;
import view.LeaderPanel;
import view.StatusPanel;
import view.TrackPanel;

/**
 * This class creates the controller GUI.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public class ControllerGUI extends JFrame {

    /** Universal version identifier for a Serializable class. */
    private static final long serialVersionUID = 1L;

    /** The amount of padding to add all around the GUI. */
    private static final int PADDING_AMOUNT = 10;
    
    /** The race track text. */
    private static final String RACE_TRACK = "Race Track";

    /** Race object in the model package. */
    private static final Race RACE = new Race();

    /** The menu bar for the GUI. */
    private final MenuBar myMenuBar;

    /** The slider for the GUI. */
    private final Slider mySlider;

    /** The time panel for the timer. */
    private final TimerPanel myTimePanel;

    /** The tabbed pane for data output stream and race participants. */
    private final TabbedPane myTabbedPane;

    /** The tool bar for the GUI. */
    private final ToolBar myToolBar;

    /** The JPanel to be in the center of the GUI. */
    private final JPanel myCenterPanel = new JPanel();

    /** The JPanel to be in the north portion of myCenterPanel, the slider and time label. */
    private final JPanel myNorthPanel = new JPanel();

    /** The Track Panel. */
    private TrackPanel myTrackPanel;
    
    /** The Leader Panel. */
    private LeaderPanel myLeaderPanel;
    
    /** The Status Panel. */
    private StatusPanel myStatusPanel;
    
    /** The Track frame. */
    private JPanel myTrackFrame;
    
    /** The title border for the Track frame. */ 
    private TitledBorder myTitle;

    /**
     * Constructor for creating the GUI.
     * 
     * @throws IOException
     */
    public ControllerGUI() {
        super("Race Day!");
        mySlider = new Slider(RACE);
        myTabbedPane = new TabbedPane(RACE);
        final ButtonControl buttonControl = new ButtonControl(RACE, myTabbedPane);
        myMenuBar = new MenuBar(buttonControl.getFileTab(), buttonControl.getControlsTab(),
                                RACE);
        myTimePanel = new TimerPanel(); 
        myToolBar = new ToolBar(buttonControl.getControlsTab());  
       
        initControllerWindow();
        addPCL();

        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         buttonControl);
    }

    /**
     * Add observers to the GUI components.
     */
    private void addPCL() {

        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         mySlider);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         myTrackPanel);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         myTimePanel);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         myTabbedPane);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         myStatusPanel);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_TIME,
                                         myLeaderPanel);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         myLeaderPanel);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         mySlider);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         myTabbedPane);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         myToolBar);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         myMenuBar);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_CONTROL,
                                         myTrackPanel);
        RACE.addPropertyChangeListener(model.
                                         PropertyChangeEnabledRaceControls.PROPERTY_MESSAGE,
                                         myTabbedPane);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD,
                                         myTabbedPane);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD,
                                         myLeaderPanel);
        RACE.addPropertyChangeListener(model.PropertyChangeEnabledRaceControls.PROPERTY_LOAD,
                                         myTrackPanel);

    }

    /**
     * Initializes the JFrame and the components of the GUI.
     */
    private void initControllerWindow() {
        setLayout(new BorderLayout());

        myNorthPanel.setLayout(new BorderLayout());
        myNorthPanel.add(myTimePanel, BorderLayout.EAST);
        myNorthPanel.add(mySlider, BorderLayout.CENTER);
        myNorthPanel.setBorder(new EmptyBorder(0, 0, PADDING_AMOUNT, 0));

        myCenterPanel.setLayout(new BorderLayout());
        myCenterPanel.add(myNorthPanel, BorderLayout.NORTH);
        myCenterPanel.add(myTabbedPane, BorderLayout.CENTER);
        myCenterPanel.setBorder(new EmptyBorder(PADDING_AMOUNT, PADDING_AMOUNT, PADDING_AMOUNT,
                                                PADDING_AMOUNT));

        setJMenuBar(myMenuBar);
        add(myToolBar, BorderLayout.SOUTH);
        add(myCenterPanel, BorderLayout.CENTER);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setIconImage(Toolkit.getDefaultToolkit().getImage("./images/ic_race_app.png"));
        pack();
        setLocationRelativeTo(null);
        setResizable(false);
        setVisible(true);
        
        seperateWindow();
    }

    /** Creates the separate window for the track, leader board, and status panel. */
    private void seperateWindow() {
        myTrackFrame = new JPanel(new BorderLayout());
        myTrackPanel = new TrackPanel(RACE);
        myStatusPanel = new StatusPanel();
        myLeaderPanel = new LeaderPanel(RACE);
        
        final JPanel emptyWindow = new JPanel();
        emptyWindow.setBackground(Color.WHITE);
        myTitle = BorderFactory.createTitledBorder(RACE_TRACK);
        emptyWindow.setBorder(myTitle);
        myTrackFrame.add(emptyWindow, BorderLayout.WEST);
        emptyWindow.add(myTrackPanel);
        myTrackFrame.add(myLeaderPanel, BorderLayout.EAST);
        myTrackFrame.add(myStatusPanel, BorderLayout.SOUTH);
        
        
        
        javax.swing.SwingUtilities.invokeLater(new Runnable() {

            public void run() {
                final JFrame timeViewFrame = new JFrame(RACE_TRACK);
                timeViewFrame.setContentPane(myTrackFrame);
                timeViewFrame.setResizable(false);
                timeViewFrame.pack();
                timeViewFrame.setVisible(true);
            }
        });

    }
}
