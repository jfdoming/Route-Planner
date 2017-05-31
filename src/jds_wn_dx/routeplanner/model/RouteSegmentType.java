package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.function.BiFunction;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-30
 * Description: Represents various types of segments.
 *
 * This object is a model object.
 */
public enum RouteSegmentType {

    LINEAR(LinearRouteSegment::new), DESCENT(DescentRouteSegment::new);

    // the constructor used to instantiate a segment of this type
    private final BiFunction<Position, Position, RouteSegment> supplier;

    /**
     * Constructor.
     *
     * @param supplier the constructor to use
     */
     RouteSegmentType(BiFunction<Position, Position, RouteSegment> supplier) {
        this.supplier = supplier;
    }

    /**
     * @return the constructor function
     */
    public BiFunction<Position, Position, RouteSegment> getSupplier() {
        return supplier;
    }
}
