package jds_wn_dx.routeplanner.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;
import jds_wn_dx.routeplanner.model.DescentRouteSegment;
import jds_wn_dx.routeplanner.model.Route;

import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 25/05/2017
 * Description:
 */
public class PositionListener extends MouseAdapter {
    private final WorldWindow wwd;
    private final Path modify;
    private final Route route;

    public PositionListener(WorldWindow wwd, Path modify) {
        this.wwd = wwd;
        this.modify = modify;
        this.route = new Route(Position.fromDegrees(90, 0, 1e6));
    }

    @Override
    public void mouseClicked(MouseEvent event) {
        event.consume();

        Position mousePosition = wwd.getCurrentPosition();
        if (mousePosition != null) {
            mousePosition = new Position(mousePosition, 1e6);
            modify.setPositions(route.extend(mousePosition, DescentRouteSegment::new));
        }
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position mousePosition = wwd.getCurrentPosition();
        if (mousePosition != null) {
            mousePosition = new Position(mousePosition, 1e6);
            modify.setPositions(route.predict(mousePosition, DescentRouteSegment::new));
        }
    }
}
