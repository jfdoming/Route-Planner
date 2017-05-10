package com.idkwhattoputhere.routeplanner.view;

import com.idkwhattoputhere.routeplanner.utils.LayerUtils;
import gov.nasa.worldwind.WorldWind;
import gov.nasa.worldwind.avlist.AVKey;
import gov.nasa.worldwind.awt.WorldWindowGLCanvas;
import gov.nasa.worldwind.layers.RenderableLayer;
import gov.nasa.worldwind.render.Path;
import gov.nasa.worldwind.render.ShapeAttributes;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 09/05/2017
 * Description: Represents a layer that contains all paths in our application.
 */
public class PathLayer {

    // the layer to draw paths onto
    private RenderableLayer layer;

    // attributes to be applied to all child paths
    private ShapeAttributes attributes;

    public PathLayer(ShapeAttributes attributes) {
        this.attributes = attributes;

        layer = new RenderableLayer();
    }

    public void addPath(Path path) {
        path.setAttributes(attributes);
        path.setAltitudeMode(WorldWind.RELATIVE_TO_GROUND);
        path.setPathType(AVKey.GREAT_CIRCLE);

        layer.addRenderable(path);
    }

    public void addTo(WorldWindowGLCanvas wwd) {
        LayerUtils.insertBeforeCompass(wwd, layer);
    }
}
