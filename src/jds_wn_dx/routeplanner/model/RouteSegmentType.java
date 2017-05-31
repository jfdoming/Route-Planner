package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.function.BiFunction;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-30
 * Description: Represents various types of segments.
 */
public enum RouteSegmentType {

    LINEAR(LinearRouteSegment::new), DESCENT(DescentRouteSegment::new);

    private final BiFunction<Position, Position, RouteSegment> supplier;

    RouteSegmentType(BiFunction<Position, Position, RouteSegment> supplier) {
        this.supplier = supplier;
    }

    public BiFunction<Position, Position, RouteSegment> getSupplier() {
        return supplier;
    }
}
