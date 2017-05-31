package jds_wn_dx.routeplanner.view;

import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Represents an object which is notified when the user attempts to load.
 */
public interface LoadListener {

    void onLoad(File in);

}
