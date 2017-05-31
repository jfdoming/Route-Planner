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

    /**
     * Converts a Position to a Point2D.
     *
     * @param position the position to convert
     * @return the point
     */
    public static Point2D.Double toPoint(Position position) {
        return new Point2D.Double(x(position), y(position));
    }

    /**
     * Determines the x-value of a position.
     *
     * @param position the position to access
     * @return the x value
     */
    public static double x(Position position) {
        return position.getLongitude().getRadians();
    }

    /**
     * Determines the y-value of a position.
     *
     * @param position the position to access
     * @return the y value
     */
    public static double y(Position position) {
        return position.getLatitude().getRadians();
    }

    /**
     * Determines the point which is the average of two other points.
     *
     * @param p1 the first point
     * @param p2 the second point
     * @return the average point
     */
    public static Point2D.Double average(Point2D.Double p1, Point2D.Double p2) {
        return new Point2D.Double((p1.x + p2.x) / 2.0, (p1.y + p2.y) / 2.0);
    }

    /**
     * Determines the point which is the average of two other positions.
     *
     * @param p1 the first positions
     * @param p2 the second positions
     * @return the average point
     */
    public static Point2D.Double average(Position p1, Position p2) {
        return new Point2D.Double((x(p1) + x(p2)) / 2.0, (y(p1) + y(p2)) / 2.0);
    }

    /**
     * Determines the angle between two points.
     *
     * @param from the first positions
     * @param to the second positions
     * @return the angle between the two points
     */
    public static double angle(Point2D.Double from, Point2D.Double to) {
        double dx = from.x - to.x;
        double dy = from.y - to.y;

        return Math.atan2(dy, dx);
    }

}
