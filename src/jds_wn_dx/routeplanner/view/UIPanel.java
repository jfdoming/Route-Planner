package jds_wn_dx.routeplanner.view;

import jds_wn_dx.routeplanner.utils.UIEvents;

import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.util.Observable;

/**
 * Route-Planner
 * Waseef Nayeem
 * 29/05/2017
 */
public class UIPanel extends JPanel implements Observable {

    private UIEvents events;

    private final int WIDTH = 160, HEIGHT = 600;
    private GroupLayout layout;

    private String saveFile, loadFile;
    private boolean active;

    public UIPanel() {
        init();
        initComponents();
    }

    private void init() {
        //layout = new GroupLayout(this);
        //setLayout(layout);
        //layout.setAutoCreateGaps(true);
        //layout.setAutoCreateContainerGaps(true);

        setSize(new Dimension(WIDTH, HEIGHT));
        setBackground(Color.BLACK);
    }

    private void initComponents() {
        JTextField title = new JTextField(10);
        JComboBox<String> type = new JComboBox<>();
        type.addItem("LINEAR");
        type.addItem("ARC");
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
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                saveFile = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(saveFile);
            }
        });

        load.addActionListener(e -> {
            JFileChooser chooser = new JFileChooser();
            FileNameExtensionFilter filter = new FileNameExtensionFilter(
                    "XML Files", "xml");
            chooser.setFileFilter(filter);
            int returnVal = chooser.showOpenDialog(this);
            if(returnVal == JFileChooser.APPROVE_OPTION) {
                loadFile = chooser.getSelectedFile().getAbsolutePath();
                System.out.println(loadFile);
            }
        });

        add(title);
        add(type);
        add(start);
        add(save);
        add(load);

//        layout.setHorizontalGroup(
//                layout.createSequentialGroup()
//                        .addComponent(title)
//                        .addComponent(type)
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.LEADING)
//                                .addComponent(start)
//                                .addComponent(load))
//        );
//        layout.setVerticalGroup(
//                layout.createSequentialGroup()
//                        .addGroup(layout.createParallelGroup(GroupLayout.Alignment.BASELINE)
//                                .addComponent(title)
//                                .addComponent(type)
//                                .addComponent(start))
//                        .addComponent(load)
//        );
    }

    public String getLoadPath() {
        return loadFile;
    }

    public String getSavePath() {
        return saveFile;
    }
}
