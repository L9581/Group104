package app;

import java.awt.CardLayout;
import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class AppWindow extends JFrame {
    public static final String SCREEN_LOGIN = "login";

    private final CardLayout cardLayout;
    private final JPanel screenContainer;

    public AppWindow() {
        super("TA Recruitment System");

        this.cardLayout = new CardLayout();
        this.screenContainer = new JPanel(cardLayout);

        initializeWindow();
        initializeScreens();
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setContentPane(screenContainer);
        setMinimumSize(new Dimension(900, 600));
        setSize(960, 640);
        setLocationRelativeTo(null);
    }

    private void initializeScreens() {
        registerScreen(SCREEN_LOGIN, new LoginPanel(this));
        showScreen(SCREEN_LOGIN);
    }

    public void registerScreen(String screenName, JPanel panel) {
        screenContainer.add(panel, screenName);
    }

    public void showScreen(String screenName) {
        cardLayout.show(screenContainer, screenName);
    }
}
