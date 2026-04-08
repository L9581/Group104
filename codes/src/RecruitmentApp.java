import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;

public class RecruitmentApp extends JFrame {

    private CardLayout cardLayout;
    private JPanel mainContentPanel;

    public RecruitmentApp() {
        setTitle("TA Recruitment System - BUPT International School");
        setSize(800, 600);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 1. 初始化顶部导航栏 (对应 HTML 的 <nav>)
        initNavigationBar();

        // 2. 初始化主体内容区域 (对应 HTML 的 data-view 切换)
        cardLayout = new CardLayout();
        mainContentPanel = new JPanel(cardLayout);
        
        // 添加各个子页面
        mainContentPanel.add(createPlaceholderPanel("Dashboard View"), "dashboard");
        mainContentPanel.add(createPostJobPanel(), "jobs"); // 这里接入发帖表单
        mainContentPanel.add(createPlaceholderPanel("Applications View"), "applications");
        mainContentPanel.add(createPlaceholderPanel("Profile View"), "profile");
        mainContentPanel.add(createPlaceholderPanel("Admin View"), "admin");

        add(mainContentPanel, BorderLayout.CENTER);
        
        // 默认显示 Dashboard
        cardLayout.show(mainContentPanel, "dashboard");
    }

    private void initNavigationBar() {
        JPanel navPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        navPanel.setBackground(new Color(40, 44, 52)); // 模拟深色导航栏

        // 导航按钮
        String[] views = {"Dashboard", "Jobs", "Applications", "Profile", "Admin"};
        for (String view : views) {
            JButton btn = new JButton(view);
            btn.addActionListener(e -> cardLayout.show(mainContentPanel, view.toLowerCase()));
            navPanel.add(btn);
        }

        // 角色选择器 (对应 HTML 的 <select id="userRole">)
        navPanel.add(Box.createHorizontalStrut(50)); // 添加间距
        navPanel.add(new JLabel("<html><font color='white'>Role:</font></html>"));
        String[] roles = {"Teaching Assistant", "Module Organiser", "System Admin"};
        JComboBox<String> roleSelector = new JComboBox<>(roles);
        navPanel.add(roleSelector);

        add(navPanel, BorderLayout.NORTH);
    }

    // 核心表单：还原 HTML 中的 Post Job 界面
    private JPanel createPostJobPanel() {
        JPanel panel = new JPanel(new GridBagLayout());
        panel.setBorder(BorderFactory.createEmptyBorder(20, 20, 20, 20));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.insets = new Insets(5, 5, 5, 5);

        // 标题
        gbc.gridx = 0; gbc.gridy = 0; gbc.gridwidth = 2;
        JLabel titleLabel = new JLabel("Post New TA Job");
        titleLabel.setFont(new Font("Arial", Font.BOLD, 18));
        panel.add(titleLabel, gbc);

        // 表单字段复刻
        gbc.gridwidth = 1;
        
        // Job Title
        gbc.gridy = 1; gbc.gridx = 0; panel.add(new JLabel("Job Title:"), gbc);
        gbc.gridx = 1; JTextField titleField = new JTextField(20); panel.add(titleField, gbc);

        // Department
        gbc.gridy = 2; gbc.gridx = 0; panel.add(new JLabel("Department:"), gbc);
        gbc.gridx = 1; 
        String[] depts = {"Computer Science", "Mathematics", "Physics"};
        JComboBox<String> deptBox = new JComboBox<>(depts); 
        panel.add(deptBox, gbc);

        // Description
        gbc.gridy = 3; gbc.gridx = 0; panel.add(new JLabel("Description:"), gbc);
        gbc.gridx = 1; 
        JTextArea descArea = new JTextArea(4, 20);
        descArea.setLineWrap(true);
        panel.add(new JScrollPane(descArea), gbc);

        // Required Skills
        gbc.gridy = 4; gbc.gridx = 0; panel.add(new JLabel("Required Skills:"), gbc);
        gbc.gridx = 1; JTextField skillsField = new JTextField(20); panel.add(skillsField, gbc);

        // Hours Required
        gbc.gridy = 5; gbc.gridx = 0; panel.add(new JLabel("Hours Required:"), gbc);
        gbc.gridx = 1; JTextField hoursField = new JTextField(20); panel.add(hoursField, gbc);

        // Hourly Rate (£)
        gbc.gridy = 6; gbc.gridx = 0; panel.add(new JLabel("Hourly Rate (£):"), gbc);
        gbc.gridx = 1; JTextField rateField = new JTextField(20); panel.add(rateField, gbc);

        // 提交按钮
        gbc.gridy = 7; gbc.gridx = 1;
        JButton submitBtn = new JButton("Post Job");
        panel.add(submitBtn, gbc);

        // 按钮事件处理：获取数据并保存
        submitBtn.addActionListener((ActionEvent e) -> {
            boolean success = DataManager.saveJob(
                titleField.getText(),
                (String) deptBox.getSelectedItem(),
                descArea.getText(),
                skillsField.getText(),
                hoursField.getText(),
                rateField.getText()
            );

            if (success) {
                JOptionPane.showMessageDialog(this, "Job Posted Successfully!");
                // 清空表单
                titleField.setText(""); descArea.setText("");
                skillsField.setText(""); hoursField.setText(""); rateField.setText("");
            } else {
                JOptionPane.showMessageDialog(this, "Failed to post job.", "Error", JOptionPane.ERROR_MESSAGE);
            }
        });

        return panel;
    }

    // 占位面板生成器，用于尚未实现的模块
    private JPanel createPlaceholderPanel(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        JLabel label = new JLabel(text, SwingConstants.CENTER);
        label.setFont(new Font("Arial", Font.BOLD, 24));
        panel.add(label, BorderLayout.CENTER);
        return panel;
    }

    // 程序入口
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            new RecruitmentApp().setVisible(true);
        });
    }
}