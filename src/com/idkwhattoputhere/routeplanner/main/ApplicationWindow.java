package com.idkwhattoputhere.routeplanner.main;

import javax.swing.JFrame;
import javax.swing.WindowConstants;
import java.awt.HeadlessException;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents the window that the application is rendered into. It autonomously configures the application
 * window, but needs to be instantiated and shown via setVisible(true) on the AWT Event Queue.
 * <p>
 * This object is a view object.
 */
public class ApplicationWindow extends JFrame {

    /**
     * Default constructor
     * Throws HeadlessException if the peripherals necessary to display and
     * interact with a window are not available.
     */
    public ApplicationWindow(ApplicationConfig config) throws HeadlessException {
        initializeWindow(config);
    }

    /**
     * Configures the application window and prepares it to be displayed.
     */
    private void initializeWindow(ApplicationConfig config) {
        // configurable properties
        setTitle(config.getTitle());
        setSize(config.getWidth(), config.getHeight());
        if (config.isCentered()) {
            setLocationRelativeTo(null);
        }
        if (config.isUsingActiveRendering()) {
            setIgnoreRepaint(true);
        }

        // non-configurable setup
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }
}
