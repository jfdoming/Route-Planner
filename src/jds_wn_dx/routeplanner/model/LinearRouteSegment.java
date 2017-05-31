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

    /**
     * Constructor.
     *
     * @param startPoint the point to start at
     * @param endPoint   the point to end at
     */
    public LinearRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint, RouteSegmentType.LINEAR);
    }

    /**
     * Builds this segment into a list of positions.
     *
     * @return a list of resultant positions
     */
    public Position.PositionList buildSegment() {
        List<Position> positions = new ArrayList<>(3);
        positions.add(endPoint);
        return new Position.PositionList(positions);
    }
}
