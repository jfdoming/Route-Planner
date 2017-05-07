package com.idkwhattoputhere.routeplanner.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;

/**
 * Assignment:
 * Author: Julian Dominguez-Schatz
 * Date : 09/01/2017
 * Description:
 */
public class ProjectFrame extends JFrame {

    /**
     * Default constructor
     * Throws HeadlessException if the peripherals necessary to display and
     * interact with a window are not available.
     */
    public ProjectFrame() throws HeadlessException {
        initializeUserInterface();
    }

    /**
     * Configures the application window and prepares it to be displayed.
     */
    private void initializeUserInterface() {
        setTitle("Project Frame");
        setSize(800, 600);
        setLocationRelativeTo(null); // position in the center of the screen
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        // TODO add widget setup code below
    }
}
