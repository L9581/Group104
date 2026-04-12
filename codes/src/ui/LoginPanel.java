package ui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import app.AppContext;
import app.AppWindow;
import model.Role;
import model.User;
import service.AuthService;
import service.RuntimeDataResetService;

public class LoginPanel extends JPanel implements RefreshableView {
    private final AuthService authService;
    private final RuntimeDataResetService runtimeDataResetService;
    private final AppWindow appWindow;
    private final JTextField nameField;
    private final JComboBox<Role> roleSelector;

    public LoginPanel(AppContext context, AppWindow appWindow) {
        this.authService = context.getAuthService();
        this.runtimeDataResetService = context.getRuntimeDataResetService();
        this.appWindow = appWindow;
        this.nameField = new JTextField();
        this.roleSelector = new JComboBox<>(Role.values());
        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new GridBagLayout());
        setBackground(new Color(245, 247, 250)); // 全局淡灰蓝底色

        JPanel cardPanel = new JPanel();
        cardPanel.setLayout(new BoxLayout(cardPanel, BoxLayout.Y_AXIS));
        cardPanel.setBackground(Color.WHITE);
        cardPanel.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(50, 60, 60, 60)
        ));

        // 标题
        JLabel titleLabel = new JLabel("Welcome Back");
        titleLabel.setFont(new Font("SansSerif", Font.BOLD, 26));
        titleLabel.setForeground(new Color(30, 30, 30));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(titleLabel);

        JLabel subTitleLabel = new JLabel("Please enter your details to continue");
        subTitleLabel.setFont(new Font("SansSerif", Font.PLAIN, 13));
        subTitleLabel.setForeground(new Color(120, 120, 120));
        subTitleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(subTitleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        Dimension fieldSize = new Dimension(260, 40);

        // Name 输入
        JLabel nameLabel = new JLabel("Full Name");
        nameLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        nameLabel.setForeground(new Color(80, 80, 80));
        nameLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(nameLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        nameField.setMaximumSize(fieldSize);
        nameField.setPreferredSize(fieldSize);
        nameField.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(nameField);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 20)));

        // Role 选择
        JLabel roleLabel = new JLabel("Select Role");
        roleLabel.setFont(new Font("SansSerif", Font.BOLD, 13));
        roleLabel.setForeground(new Color(80, 80, 80));
        roleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        cardPanel.add(roleLabel);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 8)));

        roleSelector.setMaximumSize(fieldSize);
        roleSelector.setPreferredSize(fieldSize);
        roleSelector.setAlignmentX(Component.CENTER_ALIGNMENT);
        roleSelector.setBackground(Color.WHITE);
        cardPanel.add(roleSelector);
        cardPanel.add(Box.createRigidArea(new Dimension(0, 40)));

        // 主按钮
        JButton enterButton = new JButton("Enter System");
        enterButton.setFont(new Font("SansSerif", Font.BOLD, 15));
        ButtonStyles.applyPrimary(enterButton);
        enterButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        enterButton.setMaximumSize(fieldSize);
        enterButton.setPreferredSize(fieldSize);
        enterButton.addActionListener(event -> loginOrRegister());
        cardPanel.add(enterButton);

        cardPanel.add(Box.createRigidArea(new Dimension(0, 14)));

        JButton resetButton = new JButton("Reset Test Data");
        resetButton.setFont(new Font("SansSerif", Font.BOLD, 13));
        ButtonStyles.applyDangerOutline(resetButton);
        resetButton.setAlignmentX(Component.CENTER_ALIGNMENT);
        resetButton.setMaximumSize(fieldSize);
        resetButton.setPreferredSize(fieldSize);
        resetButton.addActionListener(event -> confirmAndResetRuntimeData());
        cardPanel.add(resetButton);

        add(cardPanel, new GridBagConstraints());
    }

    private void loginOrRegister() {
        try {
            User user = authService.loginOrRegister(nameField.getText().trim(), (Role) roleSelector.getSelectedItem());
            nameField.setText("");
            appWindow.onLoginSuccess(user);
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Input Error", JOptionPane.WARNING_MESSAGE);
        }
    }

    private void confirmAndResetRuntimeData() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "This will delete all runtime CSV data under codes/data for a fresh test run.\nContinue?",
                "Confirm Reset",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.WARNING_MESSAGE);
        if (choice != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            runtimeDataResetService.resetRuntimeData();
            refreshView();
            JOptionPane.showMessageDialog(
                    this,
                    "Runtime CSV data has been cleared. The next actions will recreate fresh files as needed.",
                    "Reset Complete",
                    JOptionPane.INFORMATION_MESSAGE);
        } catch (IllegalStateException exception) {
            JOptionPane.showMessageDialog(
                    this,
                    exception.getMessage(),
                    "Reset Failed",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void refreshView() {
        nameField.setText("");
        roleSelector.setSelectedIndex(0);
    }
}
