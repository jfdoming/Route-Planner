package jds_wn_dx.routeplanner.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JSeparator;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.event.ItemListener;
import java.io.File;
import java.util.ArrayList;

/**
 * Route-Planner
 * Waseef Nayeem
 * 29/05/2017
 */
public class UIPanel extends JPanel {

    private boolean active;
    private JButton startButton;
    private JTextField title;
    private JComboBox<String> typeComboBox;

    private ArrayList<SaveListener> saveListeners;
    private ArrayList<LoadListener> loadListeners;

    public UIPanel() {
        saveListeners = new ArrayList<>();
        loadListeners = new ArrayList<>();

        init();
        initComponents();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
        setBorder(new EmptyBorder(0,5,5,5));
    }

    private void initComponents() {
        title = new JTextField(10);

        typeComboBox = new JComboBox<>();
        typeComboBox.addItem("LINEAR");
        typeComboBox.addItem("DESCENT");

        startButton = new JButton("Start");
        JButton saveButton = new JButton("Save");
        JButton loadButton = new JButton("Load");

        startButton.addActionListener(e -> {
            if (!active) {
                startButton.setText("Stop");
                active = true;
            } else {
                startButton.setText("Start");
                active = false;
            }
        });

        saveButton.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
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
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
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
        topPanel.add(typeComboBox);
        topPanel.add(startButton);
        add(topPanel);

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
}
