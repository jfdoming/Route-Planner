package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents part of a route the user has chosen in our application.
 *
 * This object is a model object.
 */
public abstract class RouteSegment {

    // the point this segment starts at
    protected Position startPoint;

    // the point this segment ends at
    protected Position endPoint;

    // the type of this segment
    protected RouteSegmentType type;

    /**
     * Constructor.
     *
     * @param startPoint the point to start at
     * @param endPoint the point to end at
     * @param type the type of this segment
     */
    protected RouteSegment(Position startPoint, Position endPoint, RouteSegmentType type) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.type = type;
    }

    /**
     * Builds this segment into a list of positions.
     *
     * @return a list of resultant positions
     */
    public abstract Position.PositionList buildSegment();

    // getters

    public Position getStartPoint() {
        return startPoint;
    }

    public Position getEndPoint() {
        return endPoint;
    }

    public RouteSegmentType getType() {
        return type;
    }
}
