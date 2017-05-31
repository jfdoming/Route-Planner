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

    // the points used to construct this route
    private List<Position> anchorPoints;

    // a list of all the segments forming this route
    private List<RouteSegment> segments;

    // a list of all result points (used for rendering)
    private List<Position> result;

    /**
     * Default constructor.
     */
    public Route() {
        anchorPoints = new ArrayList<>();
        segments = new ArrayList<>();
        result = new ArrayList<>();
    }

    /**
     * Starts this route at the specified position.
     *
     * @param startPosition the position to start the route at
     */
    public void start(Position startPosition) {
        result.add(startPosition);
        anchorPoints.add(startPosition);
    }

    /**
     * Extends this route to include a segment to the specified point.
     *
     * @param position the position to extend to
     * @param type     the type of segment to extend
     * @return the latest result list
     */
    public List<Position> extend(Position position, RouteSegmentType type) {
        if (anchorPoints.isEmpty()) {
            return null;
        }

        result = predict(position, type);
        segments.add(type.getSupplier().apply(anchorPoints.get(anchorPoints.size() - 1), position));
        anchorPoints.add(position);
        return result;
    }

    /**
     * Temporarily extends this route to include a segment to the specified point.
     *
     * @param position the position to extend to
     * @param type     the type of segment to extend
     * @return the calculated result list
     */
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

    // getters

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
