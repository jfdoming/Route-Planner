package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents part of a route the user has chosen in our application.
 */
public abstract class RouteSegment {

    protected Position startPoint;
    protected Position endPoint;

    protected RouteSegmentType type;

    protected RouteSegment(Position startPoint, Position endPoint, RouteSegmentType type) {
        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.type = type;
    }

    public abstract Position.PositionList buildSegment();

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
