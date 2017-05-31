package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents a route the user has chosen in our application.
 */
public class Route {

    private List<Position> anchorPoints;
    private List<Position> result;
    private List<RouteSegment> segments;

    public Route() {
        anchorPoints = new ArrayList<>();
        result = new ArrayList<>();
        segments = new ArrayList<>();
    }

    public void start(Position startPosition) {
        result.add(startPosition);
        anchorPoints.add(startPosition);
    }

    public List<Position> extend(Position position, RouteSegmentType type) {
        if (anchorPoints.isEmpty()) {
            return null;
        }

        result = predict(position, type);
        segments.add(type.getSupplier().apply(anchorPoints.get(anchorPoints.size() - 1), position));
        anchorPoints.add(position);
        return result;
    }

    public List<Position> predict(Position position, RouteSegmentType type) {
        if (anchorPoints.isEmpty()) {
            return null;
        }

        List<Position> returnValue = new ArrayList<>(result);

        if (!anchorPoints.isEmpty()) {
            Position lastPosition = anchorPoints.get(anchorPoints.size() - 1);

            RouteSegment segment = type.getSupplier().apply(lastPosition, position);
            returnValue.addAll(segment.buildSegment().list);
        }

        return returnValue;
    }

    public boolean isStarted() {
        return !anchorPoints.isEmpty();
    }

    public List<RouteSegment> getSegments() {
        return segments;
    }

    public List<Position> getResult() {
        return result;
    }
}
