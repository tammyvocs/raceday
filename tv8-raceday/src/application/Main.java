
package application;

import controller.ControllerGUI;
import java.awt.EventQueue;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;

/**
 * Main class to run GUI.
 * 
 * @author Tammy Vo
 * @version 02 December 2018
 *
 */
public final class Main {
    
    /** Private constructor. */
    private Main() { }
    
    /**
     * The main method, invokes the Race GUI. Command line arguments are ignored.
     * 
     * @param theArgs Command line arguments.
     */
    public static void main(final String[] theArgs) {

        try {
            UIManager.setLookAndFeel("javax.swing.plaf.metal.MetalLookAndFeel");
            UIManager.put("swing.boldMetal", false);
        } catch (final ClassNotFoundException | InstantiationException | IllegalAccessException
                        | UnsupportedLookAndFeelException ex) {
            throw new RuntimeException("Test Failed. MetalLookAndFeel not set " + "for frame");
        }

        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                new ControllerGUI();
            }
        });
    }
}
