package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Position;
import jds_wn_dx.routeplanner.utils.PointUtils;

import java.awt.geom.Point2D;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 18/05/2017
 * Description: Represents an arc-shaped part of a route the user has chosen in our application.
 */
public class ArcRouteSegment extends RouteSegment{

    /*
     * Since World Wind doesn't have the ability to draw arcs, we approximate them using lines.
     * This is the number of lines to draw.
     */
    private static final int ARC_RESOLUTION = 50;

    public ArcRouteSegment(Position startPoint, Position endPoint) {
        super(startPoint, endPoint);
    }

    public Position.PositionList buildSegment(Position mousePosition) {
        List<Position> positions = Collections.unmodifiableList(buildArcSegment(mousePosition, ARC_RESOLUTION));
        return new Position.PositionList(positions);
    }

    private List<Position> buildArcSegment(Position mousePosition, int resolution) {
        int pointCount = resolution + 1;

        ArrayList<Position> positions = new ArrayList<>(pointCount);
        positions.add(startPoint);

        Point2D.Double p1 = PointUtils.toPoint(startPoint);
        Point2D.Double p2 = PointUtils.toPoint(endPoint);

        Line line = new Line(p1, p2);
        Point2D.Double avg = PointUtils.average(p1, p2);
        Line perpLine = new Line(line);
        perpLine.perp(avg);
        Point2D.Double radiusPoint = perpLine.getClosestPoint(PointUtils.toPoint(mousePosition));

        Line perpBisector1 = new Line(p1, radiusPoint);
        perpBisector1.perp(PointUtils.average(p1, radiusPoint));
        Point2D.Double circleCentre = perpBisector1.getIntersection(perpLine);

        if (circleCentre != null) {
            double radius = circleCentre.distance(radiusPoint);

            // get the unadjusted start and end angles
            double initialAngle = PointUtils.angle(p1, circleCentre);
            double finalAngle = PointUtils.angle(p2, circleCentre);

            // determine where the radius point is relative to the line
            int position = line.getPosition(radiusPoint);

            // make sure we draw our arc starting and ending in the right place
            if ((position == 1) ^ (p1.y > p2.y)) {
                double temp = initialAngle;
                initialAngle = finalAngle;
                finalAngle = temp;
            }

            // make sure the start angle < the end angle
            if (finalAngle < initialAngle) {
                finalAngle += 2 * Math.PI;
            }

//            double initialX = (Math.cos(initialAngle) * radius) + circleCentre.x;
//            double initialY = (Math.sin(initialAngle) * radius) + circleCentre.y;
//            double finalX = (Math.cos(finalAngle) * radius) + circleCentre.x;
//            double finalY = (Math.sin(finalAngle) * radius) + circleCentre.y;
//            double lastX = initialX;
//            double lastY = initialY;
            double step = 2 * Math.PI / resolution;
            for (double a = step + initialAngle; a < finalAngle; a += step) {
                double x = (radius * Math.cos(a)) + circleCentre.x;
                double y = (radius * Math.sin(a)) + circleCentre.y;

                positions.add(new Position(Position.fromRadians(x, y), 50000));
            }

//            double a = Math.toRadians(120), b = Math.toRadians(360);
//
//            for (int i = 1; i < resolution; i++) {
//                // TODO implement
//                double z = (b - a) / resolution;
//                Position p = new Position(LatLon.fromRadians(Math.cos(a + z * i) * radius, Math.sin(a + z * i) * radius), 500000);
//                positions.add(p);
//            }
        }
//        positions.add(endPoint);

        return positions;
    }
}
