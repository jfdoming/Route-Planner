package jds_wn_dx.routeplanner.controller;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;
import jds_wn_dx.routeplanner.model.Route;
import jds_wn_dx.routeplanner.model.RouteIO;
import jds_wn_dx.routeplanner.view.ApplicationWindow;
import jds_wn_dx.routeplanner.view.UIPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Danny Xu
 * Date: 2017-05-29
 * Description: Controls our application. It adds its own listeners so it can respond to
 * various events and user actions.
 *
 * This object is a controller object.
 */
public class UIControlsController extends MouseAdapter implements SaveListener, LoadListener, ActionListener, ClearListener {

    // view-related fields
    private final ApplicationWindow window;
    private final UIPanel panel;
    private final WorldWindowGLCanvas wwd;

    // route-related fields
    private Route currentRoute;
    private RouteIO routeIOWrapper;
    private boolean constructingRoute;

    /**
     * Constructor.
     *
     * @param window the window to control
     */
    public UIControlsController(ApplicationWindow window) {
        this.window = window;
        this.panel = window.getUiPanel();
        this.wwd = window.getWorldWindowGLCanvas();

        this.currentRoute = new Route();
        this.routeIOWrapper = new RouteIO();
        this.constructingRoute = false;

        // register our controller as listeners
        panel.addSaveListener(this);
        panel.addLoadListener(this);
        panel.addStartStopListener(this);
        panel.addClearListener(this);
        wwd.addMouseListener(this);
        wwd.addMouseMotionListener(this);
    }

    @Override
    public void onClear() {
        this.currentRoute = new Route();
        this.constructingRoute = false;

        // update the view
        Path modify = window.getDisplayPath();
        modify.setPositions(currentRoute.getResult());
        panel.setStartStopText(UIPanel.START_TEXT);

        // make sure the world window is updated
        wwd.redraw();
    }

    @Override
    public void onSave(File out) {
        // writes the data to XML
        String name = panel.getNameValue();
        RouteIO.Data data = new RouteIO.Data(currentRoute, name);
        routeIOWrapper.writeToXML(data, out);
    }

    @Override
    public void onLoad(File in) {
        // obtain the data from XML
        RouteIO.Data data = routeIOWrapper.readFromXML(in);

        this.currentRoute = data.getRoute();
        panel.setNameValue(data.getName());

        // update the view
        panel.setStartStopText(UIPanel.START_TEXT);
        Path modify = window.getDisplayPath();
        modify.setPositions(currentRoute.getResult());

        // make sure the world window is updated
        wwd.redraw();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        // make sure we are currently building the route
        if (!constructingRoute) {
            return;
        }

        Position mousePosition = wwd.getCurrentPosition();
        Path modify = window.getDisplayPath();

        // make sure the user clicked on the globe
        if (mousePosition != null) {
            // adjust the position the user chose based on the altitude they chose
            mousePosition = new Position(mousePosition, panel.getAltitudeSpinnerValue());

            // try to prevent the globe from turning when the user clicks to place a point
            event.consume();

            if (currentRoute.isStarted()) {
                // extend the route based on the user-defined point
                modify.setPositions(currentRoute.extend(mousePosition, panel.getSegmentType()));
            } else {
                // start the route at the user-defined point
                currentRoute.start(mousePosition);
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        // make sure we are currently building the route
        if (!constructingRoute) {
            return;
        }

        Position mousePosition = wwd.getCurrentPosition();
        Path modify = window.getDisplayPath();

        if (mousePosition != null) {
            if (currentRoute.isStarted()) {
                // predict the extension of the route based on the user-defined point
                mousePosition = new Position(mousePosition, panel.getAltitudeSpinnerValue());
                modify.setPositions(currentRoute.predict(mousePosition, panel.getSegmentType()));
            }
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // We can be sure the only button here is the start/stop button

        // determine whether to start or stop constructing the route
        if (!constructingRoute) {
            constructingRoute = true;

            panel.setStartStopText(UIPanel.STOP_TEXT);
        } else {
            constructingRoute = false;

            Path modify = window.getDisplayPath();
            modify.setPositions(currentRoute.getResult());

            panel.setStartStopText(UIPanel.START_TEXT);
        }

        // make sure the world window is updated
        wwd.redraw();
    }
}
