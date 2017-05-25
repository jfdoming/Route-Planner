package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents part of a route the user has chosen in our application.
 */
public class RouteSegment {

    // types of route segments
    public static final int LINEAR = 0;
    public static final int ARC = 1;

    /*
     * Since World Wind doesn't have the ability to draw arcs, we approximate them using lines.
     * This is the number of lines to draw.
     */
    private static final int ARC_RESOLUTION = 50;
    private final double arcRadius;

    private int type;

    private Position startPoint;
    private Position endPoint;

    private Position.PositionList pathPoints;

    public RouteSegment(Position startPoint, Position endPoint) {
        this.type = LINEAR;

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.arcRadius = 0;
    }

    public RouteSegment(Position startPoint, Position endPoint, double radius) {
        this.type = ARC;

        this.startPoint = startPoint;
        this.endPoint = endPoint;
        this.arcRadius = radius;
    }

    public Position.PositionList buildPath() {
        // cache the result of the path building
        if (pathPoints == null) {
            List<Position> list;

            switch (type) {
                case LINEAR:
                    list = Collections.unmodifiableList(buildLinearSegment());
                    break;
                case ARC:
                    list = Collections.unmodifiableList(buildArcSegment(arcRadius, ARC_RESOLUTION));
                    break;
                default:
                    throw new IllegalArgumentException("Invalid route type!");
            }

            this.pathPoints = new Position.PositionList(list);
        }

        return pathPoints;
    }

    private List<Position> buildLinearSegment() {
        ArrayList<Position> positions = new ArrayList<>(2);
        positions.add(startPoint);
        positions.add(endPoint);
        return positions;
    }

    private List<Position> buildArcSegment(double radius, int resolution) {
//        Line line = new Line(startPoint, endPoint);
//        Point2D.Double avg = startPoint.
//        Line perpLine = new Line(line);
//        perpLine.perp(avg);
//        Point2D.Double radiusPoint = perpLine.getClosestPoint(new Point2D(mouseX, mouseY));
//        Point2D.Double closestPoint = line.getClosestPoint(radiusPoint);
//
//        Line perpBisector1 = new Line(p1, radiusPoint);
//        perpBisector1.perp(p1.avg(radiusPoint));
//        Line perpBisector2 = new Line(p2, radiusPoint);
//        perpBisector2.perp(p2.avg(radiusPoint));
//        Point2D circleCentre = perpBisector1.getIntersection(perpBisector2);

        int pointCount = resolution + 1;
        ArrayList<Position> positions = new ArrayList<>(pointCount);

        positions.add(startPoint);
        for (int i = 1; i < resolution; i++) {
            // TODO implement
        }
        positions.add(endPoint);

        return positions;
    }


    public int getType() {
        return type;
    }
}
