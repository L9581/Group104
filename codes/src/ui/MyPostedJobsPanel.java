package ui;

import java.awt.BorderLayout;
import java.awt.Color;
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
import model.User;
import service.ApplicationService;
import service.JobService;

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

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("My Posted Jobs");
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
            List<Job> jobs = jobService.listJobsPostedBy(currentUser.getName());
            if (jobs.isEmpty()) {
                JLabel emptyLabel = new JLabel("You haven't posted any jobs yet.");
                emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
                emptyLabel.setForeground(new Color(150, 150, 150));
                listPanel.add(emptyLabel);
            } else {
                for (Job job : jobs) {
                    listPanel.add(createJobCard(job));
                }
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createJobCard(Job job) {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, getPreferredSize().height); }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25)
        ));

        JPanel infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.Y_AXIS));
        infoPanel.setOpaque(false);

        JLabel jobLabel = new JLabel(job.getJobName());
        jobLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        jobLabel.setForeground(new Color(40, 40, 40));

        // 动态状态颜色
        Color statusColor = job.getStatus().name().equals("OPEN") ? new Color(40, 167, 69) : new Color(150, 150, 150);
        JLabel statusLabel = new JLabel("Status: " + job.getStatus().name());
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        statusLabel.setForeground(statusColor);

        infoPanel.add(jobLabel);
        infoPanel.add(Box.createRigidArea(new Dimension(0, 5)));
        infoPanel.add(statusLabel);
        card.add(infoPanel, BorderLayout.CENTER);

        // 管理按钮 (采用描边按钮风格)
        JButton manageButton = new JButton("View Applicants");
        manageButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        ButtonStyles.applyOutline(manageButton);
        manageButton.setPreferredSize(new Dimension(140, 36));
        manageButton.addActionListener(event -> openApplicantsDialog(job));

        JPanel btnWrapper = new JPanel(new GridBagLayout());
        btnWrapper.setOpaque(false);
        btnWrapper.add(manageButton);
        card.add(btnWrapper, BorderLayout.EAST);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private void openApplicantsDialog(Job job) {
        ApplicantsDialog dialog = new ApplicantsDialog(appWindow, applicationService, job);
        dialog.setVisible(true);
        refreshView();
    }
}
