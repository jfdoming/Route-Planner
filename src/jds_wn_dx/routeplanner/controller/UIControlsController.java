package jds_wn_dx.routeplanner.controller;

import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.markers.BasicMarker;
import jds_wn_dx.routeplanner.model.DescentRouteSegment;
import jds_wn_dx.routeplanner.model.Route;
import jds_wn_dx.routeplanner.model.XML_IO;
import jds_wn_dx.routeplanner.view.ApplicationWindow;
import jds_wn_dx.routeplanner.controller.LoadListener;
import jds_wn_dx.routeplanner.controller.SaveListener;
import jds_wn_dx.routeplanner.view.UIPanel;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Controls our application.
 */
public class UIControlsController extends MouseAdapter implements SaveListener, LoadListener, ActionListener {

    private final ApplicationWindow window;
    private final UIPanel panel;
    private final WorldWindowGLCanvas wwd;

    private Route currentRoute;

    private boolean started;

    public UIControlsController(ApplicationWindow window) {
        this.window = window;
        this.panel = window.getUIPanel();
        this.wwd = window.getWorldWindowGLCanvas();

        panel.addSaveListener(this);
        panel.addLoadListener(this);
        panel.addStartStopListener(this);
        wwd.addMouseListener(this);
        wwd.addMouseMotionListener(this);
    }

    @Override
    public void onSave(File out) {
        String title = panel.getTitleValue();
    }

    @Override
    public void onLoad(File out) {
        XML_IO io = new XML_IO();
        io.inputFile(out);
        //io.getData();
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        event.consume();

        if (started) {
            Position mousePosition = wwd.getCurrentPosition();
            Path modify = window.getDisplayPath();

            if (currentRoute == null) {
                Position position = new Position(mousePosition, panel.getAltitudeSpinnerValue());
                currentRoute = new Route(position);
                return;
            }

            if (mousePosition != null && event.getButton() == MouseEvent.BUTTON1) {
                mousePosition = new Position(mousePosition, panel.getAltitudeSpinnerValue());
                modify.setPositions(currentRoute.extend(mousePosition, DescentRouteSegment::new));
                window.addMarker(new BasicMarker(mousePosition, window.getMarkerAttributes()));
            }
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!started) {
            return;
        }

        if (currentRoute == null) {
            return;
        }

        Position mousePosition = wwd.getCurrentPosition();
        Path modify = window.getDisplayPath();

        if (mousePosition != null) {
            mousePosition = new Position(mousePosition, panel.getAltitudeSpinnerValue());
            modify.setPositions(currentRoute.predict(mousePosition, DescentRouteSegment::new));
        }
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        // we can be sure the only button here is the start/stop button

        if (!started) {
            started = true;
            panel.setStartStopText("Stop");
        } else {
            started = false;
            panel.setStartStopText("Start");
        }
    }
}
