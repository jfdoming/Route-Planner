package jds_wn_dx.routeplanner.view;

import jds_wn_dx.routeplanner.controller.LoadListener;
import jds_wn_dx.routeplanner.controller.SaveListener;
import jds_wn_dx.routeplanner.model.RouteSegmentType;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ActionListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Route-Planner
 * Waseef Nayeem
 * 29/05/2017
 */
public class UIPanel extends JPanel {

    // text used on the start/stop button
    public static final String START_TEXT = "Start";
    public static final String STOP_TEXT = "Stop";

    private JButton startButton;
    private JTextField name;
    private JComboBox<RouteSegmentType> typeComboBox;
    private JSpinner altitudeSpinner;
    private SpinnerNumberModel model;

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
        name = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setLabelFor(name);

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem(RouteSegmentType.LINEAR);
        typeComboBox.addItem(RouteSegmentType.DESCENT);

        startButton = new JButton(START_TEXT);
        altitudeSpinner = new JSpinner();
        model = new SpinnerNumberModel(altitude, MIN_ALTITUDE, MAX_ALTITUDE, STEP);
        altitudeSpinner.setModel(model);

        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
            chooser.setFileFilter(filter);

            int returnVal = chooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File saveFile = chooser.getSelectedFile();

                // make sure the file name ends in .xml
                if (!saveFile.getName().endsWith(".xml")) {
                    saveFile = new File(saveFile.getAbsolutePath().concat(".xml"));
                }

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

                // make sure the file name ends in .xml
                if (!loadFile.getName().endsWith(".xml")) {
                    loadFile = new File(loadFile.getAbsolutePath().concat(".xml"));
                }

                for (LoadListener listener : loadListeners) {
                    listener.onLoad(loadFile);
                }
            }
        });

        JPanel topPanel = new ShrinkingPanel(false, true);
        topPanel.add(nameLabel);
        topPanel.add(name);
        topPanel.add(typeComboBox);
        topPanel.add(startButton);
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

    public void addSaveListener(SaveListener listener) {
        saveListeners.add(listener);
    }

    public void addLoadListener(LoadListener listener) {
        loadListeners.add(listener);
    }

    public void addStartStopListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    public double getAltitudeSpinnerValue() {
        return model.getNumber().intValue();
    }

    public void setStartStopText(String s) {
        startButton.setText(s);
    }

    public void setNameValue(String s) {
        name.setText(s);
    }

    public String getNameValue() {
        return name.getText();
    }

    public RouteSegmentType getSegmentType() {
        return (RouteSegmentType) typeComboBox.getSelectedItem();
    }
}
