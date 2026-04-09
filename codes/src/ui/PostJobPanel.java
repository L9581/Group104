package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Cursor;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.AppContext;
import app.AppWindow;
import model.User;
import service.JobService;

public class PostJobPanel extends JPanel implements RefreshableView {
    private final AppContext context;
    private final AppWindow appWindow;
    private final JobService jobService;
    private final JTextField jobNameField;

    public PostJobPanel(AppContext context, AppWindow appWindow) {
        this.context = context;
        this.appWindow = appWindow;
        this.jobService = context.getJobService();
        this.jobNameField = new JTextField();
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250));

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(50, 60, 60, 60)
        ));

        JLabel titleLabel = new JLabel("Post a New Job");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(30, 30, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(titleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        Dimension fieldSize = new Dimension(320, 40); // 职位名字段更宽一点

        JLabel nameLabel = new JLabel("Job Title");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLabel.setForeground(new Color(80, 80, 80));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(nameLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        jobNameField.setMaximumSize(fieldSize);
        jobNameField.setPreferredSize(fieldSize);
        jobNameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(jobNameField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        JButton postButton = new JButton("Publish Job");
        postButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        postButton.setForeground(Color.WHITE);
        postButton.setBackground(new Color(40, 116, 240));
        postButton.setFocusPainted(false);
        postButton.setBorderPainted(false);
        postButton.setCursor(new Cursor(Cursor.HAND_CURSOR));
        postButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        postButton.setMaximumSize(fieldSize);
        postButton.setPreferredSize(fieldSize);
        postButton.addActionListener(event -> createJob());
        cardPanel.add(postButton);

        add(cardPanel, new GridBagConstraints());
    }

    private void createJob() {
        User currentUser = context.getCurrentUser();
        if (currentUser == null) return;
        try {
            jobService.createJob(jobNameField.getText().trim(), currentUser.getName());
            jobNameField.setText("");
            JOptionPane.showMessageDialog(this, "Job posted successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
            appWindow.showMyPostedJobs();
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void refreshView() { jobNameField.setText(""); }
}