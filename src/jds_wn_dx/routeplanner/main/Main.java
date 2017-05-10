package jds_wn_dx.routeplanner.main;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: The entry point to our application.
 */
public class Main {

    // constants related to application initialization
    private static final int APP_WIDTH = 800;
    private static final int APP_HEIGHT = 600;
    private static final String APP_TITLE = "Route Planner";

    // runs on program startup
    public static void main(String[] args) {
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle(APP_TITLE);
        config.setSize(APP_WIDTH, APP_HEIGHT);
        Application app = new Application(config);
        app.start();
    }

}
