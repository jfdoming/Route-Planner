package jds_wn_dx.routeplanner.view;

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
import gov.nasa.worldwind.view.orbit.OrbitView;
import gov.nasa.worldwind.view.orbit.OrbitViewLimits;
import jds_wn_dx.routeplanner.main.AbsentRequirementExceptionListener;
import jds_wn_dx.routeplanner.main.ApplicationConfig;
import jds_wn_dx.routeplanner.utils.LayerUtils;

import javax.swing.JFrame;
import javax.swing.JSplitPane;
import javax.swing.WindowConstants;
import java.awt.Color;
import java.awt.HeadlessException;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 07/05/2017
 * Description: Represents the window that the application is rendered into. It autonomously configures the application
 * window, but needs to be instantiated and shown via setVisible(true) on the AWT Event Queue.
 * <p>
 * This object is a view object.
 */
public class ApplicationWindow extends JFrame {

    public static final double MIN_ZOOM = 1e5;
    public static final double MAX_ZOOM = 1e8;

    /**
     * Default constructor.
     * Throws HeadlessException if the peripherals necessary to display and
     * interact with a window are not available.
     */
    public ApplicationWindow(ApplicationConfig config) throws HeadlessException {
        initializeWindow(config);
        initializeWidgets();
    }

    /**
     * Configures the application window and prepares it to be displayed.
     */
    private void initializeWindow(ApplicationConfig config) {
        // configurable properties
        setTitle(config.getTitle());
        setSize(config.getWidth(), config.getHeight());
        if (config.isCentered()) {
            setLocationRelativeTo(null);
        }
        if (config.isUsingActiveRendering()) {
            setIgnoreRepaint(true);
        }

        // non-configurable setup
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.DO_NOTHING_ON_CLOSE);
    }

    /**
     * Configures the contents of the application window and prepares it to be displayed.
     */
    private void initializeWidgets() {
        WorldWindowGLCanvas wwd = new WorldWindowGLCanvas();
        UIPanel ui = new UIPanel();
        wwd.setModel((Model) WorldWind.createConfigurationComponent(AVKey.MODEL_CLASS_NAME));

        // If an exception occurs, we want to know about it.
        wwd.addRenderingExceptionListener(new AbsentRequirementExceptionListener());

        // Limit the zoom so the user doesn't break the API.
        OrbitViewLimits limits = ((OrbitView) wwd.getView()).getOrbitViewLimits();
        limits.setZoomLimits(MIN_ZOOM, MAX_ZOOM);

        // Specify the colour, shape etc. of lines to be drawn.
        ShapeAttributes attrs = new BasicShapeAttributes();
        attrs.setOutlineMaterial(new Material(Color.YELLOW));
        attrs.setOutlineWidth(2);

        // Instantiate the layer that holds all paths being drawn.
        PathLayer pathLayer = new PathLayer(attrs);

        Path path = new Path();
        pathLayer.addPath(path);

        wwd.getInputHandler().addMouseMotionListener(new PositionListener(wwd, path));

        // Create a path, set some of its properties and set its attributes.
//        ArrayList<Position> pathPositions = new ArrayList<>();
//        pathPositions.add(Position.fromDegrees(28, -102, 1e5));
//        pathPositions.add(Position.fromDegrees(100, -100, 1e5));
//        Path path = new Path(pathPositions);
//        pathLayer.addPath(path);

        pathLayer.addTo(wwd);

        BasicMarkerAttributes mAttrs = new BasicMarkerAttributes();
        mAttrs.setMaterial(new Material(Color.WHITE));

        List<Marker> markers = new ArrayList<>(1);
        markers.add(new BasicMarker(Position.fromDegrees(90, 0), mAttrs));
//        currentPoint = markers.get(0);
        MarkerLayer markerLayer = new MarkerLayer();
        markerLayer.setMarkers(markers);
        LayerUtils.insertBeforeCompass(wwd, markerLayer);

//        getContentPane().add(wwd, BorderLayout.CENTER);
        JSplitPane contents = new JSplitPane();
        contents.setLeftComponent(ui);
        contents.setRightComponent(wwd);
        getContentPane().add(contents);
    }
}
