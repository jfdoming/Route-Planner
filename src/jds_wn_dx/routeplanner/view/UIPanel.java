package jds_wn_dx.routeplanner.view;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.io.File;

/**
 * Route-Planner
 * Waseef Nayeem
 * 29/05/2017
 */
public class UIPanel extends JPanel {

    private boolean active;

    public UIPanel() {
        init();
        initComponents();
    }

    private void init() {
        setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
    }

    private void initComponents() {
        JTextField title = new JTextField(10);

        JComboBox<String> type = new JComboBox<>();
        type.addItem("LINEAR");
        type.addItem("DESCENT");
        JButton start = new JButton("Start");
        JButton save = new JButton("Save");
        JButton load = new JButton("Load");

        start.addActionListener(e -> {
            if (!active) {
                start.setText("Stop");
                active = true;
            } else {
                start.setText("Start");
                active = false;
            }
        });

        save.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showSaveDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File saveFile = chooser.getSelectedFile();
                System.out.println(saveFile);
            }
        });

        load.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if (returnVal == JFileChooser.APPROVE_OPTION) {
                File loadFile = chooser.getSelectedFile();
                System.out.println(loadFile);
            }
        });

        JPanel topPanel = new ShrinkingPanel(false, true);
        topPanel.add(title);
        topPanel.add(type);
        topPanel.add(start);
        add(topPanel);

        add(Box.createVerticalGlue());

        JPanel bottomPanel = new ShrinkingPanel(false, true);
        bottomPanel.setLayout(new GridBagLayout());
        GridBagConstraints cons = new GridBagConstraints();
        cons.fill = GridBagConstraints.HORIZONTAL;
        cons.weightx = 1;
        cons.gridx = GridBagConstraints.RELATIVE;
        cons.gridy = 0;

        bottomPanel.add(save, cons);
        bottomPanel.add(load, cons);
        add(bottomPanel);
    }
}
