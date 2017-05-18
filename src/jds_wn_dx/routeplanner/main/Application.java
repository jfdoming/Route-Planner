package jds_wn_dx.routeplanner.main;

import gov.nasa.worldwind.Model;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.layers.MarkerLayer;
import gov.nasa.worldwind.render.BasicShapeAttributes;
import gov.nasa.worldwind.render.Material;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;
import gov.nasa.worldwind.render.markers.BasicMarker;
import gov.nasa.worldwind.render.markers.BasicMarkerAttributes;
import gov.nasa.worldwind.render.markers.Marker;
import gov.nasa.worldwind.util.BasicDragger;
import gov.nasa.worldwind.view.orbit.OrbitView;
import gov.nasa.worldwind.view.orbit.OrbitViewLimits;
import jds_wn_dx.routeplanner.utils.LayerUtils;
import jds_wn_dx.routeplanner.view.PathLayer;

import javax.swing.JFrame;
import java.awt.Color;
import java.awt.EventQueue;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents the entire application. This class provides methods for controlling the
 * application lifecycle.
 */
public class Application {

    private ApplicationConfig config;

    public Application(ApplicationConfig config) {
        this.config = config;
    }

    /**
     * Starts this application. This opens the window and starts the background threads.
     */
    public void start() {
        // declare the background threads to be started as soon as the window opens
        final LoopingThread updatingLoop = new LoopingThread(new UpdatingLoop(), "updating-loop");

        // open our window on the event dispatch thread
        EventQueue.invokeLater(new Runnable() {
            @Override
            public void run() {
                // create and initialize the application window
                final JFrame applicationWindow = new ApplicationWindow(config);

                initializeWidgets(applicationWindow);

                // listen for window events (opening, closing, etc.)
                applicationWindow.addWindowListener(new WindowAdapter() {
                    @Override
                    public void windowOpened(WindowEvent e) {
                        // attempt to capture focus
                        applicationWindow.requestFocus();

                        // start the application threads
                        updatingLoop.start();
                    }

                    @Override
                    public void windowClosing(WindowEvent e) {
                        // tell the application threads to stop (blocking)
                        updatingLoop.stop();

                        // close the window
                        applicationWindow.dispose();
                    }
                });

                // open the application window
                applicationWindow.setVisible(true);
            }
        });
    }

    private void initializeWidgets(final JFrame applicationWindow) {
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();

        wwd.addSelectListener(new BasicDragger(wwd));
        wwd.setModel((Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME));
        wwd.addRenderingExceptionListener(new AbsentRequirementExceptionListener());
        OrbitViewLimits limits = ((OrbitView) wwd.getView()).getOrbitViewLimits();
        limits.setZoomLimits(1e5, 1e8);

        // Create and set an attribute bundle.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(Color.YELLOW));
        attrs.setOutlineWidth(2);

        PathLayer pathLayer = new PathLayer(attrs);

        // Create a path, set some of its properties and set its attributes.
        ArrayList<Position> pathPositions = new ArrayList<>();
        pathPositions.add(Position.fromDegrees(28, -102, 1e5));
        pathPositions.add(Position.fromDegrees(100, -100, 1e5));
        Path path = new Path(pathPositions);
        pathLayer.addPath(path);

        pathLayer.addTo(wwd);

        BasicMarkerAttributes mAttrs = new BasicMarkerAttributes();
        mAttrs.setMaterial(new Material(Color.YELLOW));

        List<Marker> markers = new ArrayList<>(1);
        markers.add(new BasicMarker(Position.fromDegrees(90, 0), mAttrs));
        MarkerLayer markerLayer = new MarkerLayer();
        markerLayer.setMarkers(markers);
        LayerUtils.insertBeforeCompass(wwd, markerLayer);

        applicationWindow.getContentPane().add(wwd, java.awt.BorderLayout.CENTER);
    }
}
