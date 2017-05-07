package com.idkwhattoputhere.routeplanner.main;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-03-15
 * Description: Represents the entire application. This class provides methods for controlling the
 * application lifecycle.
 */
public class Application {

    private ApplicationConfig config;

    public Application(ApplicationConfig config) {
        this.config = config;
    }

    /**
     * Starts this application. This opens the window and starts the background threads.
     */
    public void start() {
        // declare the background threads to be started as soon as the window opens
        final LoopingThread renderingLoop = new RenderingLoop();
        final LoopingThread updatingLoop = new UpdatingLoop();

        // open our window on the event dispatch thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create and initialize the application window
                final JFrame applicationWindow = new ApplicationWindow(config);

                initializeWidgets(applicationWindow);

                // listen for window events (opening, closing, etc.)
                applicationWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        // attempt to capture focus
                        applicationWindow.requestFocus();

                        // start the application threads
                        updatingLoop.start();
                        renderingLoop.start();
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        // tell the application threads to stop (blocking)
                        updatingLoop.stop();
                        renderingLoop.stop();

                        // close the window
                        applicationWindow.dispose();
                    }
                });

                // open the application window
                applicationWindow.setVisible(true);
            }
        });
    }

    private void initializeWidgets(JFrame applicationWindow) {
        // TODO add widget setup code here
    }
}
