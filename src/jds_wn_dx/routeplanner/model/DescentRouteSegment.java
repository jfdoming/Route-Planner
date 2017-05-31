package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents a descending part of a route the user has chosen in our application.
 *
 * This object is a model object.
 */
public class DescentRouteSegment extends RouteSegment {

    // the factor before the path fully ascends
    private static final double PATH_INTERPOLATE_AMOUNT = 0.1;

    /**
     * Constructor.
     *
     * @param startPoint the point to start at
     * @param endPoint the point to end at
     */
    public DescentRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint, RouteSegmentType.DESCENT);
    }

    /**
     * Builds this segment into a list of positions.
     *
     * @return a list of resultant positions
     */
    public Position.PositionList buildSegment() {
        List<Position> positions = new ArrayList<>(3);

        // determine the middle point of the descending segment
        Position middlePos;
        if (startPoint.getElevation() > endPoint.getElevation()) {
            middlePos = new Position(
                    Position.interpolateGreatCircle(1 - PATH_INTERPOLATE_AMOUNT, startPoint, endPoint), startPoint.getElevation());
        } else {
            middlePos = new Position(
                    Position.interpolateGreatCircle(PATH_INTERPOLATE_AMOUNT, startPoint, endPoint), endPoint.getElevation());
        }

        // add the last points
        positions.add(middlePos);
        positions.add(endPoint);

        return new Position.PositionList(positions);
    }
}
