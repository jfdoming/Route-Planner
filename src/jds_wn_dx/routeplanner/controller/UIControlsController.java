package jds_wn_dx.routeplanner.controller;

import jds_wn_dx.routeplanner.model.XML_IO;
import jds_wn_dx.routeplanner.view.LoadListener;
import jds_wn_dx.routeplanner.view.SaveListener;
import jds_wn_dx.routeplanner.view.UIPanel;

import java.io.File;

/**
 * Assignment: Route Planner
 * Author: Julian Dominguez-Schatz
 * Date: 2017-05-29
 * Description: Controls the UI controls on the side of the screen.
 */
public class UIControlsController implements SaveListener, LoadListener {

    private final UIPanel panel;

    public UIControlsController(UIPanel panel) {
        this.panel = panel;

        panel.addSaveListener(this);
        panel.addLoadListener(this);
    }

    @Override
    public void onSave(File out) {
    }

    @Override
    public void onLoad(File out) {
        XML_IO io = new XML_IO();
        io.inputFile(out);
    }
}
