package jds_wn_dx.routeplanner.model;

import gov.nasa.worldwind.geom.Angle;
import gov.nasa.worldwind.geom.Position;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-30
 * Description: Represents a class that manages I/O operations for a route.
 */
public class RouteIO {

    // regexes used to ensure a position is in a valid format
    private static final String DECIMAL_REGEX = "[-+]?\\d*(\\.\\d+)?";
    private static final String SEPARATOR_REGEX = ",\\s*";
    private static final String POSITION_REGEX = DECIMAL_REGEX + SEPARATOR_REGEX + DECIMAL_REGEX + SEPARATOR_REGEX + DECIMAL_REGEX;

    // format specifiers used to output a position
    private static final String POSITION_FORMAT = "%f, %f, %f";

    private final XML_IO io;

    public RouteIO() {
        this.io = new XML_IO();
    }

    private String writePosition(Position position) {
        Angle lat = position.getLatitude();
        Angle lon = position.getLongitude();
        return String.format(POSITION_FORMAT, lat.getDegrees(), lon.getDegrees(), position.getElevation());
    }

    public void writeToXML(Data data, File out) {
        List<RouteSegment> routeResult = data.getRoute().getSegments();

        ArrayList<String> routeSegmentTypes = new ArrayList<>();
        ArrayList<String> startPositionStrings = new ArrayList<>();
        ArrayList<String> endPositionStrings = new ArrayList<>();

        for (RouteSegment segment : routeResult) {
            routeSegmentTypes.add(segment.getType().toString());

            startPositionStrings.add(writePosition(segment.getStartPoint()));
            endPositionStrings.add(writePosition(segment.getEndPoint()));
        }

        io.outputFile(routeSegmentTypes, startPositionStrings, endPositionStrings, data.getName(), out);
    }

    private Position parsePosition(String positionString) {
        if (!positionString.matches(POSITION_REGEX)) {
            throw new IllegalArgumentException("Invalid file format!");
        }

        String[] elements = positionString.split(SEPARATOR_REGEX);
        Angle lat = Angle.fromDegreesLatitude(Double.parseDouble(elements[0]));
        Angle lon = Angle.fromDegreesLongitude(Double.parseDouble(elements[1]));
        double elevation = Double.parseDouble(elements[2]);

        return new Position(lat, lon, elevation);
    }

    public Data readFromXML(File in) {
        io.inputFile(in);
        io.setData();

        ArrayList<String> routeSegmentTypes = io.getRouteType();
        ArrayList<String> endPositionStrings = io.getEnd();

        Route route = new Route();
        route.start(parsePosition(io.getStart().get(0)));

        for (int i = 0; i < endPositionStrings.size(); i++) {
            String endString = endPositionStrings.get(i);
            String segmentType = routeSegmentTypes.get(i);

            route.extend(parsePosition(endString), RouteSegmentType.valueOf(segmentType));
        }

        return new Data(route, io.getName());
    }

    public static class Data {

        private Route route;
        private String name;

        public Data(Route route, String name) {
            this.route = route;
            this.name = name;
        }

        public Route getRoute() {
            return route;
        }

        public String getName() {
            return name;
        }
    }

}
