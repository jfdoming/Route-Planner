package jds_wn_dx.routeplanner.controller;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Represents an object which is notified when the user attempts to clear the route.
 *
 * This object is a controller object.
 */
public interface ClearListener {

    /**
     * Clears the route.
     */
    void onClear();

}
