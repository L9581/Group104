package ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;

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

/**
 * Minimal login/register panel using only name and role.
 */
public class LoginPanel extends JPanel implements RefreshableView {
    private final AuthService authService;
    private final AppWindow appWindow;
    private final JTextField nameField;
    private final JComboBox<Role> roleSelector;

    public LoginPanel(AppContext context, AppWindow appWindow) {
        this.authService = context.getAuthService();
        this.appWindow = appWindow;
        this.nameField = new JTextField(18);
        this.roleSelector = new JComboBox<>(Role.values());

        initializeLayout();
    }

    private void initializeLayout() {
        setLayout(new BorderLayout());

        JLabel titleLabel = new JLabel("Login / Register");
        add(titleLabel, BorderLayout.NORTH);

        JPanel formPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));
        formPanel.add(new JLabel("Name:"));
        formPanel.add(nameField);
        formPanel.add(new JLabel("Role:"));
        formPanel.add(roleSelector);

        JButton enterButton = new JButton("Enter");
        enterButton.addActionListener(event -> loginOrRegister());
        formPanel.add(enterButton);

        add(formPanel, BorderLayout.CENTER);
    }

    private void loginOrRegister() {
        try {
            User user = authService.loginOrRegister(nameField.getText(), (Role) roleSelector.getSelectedItem());
            nameField.setText("");
            appWindow.onLoginSuccess(user);
        } catch (IllegalArgumentException exception) {
            JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void refreshView() {
        nameField.setText("");
    }
}
