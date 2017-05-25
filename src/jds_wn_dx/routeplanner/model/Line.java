package jds_wn_dx.routeplanner.model;

import java.awt.geom.Point2D;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 25/05/2017
 * Description: Represents a line.
 */
public class Line {

    private double slope;
    private double intercept;
    private boolean verticalLine;

    public Line(double slope, double yIntercept) {
        this.verticalLine = false;
        this.slope = slope;
        this.intercept = yIntercept;
    }

    public Line(double xIntercept) {
        this.verticalLine = true;
        this.intercept = xIntercept;
    }

    public Line(Line line) {
        this.verticalLine = line.verticalLine;
        this.slope = line.slope;
        this.intercept = line.intercept;
    }

    public Line(Point2D.Double first, Point2D.Double second) {
        double dx = second.x - first.x;
        double dy = second.y - first.y;
        if (dx == 0) {
            if (dy == 0) {
                throw new IllegalArgumentException("Identical points!");
            }

            this.verticalLine = true;
            this.intercept = first.x;
        } else {
            this.slope = dy / dx;
            this.intercept = first.y - (this.slope * first.x);
        }
    }

    private Point2D solveVerticalIntersection(Line vert, Line other) {
        double x = vert.intercept;
        double y = other.eval(x);

        return new Point2D.Double(x, y);
    }

    private Point2D solveIntersection(Line first, Line other) {
        double x = (other.intercept - first.intercept) / (first.slope - other.slope);
        double y = first.eval(x);

        return new Point2D.Double(x, y);
    }

    public Point2D getIntersection(Line other) {
        if ((this.verticalLine && other.verticalLine) ||
        (!(this.verticalLine || other.verticalLine) && this.slope == other.slope)) {
            // the other line is either parallel or congruent to this line; either way the intersection point isn't useful
            return null;
        }

        if (this.verticalLine) {
            return solveVerticalIntersection(this, other);
        } else if (other.verticalLine) {
            return solveVerticalIntersection(other, this);
        }
        return solveIntersection(this, other);
    }

    public double eval(double x) {
        if (this.verticalLine) {
            return this.intercept;
        }

        return (this.slope * x) + this.intercept;
    }

    public void perp(Point2D.Double perpPoint) {
        if (!this.verticalLine && this.slope == 0) {
            this.verticalLine = true;
            this.intercept = perpPoint.x;
        } else {
            if (this.verticalLine) {
                this.verticalLine = false;
                this.slope = 0;
            } else {
                this.slope = -1.0 / this.slope;
            }

            this.intercept = perpPoint.y - (this.slope * perpPoint.x);
        }
    }

    public Point2D getClosestPoint(Point2D.Double point) {
        Line temp = new Line(this);
        temp.perp(point);
        return temp.getIntersection(this);
    }

    public double getAngle() {
        if (this.verticalLine) {
            return Math.PI / 2;
        }

        return Math.atan2(1, this.eval(1) - this.eval(0));
    }

    @Override
    public String toString() {
        if (this.verticalLine) {
            return "Line{slope=undefined, x-intercept=" + this.intercept + "}";
        }
        return "Line{slope=" + this.slope + ", y-intercept=" + this.intercept + "}";
    }
}