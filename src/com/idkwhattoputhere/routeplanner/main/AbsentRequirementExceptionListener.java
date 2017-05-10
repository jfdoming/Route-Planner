package com.idkwhattoputhere.routeplanner.main;

import gov.nasa.worldwind.event.RenderingExceptionListener;
import gov.nasa.worldwind.exception.WWAbsentRequirementException;

import javax.swing.JOptionPane;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 09/05/2017
 * Description: Provides user feedback if a system does not support the World Wind API.
 */
public class AbsentRequirementExceptionListener implements RenderingExceptionListener {

    public void exceptionThrown(Throwable t) {
        if (t instanceof WWAbsentRequirementException) {
            String message = "Computer does not meet minimum graphics requirements.\n";
            message += "Please install an up-to-date graphics driver and try again.\n";
            message += "Reason: " + t.getMessage() + "\n";
            message += "This program will end when you press OK.";

            JOptionPane.showMessageDialog(null, message, "Unable to Start Program",
                    JOptionPane.ERROR_MESSAGE);
        }
    }
}
