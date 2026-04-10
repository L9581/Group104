package ui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.util.List;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import app.AppContext;
import model.Application;
import model.Job;
import model.User;
import service.ApplicationService;
import service.JobService;

/**
 * Shows the current TA's submitted applications and their current results.
 */
public class MyApplicationsPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final ApplicationService applicationService;
    private final JobService jobService;
    private final JPanel listPanel;

    public MyApplicationsPanel(AppContext context) {
        this.context = context;
        this.applicationService = context.getApplicationService();
        this.jobService = context.getJobService();
        this.listPanel = new JPanel();

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60));

        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("My Applications");
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
            List<Application> applications = applicationService.listApplicationsForApplicant(currentUser.getName());
            if (applications.isEmpty()) {
                JLabel emptyLabel = new JLabel("You have not submitted any applications yet.");
                emptyLabel.setFont(new Font("SansSerif", Font.ITALIC, 16));
                emptyLabel.setForeground(new Color(150, 150, 150));
                listPanel.add(emptyLabel);
            } else {
                for (Application application : applications) {
                    listPanel.add(createApplicationCard(application));
                }
            }
        }

        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createApplicationCard(Application application) {
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override
            public Dimension getMaximumSize() {
                return new Dimension(Integer.MAX_VALUE, getPreferredSize().height);
            }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0));

        JPanel card = new JPanel(new BorderLayout(18, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(20, 24, 20, 24)));

        JPanel contentPanel = new JPanel();
        contentPanel.setOpaque(false);
        contentPanel.setLayout(new BoxLayout(contentPanel, BoxLayout.Y_AXIS));

        Job job = jobService.findJobById(application.getJobId()).orElse(null);
        String jobName = job == null ? "Unknown Job" : job.getJobName();
        String postedBy = job == null ? "Unknown MO" : job.getPostedBy();

        JLabel jobLabel = new JLabel(jobName);
        jobLabel.setFont(new Font("SansSerif", Font.BOLD, 18));
        jobLabel.setForeground(new Color(40, 40, 40));
        jobLabel.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(jobLabel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 4)));

        // --- 状态颜色优化逻辑 ---
        JPanel metaPanel = new JPanel();
        metaPanel.setLayout(new BoxLayout(metaPanel, BoxLayout.X_AXIS));
        metaPanel.setOpaque(false);
        metaPanel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel postedByLabel = new JLabel("Posted by: " + postedBy + "  |  Status: ");
        postedByLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        postedByLabel.setForeground(new Color(110, 110, 110));
        metaPanel.add(postedByLabel);

        JLabel statusLabel = new JLabel(application.getStatus().toDisplayText());
        statusLabel.setFont(new Font("SansSerif", Font.BOLD, 13));

        // 根据不同状态设置不同的颜色
        switch (application.getStatus()) {
            case HIRED:
                statusLabel.setForeground(new Color(40, 167, 69)); // 绿色 (Accepted)
                break;
            case REJECTED:
                statusLabel.setForeground(new Color(220, 53, 69)); // 红色 (Rejected)
                break;
            case PENDING:
            default:
                statusLabel.setForeground(new Color(253, 126, 20)); // 橙色 (Pending)
                break;
        }
        metaPanel.add(statusLabel);

        contentPanel.add(metaPanel);
        contentPanel.add(Box.createRigidArea(new Dimension(0, 12)));
        // -------------------------

        JTextArea noteArea = new JTextArea(application.getNote().isBlank() ? "-" : application.getNote());
        noteArea.setEditable(false);
        noteArea.setLineWrap(true);
        noteArea.setWrapStyleWord(true);
        noteArea.setFont(new Font("SansSerif", Font.PLAIN, 13));
        noteArea.setBackground(new Color(250, 251, 252));
        noteArea.setBorder(BorderFactory.createLineBorder(new Color(230, 233, 237)));
        noteArea.setAlignmentX(LEFT_ALIGNMENT);
        contentPanel.add(noteArea);

        card.add(contentPanel, BorderLayout.CENTER);
        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }
}