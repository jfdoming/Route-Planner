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

    private ArrayList<RouteSegment> children;
    private Position.PositionList routePoints;

    public Route() {
        children = new ArrayList<>();
    }

    public RouteSegment get(int index) {
        return children.get(index);
    }

    public void add(RouteSegment routeSegment) {
        children.add(routeSegment);

        // make sure we rebuild the route point list, as the
        routePoints = null;
    }

    public Position.PositionList buildRoute() {
        // cache the result of the route building
        if (routePoints == null) {
            List<Position> list = new ArrayList<>();

            for (RouteSegment child : children) {
                list.addAll(child.buildSegment(null).list);
            }

            this.routePoints = new Position.PositionList(list);
        }

        return routePoints;
    }
}
