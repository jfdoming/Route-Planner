package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents part of a route the user has chosen in our application.
 */
public class RouteSegment {

    // types of route segments
    public static final int LINEAR = 0;
    public static final int ARC = 1;

    /*
     * Since World Wind doesn't have the ability to draw arcs, we approximate them using lines.
     * This is the number of lines to draw.
     */
    private static final int ARC_RESOLUTION = 50;

    private int type;

    private Position startPoint;
    private Position endPoint;

    private Position.PositionList pathPoints;

    public RouteSegment(int type, Position startPoint, Position endPoint) {
        this.type = type;

        this.startPoint = startPoint;
        this.endPoint = endPoint;
    }

    public Position.PositionList buildPath() {
        // cache the result of the path building
        if (pathPoints == null) {
            List<Position> list;

            switch (type) {
                case LINEAR:
                    list = Collections.unmodifiableList(buildLinearSegment());
                    break;
                case ARC:
                    list = Collections.unmodifiableList(buildArcSegment(ARC_RESOLUTION));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid route type!");
            }

            this.pathPoints = new Position.PositionList(list);
        }

        return pathPoints;
    }

    private List<Position> buildLinearSegment() {
        return buildArcSegment(1);
    }

    private List<Position> buildArcSegment(int resolution) {
        int pointCount = resolution + 1;
        ArrayList<Position> positions = new ArrayList<>(pointCount);

        positions.add(startPoint);
        for (int i = 1; i < resolution; i++) {
            // TODO implement
        }
        positions.add(endPoint);

        return positions;
    }


    public int getType() {
        return type;
    }
}
