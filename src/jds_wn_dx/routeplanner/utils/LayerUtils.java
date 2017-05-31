package jds_wn_dx.routeplanner.utils;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.layers.CompassLayer;
import gov.nasa.worldwind.layers.Layer;
import gov.nasa.worldwind.layers.LayerList;
import gov.nasa.worldwind.layers.placename.PlaceNameLayer;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 09/05/2017
 * Description: Provides utilities for adding layers to a canvas.
 */
public class LayerUtils {

    /**
     * Inserts a layer into the window before the compass layer.
     *
     * @param wwd the window
     * @param layer the layer to insert
     */
    public static void insertBeforeCompass(WorldWindow wwd, Layer layer)
    {
        // insert the layer into the layer list just before the compass
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof CompassLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition, layer);
    }

    /**
     * Inserts a layer into the window before the placename layer.
     *
     * @param wwd the window
     * @param layer the layer to insert
     */
    public static void insertBeforePlacenames(WorldWindow wwd, Layer layer)
    {
        // insert the layer into the layer list just before the placenames
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof PlaceNameLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition, layer);
    }

    /**
     * Inserts a layer into the window after the placename layer.
     *
     * @param wwd the window
     * @param layer the layer to insert
     */
    public static void insertAfterPlacenames(WorldWindow wwd, Layer layer)
    {
        // insert the layer into the layer list just after the placenames
        int compassPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l instanceof PlaceNameLayer)
                compassPosition = layers.indexOf(l);
        }
        layers.add(compassPosition + 1, layer);
    }

    /**
     * Inserts a layer into the window before the named layer.
     *
     * @param wwd the window
     * @param layer the layer to insert
     * @param targetName the name of the layer to insert before
     */
    public static void insertBeforeLayerName(WorldWindow wwd, Layer layer, String targetName)
    {
        // insert the layer into the layer list just before the target layer
        int targetPosition = 0;
        LayerList layers = wwd.getModel().getLayers();
        for (Layer l : layers)
        {
            if (l.getName().contains(targetName))
            {
                targetPosition = layers.indexOf(l);
                break;
            }
        }
        layers.add(targetPosition, layer);
    }

}
