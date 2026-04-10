package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.Color;
import java.awt.Cursor;
import java.awt.FlowLayout;
import java.awt.Font;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Role;
import model.User;
import ui.JobsPanel;
import ui.LoginPanel;
import ui.MyApplicationsPanel;
import ui.MyPostedJobsPanel;
import ui.PostJobPanel;
import ui.RefreshableView;

/**
 * Main application window. It owns role-based navigation and view switching.
 */

public class AppWindow extends JFrame {
    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_JOBS = "jobs";
    private static final String VIEW_MY_APPLICATIONS = "myApplications";
    private static final String VIEW_POST_JOB = "postJob";
    private static final String VIEW_MY_POSTED_JOBS = "myPostedJobs";

    private final AppContext context;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final JPanel navigationPanel;

    private final LoginPanel loginPanel;
    private final JobsPanel jobsPanel;
    private final MyApplicationsPanel myApplicationsPanel;
    private final PostJobPanel postJobPanel;
    private final MyPostedJobsPanel myPostedJobsPanel;

    public AppWindow(AppContext context) {
        super("BUPT TA Recruitment System");
        this.context = context;
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);
        this.navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        this.navigationPanel.setBackground(Color.WHITE);

        this.loginPanel = new LoginPanel(context, this);
        this.jobsPanel = new JobsPanel(context, this);
        this.myApplicationsPanel = new MyApplicationsPanel(context);
        this.postJobPanel = new PostJobPanel(context, this);
        this.myPostedJobsPanel = new MyPostedJobsPanel(context, this);

        initializeWindow();
        initializeViews();
        updateNavigation();
        showLogin();
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1024, 768);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        navigationPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeViews() {
        contentPanel.add(loginPanel, VIEW_LOGIN);
        contentPanel.add(jobsPanel, VIEW_JOBS);
        contentPanel.add(myApplicationsPanel, VIEW_MY_APPLICATIONS);
        contentPanel.add(postJobPanel, VIEW_POST_JOB);
        contentPanel.add(myPostedJobsPanel, VIEW_MY_POSTED_JOBS);
    }

    public void onLoginSuccess(User user) {
        context.setCurrentUser(user);
        updateNavigation();
        showJobs();
    }

    // --- ISSUE #9: 添加登出确认弹窗 ---
    public void logout() {
        int choice = JOptionPane.showConfirmDialog(
                this,
                "Are you sure you want to log out?",
                "Confirm Logout",
                JOptionPane.YES_NO_OPTION,
                JOptionPane.QUESTION_MESSAGE
        );

        if (choice == JOptionPane.YES_OPTION) {
            context.setCurrentUser(null);
            updateNavigation();
            showLogin();
        }
    }

    public void showLogin() {
        showView(VIEW_LOGIN, loginPanel);
    }

    public void showJobs() {
        showView(VIEW_JOBS, jobsPanel);
    }

    public void showMyApplications() {
        showView(VIEW_MY_APPLICATIONS, myApplicationsPanel);
    }

    public void showPostJob() {
        showView(VIEW_POST_JOB, postJobPanel);
    }

    public void showMyPostedJobs() {
        showView(VIEW_MY_POSTED_JOBS, myPostedJobsPanel);
    }

    private void showView(String viewName, JPanel panel) {
        if (panel instanceof RefreshableView refreshableView) {
            refreshableView.refreshView();
        }
        cardLayout.show(contentPanel, viewName);
    }

    private void updateNavigation() {
        navigationPanel.removeAll();
        User currentUser = context.getCurrentUser();

        if (currentUser == null) {
            navigationPanel.setVisible(false);
        } else {
            navigationPanel.setVisible(true);
            navigationPanel.add(createNavButton("Discover Jobs", this::showJobs));

            if (currentUser.getRole() == Role.TA) {
                navigationPanel.add(createNavButton("My Applications", this::showMyApplications));
            } else if (currentUser.getRole() == Role.MO) {
                navigationPanel.add(createNavButton("Post New Job", this::showPostJob));
                navigationPanel.add(createNavButton("My Posted Jobs", this::showMyPostedJobs));
            }

            navigationPanel.add(createNavButton("Logout (" + currentUser.getName() + ")", this::logout));
        }

        navigationPanel.revalidate();
        navigationPanel.repaint();
    }

    private JButton createNavButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.setFont(new Font("SansSerif", Font.BOLD, 14));
        button.setForeground(new Color(60, 60, 60));
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setFocusPainted(false);
        button.setCursor(new Cursor(Cursor.HAND_CURSOR));
        button.addActionListener(event -> action.run());
        return button;
    }
}