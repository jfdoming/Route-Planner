package com.idkwhattoputhere.routeplanner.main;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 09/05/2017
 * Description: Represents a loop that runs until told to stop.
 */
public interface Loop {

    // runs before the loop starts
    void init();

    // runs after the loop ends
    void dispose();

    // runs for the loop
    void update();
}
