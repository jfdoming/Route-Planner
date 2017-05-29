package jds_wn_dx.routeplanner.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import gov.nasa.worldwind.render.Path;
import jds_wn_dx.routeplanner.model.LinearRouteSegment;
import jds_wn_dx.routeplanner.model.RouteSegment;

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
    private final RouteSegment segment;

    public PositionListener(WorldWindow wwd, Path modify) {
        this.wwd = wwd;
        this.modify = modify;
        this.segment = new LinearRouteSegment(Position.fromDegrees(90, 0, 1e6), Position.fromDegrees(80, 0, 1e5));
    }

    @Override
    public void mouseClicked(MouseEvent event) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        Position mousePosition = wwd.getCurrentPosition();
        if (mousePosition != null) {
            modify.setPositions(segment.buildSegment(mousePosition).list);
        }
    }
}
