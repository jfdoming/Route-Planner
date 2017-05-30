package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents a linear part of a route the user has chosen in our application.
 */
public class LinearRouteSegment extends RouteSegment {

    private Position.PositionList pathPoints;

    public LinearRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint);
    }

    public Position.PositionList buildSegment() {
        // cache the result of the path building
        if (pathPoints == null) {
            List<Position> positions = new ArrayList<>(3);

            positions.add(endPoint);

            this.pathPoints = new Position.PositionList(positions);
        }
        return pathPoints;
    }
}
