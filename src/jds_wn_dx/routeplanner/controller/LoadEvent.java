package jds_wn_dx.routeplanner.controller;

import java.awt.AWTEvent;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 29/05/2017
 * Description: Represents an event sent when the user presses load.
 */
public class LoadEvent extends AWTEvent {

    public LoadEvent(Object source, int id) {
        super(source, id);
    }
}
