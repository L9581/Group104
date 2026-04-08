package app;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;

import javax.swing.BorderFactory;
import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

public class LoginPanel extends JPanel {
    private final AppWindow appWindow;
    private final JTextField usernameField;
    private final JPasswordField passwordField;
    private final JComboBox<UserRole> roleComboBox;
    private final JLabel statusLabel;

    public LoginPanel(AppWindow appWindow) {
        this.appWindow = appWindow;
        this.usernameField = new JTextField(18);
        this.passwordField = new JPasswordField(18);
        this.roleComboBox = new JComboBox<>(UserRole.values());
        this.statusLabel = new JLabel(" ");

        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250));

        JPanel contentPanel = new JPanel(new GridBagLayout());
        contentPanel.setOpaque(false);

        JPanel formCard = buildFormCard();
        contentPanel.add(formCard);

        add(contentPanel, BorderLayout.CENTER);
    }

    private JPanel buildFormCard() {
        JPanel card = new JPanel();
        card.setLayout(new BoxLayout(card, BoxLayout.Y_AXIS));
        card.setPreferredSize(new Dimension(360, 320));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(220, 224, 230)),
                BorderFactory.createEmptyBorder(24, 24, 24, 24)));

        JLabel titleLabel = new JLabel("Login");
        titleLabel.setFont(titleLabel.getFont().deriveFont(Font.BOLD, 24f));
        titleLabel.setAlignmentX(LEFT_ALIGNMENT);

        JLabel subtitleLabel = new JLabel("Teaching Assistant Recruitment System");
        subtitleLabel.setForeground(new Color(90, 99, 110));
        subtitleLabel.setAlignmentX(LEFT_ALIGNMENT);

        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setOpaque(false);
        formPanel.setAlignmentX(LEFT_ALIGNMENT);

        GridBagConstraints constraints = new GridBagConstraints();
        constraints.gridx = 0;
        constraints.gridy = 0;
        constraints.anchor = GridBagConstraints.WEST;
        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(0, 0, 6, 0);
        constraints.weightx = 1.0;

        addField(formPanel, constraints, "Username", usernameField);
        addField(formPanel, constraints, "Password", passwordField);
        addField(formPanel, constraints, "Role", roleComboBox);

        JButton loginButton = new JButton("Login");
        loginButton.setAlignmentX(LEFT_ALIGNMENT);
        loginButton.addActionListener(event -> handleLogin());

        statusLabel.setAlignmentX(LEFT_ALIGNMENT);
        statusLabel.setForeground(new Color(52, 103, 81));

        card.add(titleLabel);
        card.add(Box.createVerticalStrut(6));
        card.add(subtitleLabel);
        card.add(Box.createVerticalStrut(20));
        card.add(formPanel);
        card.add(Box.createVerticalStrut(16));
        card.add(loginButton);
        card.add(Box.createVerticalStrut(10));
        card.add(statusLabel);

        return card;
    }

    private void addField(JPanel formPanel, GridBagConstraints constraints, String labelText, java.awt.Component field) {
        JLabel label = new JLabel(labelText);

        formPanel.add(label, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 14, 0);
        formPanel.add(field, constraints);

        constraints.gridy++;
        constraints.insets = new Insets(0, 0, 6, 0);
    }

    private void handleLogin() {
        String username = usernameField.getText().trim();
        String password = new String(passwordField.getPassword()).trim();
        UserRole role = (UserRole) roleComboBox.getSelectedItem();

        if (username.isEmpty() || password.isEmpty() || role == null) {
            statusLabel.setForeground(new Color(160, 50, 50));
            statusLabel.setText("Please complete username, password and role.");
            return;
        }

        statusLabel.setForeground(new Color(52, 103, 81));
        statusLabel.setText("Login UI scaffold ready.");

        JOptionPane.showMessageDialog(
                appWindow,
                "Captured login input for " + role + ": " + username,
                "Scaffold Ready",
                JOptionPane.INFORMATION_MESSAGE);
    }
}
