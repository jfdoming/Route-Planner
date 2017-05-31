package jds_wn_dx.routeplanner.view;

import javax.swing.JPanel;
import java.awt.Dimension;

/**
 * Assignment: Route Planner
 * Author: Waseef Nayeem
 * Date: 2017-05-29
 * Description: Represents a JPanel that will only fill as much space as it needs.
 *
 * This object is a view object.
 */
public class ShrinkingPanel extends JPanel {

    private boolean horizontal;
    private boolean vertical;

    public ShrinkingPanel() {
        this(true, true);
    }

    public ShrinkingPanel(boolean horizontal, boolean vertical) {
        this.horizontal = horizontal;
        this.vertical = vertical;
    }

    @Override
    public Dimension getMaximumSize() {
        Dimension size = getPreferredSize();
        if (!horizontal) {
            size.width = Integer.MAX_VALUE;
        }
        if (!vertical) {
            size.height = Integer.MAX_VALUE;
        }
        return size;
    }
}
