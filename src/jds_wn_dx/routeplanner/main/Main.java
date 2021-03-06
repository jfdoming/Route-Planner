package jds_wn_dx.routeplanner.main;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: The entry point to our application.
 */
public class Main {

    // constants related to application initialization
    private static final int APP_WIDTH = 1024;
    private static final int APP_HEIGHT = 768;
    private static final String APP_TITLE = "Route Planner";

    private static final boolean USING_ACTIVE_RENDERING = false;
    private static final boolean USING_SYSTEM_UI = true;

    // runs on program startup
    public static void main(String[] args) {
        // prepare the configuration
        ApplicationConfig config = new ApplicationConfig();
        config.setTitle(APP_TITLE);
        config.setSize(APP_WIDTH, APP_HEIGHT);
        config.setUsingActiveRendering(USING_ACTIVE_RENDERING);
        config.setUsingSystemUI(USING_SYSTEM_UI);

        // start our application
        Application app = new Application(config);
        app.start();
    }
}
