package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppWindow;
import model.Application;
import model.ApplicationStatus;
import model.Job;
import service.ApplicationService;

/**
 * Modal dialog for reviewing applicants under one posted job and hiring one of them.
 */
public class ApplicantsDialog extends JDialog {
    private final ApplicationService applicationService;
    private final AppWindow appWindow;
    private final Job job;
    private final JPanel listPanel;

    public ApplicantsDialog(AppWindow appWindow, ApplicationService applicationService, Job job) {
        super(appWindow, "Applicants - " + job.getJobName(), true);
        this.applicationService = applicationService;
        this.appWindow = appWindow;
        this.job = job;
        this.listPanel = new JPanel();

        initializeDialog();
        refreshApplicants();
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        setSize(480, 360);
        setLocationRelativeTo(appWindow);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    private void refreshApplicants() {
        listPanel.removeAll();

        List<Application> applications = applicationService.listApplicationsForJob(job.getJobId());
        if (applications.isEmpty()) {
            listPanel.add(new JLabel("No applicants yet."));
        } else {
            for (Application application : applications) {
                listPanel.add(createApplicantRow(application));
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createApplicantRow(Application application) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        String labelText = application.getApplicantName();
        if (application.getStatus() != ApplicationStatus.PENDING) {
            labelText += " (" + application.getStatus().name() + ")";
        }
        rowPanel.add(new JLabel(labelText));

        JButton hireButton = new JButton("Hire");
        hireButton.setEnabled(application.getStatus() == ApplicationStatus.PENDING);
        hireButton.addActionListener(event -> hireApplicant(application));
        rowPanel.add(hireButton);
        return rowPanel;
    }

    private void hireApplicant(Application application) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Hire " + application.getApplicantName() + " for this job?",
                "Confirm Hire",
                JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            applicationService.hireApplicant(job.getJobId(), application.getApplicantName());
            JOptionPane.showMessageDialog(this, "Applicant hired. The job is now closed.");
            appWindow.showMyPostedJobs();
            dispose();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
