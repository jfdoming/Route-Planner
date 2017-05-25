package jds_wn_dx.routeplanner.main;

import jds_wn_dx.routeplanner.controller.LoopingThread;

import javax.swing.JFrame;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
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
        final LoopingThread updatingLoop = new LoopingThread(new UpdatingLoop(), "updating-loop");

        // open our window on the event dispatch thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create and initialize the application window
                final JFrame applicationWindow = new ApplicationWindow(config);

                // listen for window events (opening, closing, etc.)
                applicationWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        // attempt to capture focus
                        applicationWindow.requestFocus();

                        // start the application threads
                        updatingLoop.start();
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        // tell the application threads to stop (blocking)
                        updatingLoop.stop();

                        // close the window
                        applicationWindow.dispose();
                    }
                });

                // open the application window
                applicationWindow.setVisible(true);
            }
        });
    }
}
