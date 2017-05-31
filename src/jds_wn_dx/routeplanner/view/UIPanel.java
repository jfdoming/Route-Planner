package jds_wn_dx.routeplanner.view;

import jds_wn_dx.routeplanner.controller.ClearListener;
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
 * Assignment: Route Planner
 * Author: Waseef Nayeem
 * Date: 13/05/2017
 * Description: Represents the portion of the screen that contains the GUI. The user interacts with this part of the
 * application and there are widgets on the UIPanel that the user can interact with.
 *
 * This object is a view object.
 */
public class UIPanel extends JPanel {

    // text used on the start/stop button
    public static final String START_TEXT = "Start Route";
    public static final String STOP_TEXT = "Stop Route";
    public static final String CLEAR_TEXT = "Clear Route";

    // UI Widgets
    private JButton startButton;
    private JButton clearButton;
    private JTextField name;
    private JComboBox<RouteSegmentType> typeComboBox;
    private JSpinner altitudeSpinner;
    private SpinnerNumberModel model;

    // Lists of listeners
    private ArrayList<SaveListener> saveListeners;
    private ArrayList<LoadListener> loadListeners;

    private final double MAX_ALTITUDE = 1e6, MIN_ALTITUDE = 1e1, STEP = 1;
    private double altitude;
    private Runnable clearAction;

    /**
     *  Default Constructor
     * */
    public UIPanel() {
        saveListeners = new ArrayList<>();
        loadListeners = new ArrayList<>();

        altitude = MAX_ALTITUDE;

        init();
        initComponents();
    }

    /**
     *  Initializes the panel
     * */
    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(0,5,5,5));
    }

    /**
     *  Initializes the JComponents on the panel
     * */
    private void initComponents() {
        name = new JTextField(10);
        JLabel nameLabel = new JLabel("Name:");
        nameLabel.setLabelFor(name);

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem(RouteSegmentType.LINEAR);
        typeComboBox.addItem(RouteSegmentType.DESCENT);

        clearButton = new JButton(CLEAR_TEXT);
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

        // Organize top panel
        JPanel topPanel = new ShrinkingPanel(false, true);
        topPanel.add(nameLabel);
        topPanel.add(name);
        topPanel.add(typeComboBox);
        add(topPanel);

        // Organize middle panel
        JPanel midPanel = new ShrinkingPanel(false, true);
        midPanel.add(clearButton);
        midPanel.add(startButton);
        midPanel.add(altitudeSpinner);
        add(midPanel);

        // Extra space goes here
        add(Box.createVerticalGlue());

        // Add a separator
        add(new JSeparator(SwingConstants.HORIZONTAL) {
            @Override
            public Dimension getMaximumSize() {
                Dimension preferredSize = getPreferredSize();
                preferredSize.width = Integer.MAX_VALUE;
                return preferredSize;
            }
        });

        // Space the separator from the bottom
        add(Box.createVerticalStrut(5));

        // Organize bottom panel
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

    /**
     *  Adds a listener to the saveListener list
     * */
    public void addSaveListener(SaveListener listener) {
        saveListeners.add(listener);
    }

    /**
     *  Adds a listener to the loadListener list
     * */
    public void addLoadListener(LoadListener listener) {
        loadListeners.add(listener);
    }

    /**
     *  Adds a listener to the start/stop button
     * */
    public void addStartStopListener(ActionListener listener) {
        startButton.addActionListener(listener);
    }

    /**
     *  Adds a listener to the route clear button
     * */
    public void addClearListener(ClearListener listener) {
        clearButton.addActionListener(e -> listener.onClear());
    }

    /**
     *  Gets the value from the altitude spinner
     * */
    public double getAltitudeSpinnerValue() {
        return model.getNumber().intValue();
    }

    /**
     *  Sets the text of the start/stop button
     * */
    public void setStartStopText(String s) {
        startButton.setText(s);
    }

    /**
     *  Sets the text of the name text field
     * */
    public void setNameValue(String s) {
        name.setText(s);
    }

    /**
     *  Gets the text from the name text field
     * */
    public String getNameValue() {
        return name.getText();
    }

    /**
     *  Gets the type of route selected from the combo box
     * */
    public RouteSegmentType getSegmentType() {
        return (RouteSegmentType) typeComboBox.getSelectedItem();
    }
}
