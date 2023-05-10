package view;

import javax.swing.*;
import java.awt.*;

public class GUI {

    private final JFrame frame = new JFrame("Assignment#02");
    private final DefaultListModel<String> countingListModel = new DefaultListModel<>();
    private final DefaultListModel<String> longestFilesModel = new DefaultListModel<>();
    private final ListView counting = new ListView(countingListModel);
    private final ListView leaderboard = new ListView(longestFilesModel);
    private final NumericBox totalFilesBox = new NumericBox("Files counted:");
    private final NumericBox durationBox = new NumericBox("Duration (ms):");
    private final JButton startButton = new JButton("START");
    private final JButton stopButton = new JButton("STOP");

    public GUI() {
        frame.setSize(800, 600);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        Container panel = frame.getContentPane();

        Box inputsPanel = Box.createHorizontalBox();
        NumericInputBox maxLinesBox = new NumericInputBox("Max. lines", 1000);
        NumericInputBox intervalsBox = new NumericInputBox("N. Intervals", 5);
        NumericInputBox longestFilesBox = new NumericInputBox("# of longestFiles", 5);

        inputsPanel.add(maxLinesBox);
        inputsPanel.add(Box.createGlue());
        inputsPanel.add(intervalsBox);
        inputsPanel.add(Box.createGlue());
        inputsPanel.add(longestFilesBox);
        inputsPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Box mainPanel = Box.createHorizontalBox();
        mainPanel.add(counting);
        mainPanel.add(Box.createGlue());
        mainPanel.add(leaderboard);
        mainPanel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));

        Box controlsPanel = Box.createHorizontalBox();
        NumericInputBox workersInput = new NumericInputBox("# Workers", 1);
        controlsPanel.add(totalFilesBox);
        controlsPanel.add(Box.createGlue());
        controlsPanel.add(durationBox);
        controlsPanel.add(Box.createGlue());
        controlsPanel.add(startButton);
        controlsPanel.add(Box.createRigidArea(new Dimension(20, 0)));
        controlsPanel.add(stopButton);
        controlsPanel.add(Box.createGlue());
        controlsPanel.add(workersInput);
        stopButton.setEnabled(false);
        startButton.addActionListener(e -> {
            totalFilesBox.reset();
            durationBox.reset();
            int maxLines = (int) maxLinesBox.spinner.getValue();
            int intervals = (int) intervalsBox.spinner.getValue();
            int longestFiles = (int) longestFilesBox.spinner.getValue();
            int numberOfWorkers = (int) workersInput.spinner.getValue();
            startButton.setEnabled(false);
            stopButton.setEnabled(true);
        });
        stopButton.addActionListener(e -> {
            // TODO
        });
        controlsPanel.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        controlsPanel.setAlignmentX(Component.CENTER_ALIGNMENT);

        panel.add(inputsPanel, BorderLayout.NORTH);
        panel.add(controlsPanel, BorderLayout.PAGE_END);
        panel.add(mainPanel, BorderLayout.CENTER);
        frame.setVisible(true);
    }

    private static void setSizeForText(JComponent component) {
        component.setPreferredSize(new Dimension(100, 40));
        component.setMaximumSize(component.getPreferredSize());
        component.setAlignmentX(Component.RIGHT_ALIGNMENT);
    }

    private static class NumericInputBox extends Box {

        private final JSpinner spinner;

        public NumericInputBox(String prompt, int initialValue) {
            super(BoxLayout.Y_AXIS);
            spinner = new JSpinner(new SpinnerNumberModel(initialValue, 0, Integer.MAX_VALUE, 1));
            add(new JLabel(prompt) {{
                setAlignmentX(Component.RIGHT_ALIGNMENT);
            }});
            add(spinner);
            setSizeForText(spinner);
        }
    }

    private static class NumericBox extends Box {

        private final JTextField textField;

        public NumericBox(String label) {
            super(BoxLayout.Y_AXIS);
            textField = new JTextField();
            add(new JLabel(label) {{
                setAlignmentX(Component.RIGHT_ALIGNMENT);
            }});
            add(textField);
            setSizeForText(textField);
            textField.setEditable(false);
        }

        public void reset() {
            update(0);
        }

        public void update(int value) {
            textField.setText(String.valueOf(value));
        }
    }

    private static class ListView extends JScrollPane {

        public ListView(ListModel<String> listModel) {
            super(new JList<>(listModel) {{
                setMinimumSize(new Dimension(300, 400));
                setCellRenderer(new DefaultListCellRenderer());
            }});
        }
    }

}