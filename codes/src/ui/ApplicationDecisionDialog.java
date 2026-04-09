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
import model.Application;
import model.Job;
import service.ApplicationService;

/**
 * Allows an MO to review one application note and either accept, reject, or exit.
 */
public class ApplicationDecisionDialog extends JDialog {
    private final ApplicationService applicationService;
    private final Job job;
    private final Application application;
    private boolean changed;
    private boolean closeApplicantsDialog;

    public ApplicationDecisionDialog(
            AppWindow owner,
            ApplicationService applicationService,
            Job job,
            Application application) {
        super(owner, "Review Application", true);
        this.applicationService = applicationService;
        this.job = job;
        this.application = application;

        initializeDialog(owner);
    }

    public boolean wasChanged() {
        return changed;
    }

    public boolean shouldCloseApplicantsDialog() {
        return closeApplicantsDialog;
    }

    private void initializeDialog(AppWindow owner) {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 24, 20, 24));
        setSize(540, 430);
        setLocationRelativeTo(owner);

        JPanel contentPanel = new JPanel();
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));
        contentPanel.setOpaque(false);

        JLabel applicantLabel = new JLabel(application.getApplicantName());
        applicantLabel.setFont(new Font("SansSerif", Font.BOLD, 20));
        applicantLabel.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(applicantLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 6)));

        JLabel metaLabel = new JLabel("Job: " + job.getJobName() + "  |  Status: "
                + application.getStatus().toDisplayText());
        metaLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        metaLabel.setForeground(new Color(110, 110, 110));
        metaLabel.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(metaLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 16)));

        JTextArea noteArea = new JTextArea(application.getNote().isBlank() ? "-" : application.getNote());
        noteArea.setEditable(false);
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
        boolean pending = application.getStatus() == model.ApplicationStatus.PENDING;

        JButton rejectButton = new JButton("Reject");
        rejectButton.setEnabled(pending);
        rejectButton.addActionListener(event -> rejectApplication());
        buttonPanel.add(rejectButton);

        JButton acceptButton = new JButton("Accept");
        acceptButton.setEnabled(pending);
        acceptButton.addActionListener(event -> acceptApplication());
        buttonPanel.add(acceptButton);

        JButton exitButton = new JButton("Exit");
        exitButton.addActionListener(event -> dispose());
        buttonPanel.add(exitButton);

        contentPanel.add(buttonPanel);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void rejectApplication() {
        try {
            applicationService.rejectApplicant(job.getJobId(), application.getApplicantName());
            changed = true;
            JOptionPane.showMessageDialog(this, "Application rejected.");
            dispose();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void acceptApplication() {
        try {
            applicationService.hireApplicant(job.getJobId(), application.getApplicantName());
            changed = true;
            closeApplicantsDialog = true;
            JOptionPane.showMessageDialog(this, "Application accepted. This job is now closed.");
            dispose();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
