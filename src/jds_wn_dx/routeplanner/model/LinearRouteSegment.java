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

    protected Position.PositionList pathPoints;

    public LinearRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint);
    }

    public Position.PositionList buildSegment(Position mousePosition) {
        // cache the result of the path building
        if (pathPoints == null) {
            List<Position> positions = new ArrayList<>(3);

            positions.add(startPoint);

            Position middlePos;
            if (startPoint.getElevation() > endPoint.getElevation()) {
                middlePos = new Position(
                        Position.interpolateGreatCircle(0.9, startPoint, endPoint), startPoint.getElevation());
            } else {
                middlePos = new Position(
                        Position.interpolateGreatCircle(0.1, startPoint, endPoint), endPoint.getElevation());
            }

            positions.add(middlePos);
            positions.add(endPoint);

            this.pathPoints = new Position.PositionList(positions);
        }
        return pathPoints;
    }
}
