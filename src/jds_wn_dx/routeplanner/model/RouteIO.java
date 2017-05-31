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

    // used to write to an XML file
    private final XML_IO io;

    /**
     * Default constructor.
     */
    public RouteIO() {
        this.io = new XML_IO();
    }

    /**
     * Converts a Position to its String representation.
     *
     * @param position the position to convert to a string
     * @return a string representing the provided position
     */
    private String toString(Position position) {
        Angle lat = position.getLatitude();
        Angle lon = position.getLongitude();
        return String.format(POSITION_FORMAT, lat.getDegrees(), lon.getDegrees(), position.getElevation());
    }

    /**
     * Writes the specified data the the specified XML file.
     *
     * @param data the data to write to file
     * @param out the XML file to write to
     */
    public void writeToXML(Data data, File out) {
        List<RouteSegment> routeResult = data.getRoute().getSegments();

        ArrayList<String> routeSegmentTypes = new ArrayList<>();
        ArrayList<String> startPositionStrings = new ArrayList<>();
        ArrayList<String> endPositionStrings = new ArrayList<>();

        // convert our route to a format easily usable by the XML code
        for (RouteSegment segment : routeResult) {
            routeSegmentTypes.add(segment.getType().toString());

            startPositionStrings.add(toString(segment.getStartPoint()));
            endPositionStrings.add(toString(segment.getEndPoint()));
        }

        // output our altered data to the file
        io.outputFile(routeSegmentTypes, startPositionStrings, endPositionStrings, data.getName(), out);
    }

    /**
     * Converts a String representation of a Position to a Position.
     *
     * @param positionString a string representing a position
     * @return a position represented by the provided string
     */
    private Position parsePosition(String positionString) {
        if (!positionString.matches(POSITION_REGEX)) {
            // this XML is malformed, tell the user
            throw new IllegalArgumentException("Invalid file format!");
        }

        // determine imformation needed to instantiate a position
        String[] elements = positionString.split(SEPARATOR_REGEX);
        Angle lat = Angle.fromDegreesLatitude(Double.parseDouble(elements[0]));
        Angle lon = Angle.fromDegreesLongitude(Double.parseDouble(elements[1]));
        double elevation = Double.parseDouble(elements[2]);

        // instantiate the position object
        return new Position(lat, lon, elevation);
    }

    /**
     * Reads from the specified XML file, providing the data read to the caller.
     *
     * @param in the XML file to read from
     * @return a Data object representing the data read from file
     */
    public Data readFromXML(File in) {
        // load the file data
        io.inputFile(in);
        io.setData();

        // prepare to convert from String[] to Route form
        ArrayList<String> routeSegmentTypes = io.getRouteType();
        ArrayList<String> endPositionStrings = io.getEnd();

        // start our route
        Route route = new Route();
        route.start(parsePosition(io.getStart().get(0)));

        // continue our route as necessary
        for (int i = 0; i < endPositionStrings.size(); i++) {
            String endString = endPositionStrings.get(i);
            String segmentType = routeSegmentTypes.get(i);

            route.extend(parsePosition(endString), RouteSegmentType.valueOf(segmentType));
        }

        // return our model to the caller
        return new Data(route, io.getName());
    }

    /**
     * Represents the data read from/written to an XML file.
     */
    public static class Data {

        // the route represented by the XML
        private Route route;

        // the name of the route
        private String name;

        /**
         * Constructor.
         *
         * @param route the route to save
         * @param name the name of the route
         */
        public Data(Route route, String name) {
            this.route = route;
            this.name = name;
        }

        // getters

        public Route getRoute() {
            return route;
        }

        public String getName() {
            return name;
        }
    }

}
