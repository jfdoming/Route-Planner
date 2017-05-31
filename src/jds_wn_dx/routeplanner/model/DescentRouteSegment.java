package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents a descending part of a route the user has chosen in our application.
 */
public class DescentRouteSegment extends RouteSegment {

    public static final double PATH_INTERPOLATE_AMOUNT = 0.1;
    protected Position.PositionList pathPoints;

    public DescentRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint, RouteSegmentType.DESCENT);
    }

    public Position.PositionList buildSegment() {
        // cache the result of the path building
        if (pathPoints == null) {
            List<Position> positions = new ArrayList<>(3);

            Position middlePos;
            if (startPoint.getElevation() > endPoint.getElevation()) {
                middlePos = new Position(
                        Position.interpolateGreatCircle(1 - PATH_INTERPOLATE_AMOUNT, startPoint, endPoint), startPoint.getElevation());
            } else {
                middlePos = new Position(
                        Position.interpolateGreatCircle(PATH_INTERPOLATE_AMOUNT, startPoint, endPoint), endPoint.getElevation());
            }

            positions.add(middlePos);
            positions.add(endPoint);

            this.pathPoints = new Position.PositionList(positions);
        }
        return pathPoints;
    }
}
