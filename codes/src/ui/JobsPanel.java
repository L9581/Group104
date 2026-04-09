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
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.Box;

import app.AppContext;
import model.Job;
import model.Role;
import model.User;
import service.ApplicationService;
import service.JobService;

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

        setLayout(new BorderLayout(0, 20));
        setBackground(new Color(245, 247, 250));
        setBorder(BorderFactory.createEmptyBorder(30, 60, 30, 60)); // 边缘大留白

        // 头部标题区
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setOpaque(false);
        JLabel titleLabel = new JLabel("Available Opportunities");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 28));
        titleLabel.setForeground(new Color(30, 30, 30));
        headerPanel.add(titleLabel, BorderLayout.WEST);
        add(headerPanel, BorderLayout.NORTH);

        // 列表区
        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false); // 透明背景，露出底层灰蓝

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null); // 去除滚动条自带的丑陋边框
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16); // 加快滚动速度
        add(scrollPane, BorderLayout.CENTER);
    }

    @Override
    public void refreshView() {
        listPanel.removeAll();
        User currentUser = context.getCurrentUser();

        if (currentUser != null) {
            List<Job> jobs = jobService.listVisibleJobs();
            if (jobs.isEmpty()) {
                JLabel emptyLabel = new JLabel("No open jobs available right now.");
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
        // Wrapper 用于制造卡片之间的垂直间距 (Margin)
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, getPreferredSize().height); }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 15, 0)); // 底部间距 15px

        // 真实的白色卡片 (Card)
        JPanel card = new JPanel(new BorderLayout(20, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(20, 25, 20, 25) // 内部留白 Padding
        ));

        // 左侧文字信息
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

        card.add(infoPanel, BorderLayout.CENTER);

        // 右侧操作按钮
        if (currentUser.getRole() == Role.TA) {
            JButton applyButton = new JButton("Apply Now");
            applyButton.setFont(new Font("SansSerif", Font.BOLD, 13));
            applyButton.setForeground(Color.WHITE);
            applyButton.setBackground(new Color(40, 116, 240));
            applyButton.setFocusPainted(false);
            applyButton.setBorderPainted(false);
            applyButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
            applyButton.setPreferredSize(new Dimension(120, 36));
            applyButton.addActionListener(event -> applyToJob(job, currentUser.getName()));

            // 使用包裹面板让按钮垂直居中
            JPanel btnWrapper = new JPanel(new GridBagLayout());
            btnWrapper.setOpaque(false);
            btnWrapper.add(applyButton);
            card.add(btnWrapper, BorderLayout.EAST);
        }

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private void applyToJob(Job job, String applicantName) {
        int choice = JOptionPane.showConfirmDialog(this, "Are you sure you want to apply for " + job.getJobName() + "?", "Confirm Application", JOptionPane.YES_NO_OPTION);
        if (choice != JOptionPane.YES_OPTION) return;
        try {
            applicationService.applyToJob(job.getJobId(), applicantName);
            JOptionPane.showMessageDialog(this, "Application submitted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            refreshView();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }
}