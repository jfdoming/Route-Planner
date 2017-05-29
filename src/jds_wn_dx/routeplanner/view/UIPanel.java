package jds_wn_dx.routeplanner.view;

import gov.nasa.worldwind.WorldWindow;
import gov.nasa.worldwind.geom.Position;
import jds_wn_dx.routeplanner.model.Route;
import jds_wn_dx.routeplanner.model.RouteSegment;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

/**
 * Assignment: Route Planner
 * Author: Waseef Nayeem
 * Date: 07/05/2017
 * Description: Object that contains UI elements
 *
 * This object is a view object.
 */
public class UIPanel extends JPanel implements MouseListener {

    private WorldWindow window;
    private boolean active, started;

    private Route route;
    private Position start, end;

    public UIPanel(WorldWindow window) {
        this.setPreferredSize(new Dimension(160, 480));

        this.setFocusable(true);
        this.addMouseListener(this);

        route = new Route();

        setWindow(window);
        initComponents();
    }

    private void initComponents() {
        JButton start = new JButton("Start");
        JButton stop = new JButton("Stop");

        start.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = true;
            }
        });

        stop.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                active = false;
            }
        });

        this.add(start);
        this.add(stop);
    }

    public void setWindow(WorldWindow window) {
        this.window = window;
    }

    public Position getCurrentMousePosition() {
        return window.getCurrentPosition();
    }

    @Override
    public void mouseClicked(MouseEvent e) {

    }

    @Override
    public void mousePressed(MouseEvent e) {
        if (active && !started) {
            start = getCurrentMousePosition();
            started = true;
        }

        if (active && started) {
            end = getCurrentMousePosition();
            started = false;

            route.add(new RouteSegment(start, end));
        }
    }

    public Route getRoute() {
        return route;
    }

    @Override
    public void mouseReleased(MouseEvent e) {

    }

    @Override
    public void mouseEntered(MouseEvent e) {

    }

    @Override
    public void mouseExited(MouseEvent e) {

    }
}
