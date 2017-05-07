package com.idkwhattoputhere.routeplanner.main;

import java.awt.EventQueue;

/**
 * Assignment:
 * Author: Julian Dominguez-Schatz
 * Date : 09/01/2017
 * Description:
 */
public class Main {

    // runs on program startup
    public static void main(String[] args) {
        // open our window on the event dispatch thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create the frame and make it visible
                ProjectFrame projectFrame = new ProjectFrame();
                projectFrame.setVisible(true);
            }
        });
    }

}
