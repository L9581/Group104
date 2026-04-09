package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagLayout;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppContext;
import app.AppWindow;
import model.Job;
import model.Role;
import model.User;
import service.ApplicationService;
import service.JobService;

/**
 * Shared jobs view. TAs can apply through a form, while MOs see all jobs as a read-only list.
 */
public class JobsPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final AppWindow appWindow;
    private final JobService jobService;
    private final ApplicationService applicationService;
    private final JPanel listPanel;

    public JobsPanel(AppContext context, AppWindow appWindow) {
        this.context = context;
        this.appWindow = appWindow;
        this.jobService = context.getJobService();
        this.applicationService = context.getApplicationService();
        this.listPanel = new JPanel();

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Available Opportunities");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false);

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null);
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void refreshView() {
        listPanel.removeAll();
        User currentUser = context.getCurrentUser();

        if (currentUser != null) {
            List<Job> jobs = currentUser.getRole() == Role.MO
                    ? jobService.listAllJobs()
                    : jobService.listVisibleJobs();
            if (jobs.isEmpty()) {
                JLabel emptyLabel = new JLabel("No jobs available right now.");
                emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
                emptyLabel.setForeground(new Color(150, 150, 150));
                listPanel.add(emptyLabel);
            } else {
                for (Job job : jobs) {
                    listPanel.add(createJobCard(job, currentUser));
                }
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createJobCard(Job job, User currentUser) {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel jobLabel = new JLabel(job.getJobName());
        jobLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        jobLabel.setForeground(new Color(40, 40, 40));

        JLabel authorLabel = new JLabel("Posted by: " + job.getPostedBy());
        authorLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        authorLabel.setForeground(new Color(120, 120, 120));

        infoPanel.add(jobLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(authorLabel);

        if (currentUser.getRole() == Role.MO) {
            infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
            JLabel statusLabel = new JLabel("Status: " + job.getStatus().name());
            statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
            statusLabel.setForeground(job.getStatus().name().equals("OPEN")
                    ? new Color(40, 167, 69)
                    : new Color(150, 150, 150));
            infoPanel.add(statusLabel);
        }

        card.add(infoPanel, BorderLayout.CENTER);

        if (currentUser.getRole() == Role.TA) {
            JButton applyButton = new JButton("Apply Now");
            applyButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            applyButton.setForeground(Color.WHITE);
            applyButton.setBackground(new Color(40, 116, 240));
            applyButton.setFocusPainted(false);
            applyButton.setBorderPainted(false);
            applyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            applyButton.setPreferredSize(new Dimension(120, 36));
            applyButton.addActionListener(event -> openApplicationForm(job, currentUser.getName()));

            JPanel buttonWrapper = new JPanel(new GridBagLayout());
            buttonWrapper.setOpaque(false);
            buttonWrapper.add(applyButton);
            card.add(buttonWrapper, BorderLayout.EAST);
        }

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private void openApplicationForm(Job job, String applicantName) {
        ApplicationFormDialog dialog = new ApplicationFormDialog(appWindow, applicationService, job, applicantName);
        dialog.setVisible(true);
        if (dialog.wasSubmitted()) {
            refreshView();
        }
    }
}

