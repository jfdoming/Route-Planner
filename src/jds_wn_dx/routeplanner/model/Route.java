package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;
import java.util.function.BiFunction;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents a route the user has chosen in our application.
 */
public class Route {

    private ArrayList<Position> anchorPoints;
    private List<Position> result;

    public Route(Position startPosition) {
        anchorPoints = new ArrayList<>();
        result = new ArrayList<>();

        result.add(startPosition);
        anchorPoints.add(startPosition);
    }

//    public Route(Position startPosition, ArrayList<Position> endPoints) {
//        anchorPoints = new ArrayList<>();
//        result = new ArrayList<>();
//
//        result.add(startPosition);
//        anchorPoints.add(startPosition);
//    }

    public List<Position> extend(Position position, BiFunction<Position, Position, RouteSegment> supplier) {
        result = predict(position, supplier);
        anchorPoints.add(position);
        return result;
    }

    public List<Position> predict(Position position, BiFunction<Position, Position, RouteSegment> supplier) {
        List<Position> returnValue = new ArrayList<>(result);

        if (!anchorPoints.isEmpty()) {
            Position lastPosition = anchorPoints.get(anchorPoints.size() - 1);

            RouteSegment segment = supplier.apply(lastPosition, position);
            returnValue.addAll(segment.buildSegment().list);
        }

        return returnValue;
    }
}
