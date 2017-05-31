package jds_wn_dx.routeplanner.controller;

import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Represents an object which is notified when the user attempts to load.
 *
 * This object is a controller object.
 */
public interface LoadListener {

    /**
     * Loads from the given file.
     *
     * @param in the file to read from
     */
    void onLoad(File in);

}
