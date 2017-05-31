package jds_wn_dx.routeplanner.view;

import jds_wn_dx.routeplanner.controller.LoadListener;
import jds_wn_dx.routeplanner.controller.SaveListener;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.event.ChangeListener;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Route-Planner
 * Waseef Nayeem
 * 29/05/2017
 */
public class UIPanel extends JPanel {

    private boolean canSelectPaths;
    private JButton startButton;
    private JTextField title;
    private JSpinner altitudeSpinner;
    private SpinnerNumberModel model;
    private JComboBox<String> typeComboBox;

    private ArrayList<SaveListener> saveListeners;
    private ArrayList<LoadListener> loadListeners;

    private final double MAX_ALTITUDE = 1e6, MIN_ALTITUDE = 1e1, STEP = 1;
    private double altitude;

    public UIPanel() {
        saveListeners = new ArrayList<>();
        loadListeners = new ArrayList<>();

        altitude = MAX_ALTITUDE;

        init();
        initComponents();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(0,5,5,5));
    }

    private void initComponents() {
        title = new JTextField(25);

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem("LINEAR");
        typeComboBox.addItem("DESCENT");

        altitudeSpinner = new JSpinner();
        model = new SpinnerNumberModel(altitude, MIN_ALTITUDE, MAX_ALTITUDE, STEP);
        altitudeSpinner.setModel(model);

        startButton = new JButton("Start");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File saveFile = chooser.getSelectedFile();
                for (SaveListener listener : saveListeners) {
                    listener.onSave(saveFile);
                }
            }
        });

        loadButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter("XML Files", "xml");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File loadFile = chooser.getSelectedFile();
                for (LoadListener listener : loadListeners) {
                    listener.onLoad(loadFile);
                }
            }
        });

        JPanel topPanel = new ShrinkingPanel(false, true);
        topPanel.add(title);
        add(topPanel);

        JPanel midPanel = new ShrinkingPanel(false, true);
        midPanel.add(typeComboBox);
        midPanel.add(startButton);
        midPanel.add(altitudeSpinner);
        add(midPanel);

        // extra space goes here
        add(Box.createVerticalGlue());

        // add a separator
        add(new JSeparator(SwingConstants.HORIZONTAL) {
            @Override
            public Dimension getMaximumSize() {
                Dimension preferredSize = getPreferredSize();
                preferredSize.width = Integer.MAX_VALUE;
                return preferredSize;
            }
        });

        // space the separator from the bottom
        add(Box.createVerticalStrut(5));

        JPanel bottomPanel = new ShrinkingPanel(false, true);
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = GridBagConstraints.RELATIVE;
        cons.gridy = 0;

        bottomPanel.add(saveButton, cons);
        bottomPanel.add(loadButton, cons);
        add(bottomPanel);
    }

    public void addItemListener(ItemListener aListener) {
        typeComboBox.addItemListener(aListener);
    }

    public void addSaveListener(SaveListener listener) {
        saveListeners.add(listener);
    }

    public void addLoadListener(LoadListener listener) {
        loadListeners.add(listener);
    }

    public void addStartStopListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public String getTitleValue() {
        return title.getText();
    }

    public double getAltitudeSpinnerValue() {
        return model.getNumber().intValue();
    }

    public void setStartStopText(String s) {
        startButton.setText(s);
    }
}
