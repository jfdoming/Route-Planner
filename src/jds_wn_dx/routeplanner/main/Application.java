package jds_wn_dx.routeplanner.main;

import jds_wn_dx.routeplanner.view.ApplicationWindow;

import javax.swing.JFrame;
import javax.swing.UIManager;
import javax.swing.UnsupportedLookAndFeelException;
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
        // open our window on the event dispatch thread
        EventQueue.invokeLater(() -> {
            if (config.isUsingSystemUI()) {
                enableSystemUI();
            }

            // create and initialize the application window
            final JFrame applicationWindow = new ApplicationWindow(config);

            // listen for window events (opening, closing, etc.)
            applicationWindow.addWindowListener(new WindowAdapter() {
                @Override
                public void windowOpened(WindowEvent e) {
                    // attempt to capture focus
                    applicationWindow.requestFocus();
                }

                @Override
                public void windowClosing(WindowEvent e) {
                    // close the window
                    applicationWindow.dispose();
                }
            });

            // open the application window
            applicationWindow.setVisible(true);
        });
    }

    private void enableSystemUI() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (ClassNotFoundException e) {
            System.err.println("Failed to locate system UI class!");
        } catch (InstantiationException e) {
            System.err.println("Failed to instantiate system UI class!");
        } catch (IllegalAccessException e) {
            System.err.println("Failed to access system UI class!");
        } catch (UnsupportedLookAndFeelException e) {
            System.err.println("System UI class invalid!");
        }
    }
}
