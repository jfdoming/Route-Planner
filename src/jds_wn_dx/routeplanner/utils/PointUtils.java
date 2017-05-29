package jds_wn_dx.routeplanner.utils;

import gov.nasa.worldwind.geom.Position;

import java.awt.geom.Point2D;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-28
 * Description: Provides utility methods used for maniplulating points.
 */
public class PointUtils {

    public static Point2D.Double toPoint(Position position) {
        return new Point2D.Double(x(position), y(position));
    }

    public static double x(Position position) {
        return position.getLongitude().getRadians();
    }

    public static double y(Position position) {
        return position.getLatitude().getRadians();
    }

    public static Point2D.Double average(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0);
    }

    public static Point2D.Double average(Position p1, Position p2) {
        return new Point2D.Double((x(p1) + x(p2)) / 2.0, (y(p1) + y(p2)) / 2.0);
    }

    public static double angle(Point2D.Double from, Point2D.Double to) {
        double dx = from.x - to.x;
        double dy = from.y - to.y;

        return Math.atan2(dy, dx);
    }

}
