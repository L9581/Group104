package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppWindow;
import model.Application;
import model.Job;
import service.ApplicationService;

/**
 * Lists all applicants under one posted job and opens the decision dialog for each record.
 */
public class ApplicantsDialog extends JDialog {
    private final ApplicationService applicationService;
    private final AppWindow appWindow;
    private final Job job;
    private final JPanel listPanel;

    public ApplicantsDialog(AppWindow appWindow, ApplicationService applicationService, Job job) {
        super(appWindow, "Applicants for: " + job.getJobName(), true);
        this.applicationService = applicationService;
        this.appWindow = appWindow;
        this.job = job;
        this.listPanel = new JPanel();

        initializeDialog();
        refreshApplicants();
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        getContentPane().setBackground(new Color(245, 247, 250));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setSize(540, 450);
        setLocationRelativeTo(appWindow);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshApplicants() {
        listPanel.removeAll();
        List<Application> applications = applicationService.listApplicationsForJob(job.getJobId());

        if (applications.isEmpty()) {
            JLabel empty = new JLabel("No applicants yet.");
            empty.setFont(new Font("SansSerif", Font.ITALIC, 14));
            empty.setForeground(new Color(150, 150, 150));
            listPanel.add(empty);
        } else {
            for (Application application : applications) {
                listPanel.add(createApplicantCard(application));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createApplicantCard(Application application) {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)));

        JLabel nameLabel = new JLabel(
                application.getApplicantName() + " • " + application.getStatus().toDisplayText());
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 15));
        nameLabel.setForeground(new Color(40, 40, 40));
        card.add(nameLabel, BorderLayout.CENTER);

        JButton reviewButton = new JButton("Review");
        reviewButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        reviewButton.setPreferredSize(new Dimension(120, 32));
        reviewButton.setFocusPainted(false);
        reviewButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        reviewButton.addActionListener(event -> openDecisionDialog(application));

        JPanel buttonWrapper = new JPanel(new GridBagLayout());
        buttonWrapper.setOpaque(false);
        buttonWrapper.add(reviewButton);
        card.add(buttonWrapper, BorderLayout.EAST);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private void openDecisionDialog(Application application) {
        ApplicationDecisionDialog dialog =
                new ApplicationDecisionDialog(appWindow, applicationService, job, application);
        dialog.setVisible(true);

        if (dialog.wasChanged()) {
            if (dialog.shouldCloseApplicantsDialog()) {
                appWindow.showMyPostedJobs();
                dispose();
            } else {
                refreshApplicants();
            }
        }
    }
}

