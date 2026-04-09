package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.AppContext;
import app.AppWindow;
import model.User;
import service.JobService;

/**
 * Minimal job posting panel for MOs.
 */
public class PostJobPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final AppWindow appWindow;
    private final JobService jobService;
    private final JTextField jobNameField;

    public PostJobPanel(AppContext context, AppWindow appWindow) {
        this.context = context;
        this.appWindow = appWindow;
        this.jobService = context.getJobService();
        this.jobNameField = new JTextField(24);

        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        add(new JLabel("Post New Job"), BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Job Name:"));
        formPanel.add(jobNameField);

        JButton postButton = new JButton("Post");
        postButton.addActionListener(event -> createJob());
        formPanel.add(postButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void createJob() {
        User currentUser = context.getCurrentUser();
        if (currentUser == null) {
            JOptionPane.showMessageDialog(this, "Please log in first.", "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            jobService.createJob(jobNameField.getText(), currentUser.getName());
            jobNameField.setText("");
            JOptionPane.showMessageDialog(this, "Job posted successfully.");
            appWindow.showMyPostedJobs();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void refreshView() {
        jobNameField.setText("");
    }
}
