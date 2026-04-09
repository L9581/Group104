package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.AppWindow;
import model.Job;
import service.ApplicationService;

/**
 * Collects the short application text required by the V2 workflow.
 */
public class ApplicationFormDialog extends JDialog {
    private final ApplicationService applicationService;
    private final Job job;
    private final String applicantName;
    private final JTextArea noteArea;
    private boolean submitted;

    public ApplicationFormDialog(
            AppWindow owner,
            ApplicationService applicationService,
            Job job,
            String applicantName) {
        super(owner, "Apply for " + job.getJobName(), true);
        this.applicationService = applicationService;
        this.job = job;
        this.applicantName = applicantName;
        this.noteArea = new JTextArea(8, 28);

        initializeDialog(owner);
    }

    public boolean wasSubmitted() {
        return submitted;
    }

    private void initializeDialog(AppWindow owner) {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setSize(520, 420);
        setLocationRelativeTo(owner);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel titleLabel = new JLabel(job.getJobName());
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(titleLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        JLabel helpLabel = new JLabel("Enter a short application note before you apply.");
        helpLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        helpLabel.setForeground(new Color(110, 110, 110));
        helpLabel.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(helpLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));

        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setFont(new Font("SansSerif", Font.PLAIN, 14));

        JScrollPane scrollPane = new JScrollPane(noteArea);
        scrollPane.setAlignmentX(LEFT_ALIGNMENT);
        scrollPane.setBorder(BorderFactory.createLineBorder(new Color(210, 214, 220)));
        contentPanel.add(scrollPane);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));

        JPanel buttonPanel = new JPanel();
        buttonPanel.setOpaque(false);
        buttonPanel.setAlignmentX(LEFT_ALIGNMENT);

        JButton applyButton = new JButton("Apply");
        applyButton.addActionListener(event -> submitApplication());
        buttonPanel.add(applyButton);

        JButton cancelButton = new JButton("Cancel");
        cancelButton.addActionListener(event -> dispose());
        buttonPanel.add(cancelButton);

        contentPanel.add(buttonPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void submitApplication() {
        try {
            applicationService.applyToJob(job.getJobId(), applicantName, noteArea.getText());
            submitted = true;
            JOptionPane.showMessageDialog(this, "Application submitted successfully.");
            dispose();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}

