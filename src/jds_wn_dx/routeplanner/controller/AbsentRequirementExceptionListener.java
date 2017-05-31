package jds_wn_dx.routeplanner.controller;

import gov.nasa.worldwind.event.RenderingExceptionListener;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;

import javax.swing.JOptionPane;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 09/05/2017
 * Description: Provides user feedback if a system does not support the World Wind API.
 *
 * This object is a controller object.
 */
public class AbsentRequirementExceptionListener implements RenderingExceptionListener {

    /**
     * Runs whenever a rendering exception occurs in the World Wind API.
     *
     * @param t the throwable that was thrown
     */
    public void exceptionThrown(Throwable t) {
        if (t instanceof WWAbsentRequirementException) {
            String message = "Computer does not meet minimum graphics requirements.\n";
            message += "Please install an up-to-date graphics driver and try again.\n";
            message += "Reason: " + t.getMessage() + "\n";
            message += "This program will end when you press OK.";

            // tell the user our message
            JOptionPane.showMessageDialog(null, message, "Unable to Start Program",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
