package jds_wn_dx.routeplanner.controller;

import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Represents an object which is notified when the user attempts to save.
 *
 * This object is a controller object.
 */
public interface SaveListener {

    /**
     * Saves to the given file.
     *
     * @param out the file to save to
     */
    void onSave(File out);

}
