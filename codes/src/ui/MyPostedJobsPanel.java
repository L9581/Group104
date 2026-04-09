package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.List;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppContext;
import app.AppWindow;
import model.Job;
import model.User;
import service.ApplicationService;
import service.JobService;

/**
 * Lists the current MO's jobs and opens the applicant management dialog.
 */
public class MyPostedJobsPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final AppWindow appWindow;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final JPanel listPanel;

    public MyPostedJobsPanel(AppContext context, AppWindow appWindow) {
        this.context = context;
        this.appWindow = appWindow;
        this.jobService = context.getJobService();
        this.applicationService = context.getApplicationService();
        this.listPanel = new JPanel();

        setLayout(new BorderLayout());
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        add(new JLabel("My Posted Jobs"), BorderLayout.NORTH);
        add(new JScrollPane(listPanel), BorderLayout.CENTER);
    }

    @Override
    public void refreshView() {
        listPanel.removeAll();

        User currentUser = context.getCurrentUser();
        if (currentUser == null) {
            listPanel.add(new JLabel("Please log in first."));
        } else {
            List<Job> jobs = jobService.listJobsPostedBy(currentUser.getName());
            if (jobs.isEmpty()) {
                listPanel.add(new JLabel("You have not posted any jobs."));
            } else {
                for (Job job : jobs) {
                    listPanel.add(createJobRow(job));
                }
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createJobRow(Job job) {
        JPanel rowPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        rowPanel.add(new JLabel(job.getJobName() + " | Status: " + job.getStatus().name()));

        JButton optionButton = new JButton("Option");
        optionButton.addActionListener(event -> openApplicantsDialog(job));
        rowPanel.add(optionButton);
        return rowPanel;
    }

    private void openApplicantsDialog(Job job) {
        ApplicantsDialog dialog = new ApplicantsDialog(appWindow, applicationService, job);
        dialog.setVisible(true);
        refreshView();
    }
}
