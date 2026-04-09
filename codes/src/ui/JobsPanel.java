package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppContext;
import model.Job;
import model.Role;
import model.User;
import service.ApplicationService;
import service.JobService;

/**
 * Shared jobs list panel. TAs can apply here, while MOs use it as a read-only overview.
 */
public class JobsPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final JPanel listPanel;

    public JobsPanel(AppContext context) {
        this.context = context;
        this.jobService = context.getJobService();
        this.applicationService = context.getApplicationService();
        this.listPanel = new JPanel();

        setLayout(new BorderLayout());
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        add(new JLabel("Jobs"), BorderLayout.NORTH);
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    @Override
    public void refreshView() {
        listPanel.removeAll();

        User currentUser = context.getCurrentUser();
        if (currentUser == null) {
            listPanel.add(new JLabel("Please log in first."));
        } else {
            List<Job> jobs = jobService.listVisibleJobs();
            if (jobs.isEmpty()) {
                listPanel.add(new JLabel("No open jobs available."));
            } else {
                for (Job job : jobs) {
                    listPanel.add(createJobRow(job, currentUser));
                }
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createJobRow(Job job, User currentUser) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.add(new JLabel(job.getJobName() + " | Posted by: " + job.getPostedBy()));

        if (currentUser.getRole() == Role.TA) {
            JButton applyButton = new JButton("Apply");
            applyButton.addActionListener(event -> applyToJob(job, currentUser.getName()));
            rowPanel.add(applyButton);
        }

        return rowPanel;
    }

    private void applyToJob(Job job, String applicantName) {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Apply for " + job.getJobName() + "?",
                "Confirm Application",
                JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            applicationService.applyToJob(job.getJobId(), applicantName);
            JOptionPane.showMessageDialog(this, "Application submitted.");
            refreshView();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}
