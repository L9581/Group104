package app;

import java.awt.BorderLayout;
import java.awt.CardLayout;
import java.awt.FlowLayout;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;

import model.Role;
import model.User;
import ui.JobsPanel;
import ui.LoginPanel;
import ui.MyPostedJobsPanel;
import ui.PostJobPanel;
import ui.RefreshableView;

/**
 * Main application window. It owns navigation and switches panels with CardLayout.
 */
public class AppWindow extends JFrame {
    private static final String VIEW_LOGIN = "login";
    private static final String VIEW_JOBS = "jobs";
    private static final String VIEW_POST_JOB = "postJob";
    private static final String VIEW_MY_POSTED_JOBS = "myPostedJobs";

    private final AppContext context;
    private final CardLayout cardLayout;
    private final JPanel contentPanel;
    private final JPanel navigationPanel;

    private final LoginPanel loginPanel;
    private final JobsPanel jobsPanel;
    private final PostJobPanel postJobPanel;
    private final MyPostedJobsPanel myPostedJobsPanel;

    public AppWindow(AppContext context) {
        super("TA Recruitment System");
        this.context = context;
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);
        this.navigationPanel = new JPanel(new FlowLayout(FlowLayout.LEFT));

        this.loginPanel = new LoginPanel(context, this);
        this.jobsPanel = new JobsPanel(context);
        this.postJobPanel = new PostJobPanel(context, this);
        this.myPostedJobsPanel = new MyPostedJobsPanel(context, this);

        initializeWindow();
        initializeViews();
        updateNavigation();
        showLogin();
    }

    private void initializeWindow() {
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(960, 640);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());
        add(navigationPanel, BorderLayout.NORTH);
        add(contentPanel, BorderLayout.CENTER);
    }

    private void initializeViews() {
        contentPanel.add(loginPanel, VIEW_LOGIN);
        contentPanel.add(jobsPanel, VIEW_JOBS);
        contentPanel.add(postJobPanel, VIEW_POST_JOB);
        contentPanel.add(myPostedJobsPanel, VIEW_MY_POSTED_JOBS);
    }

    public void onLoginSuccess(User user) {
        context.setCurrentUser(user);
        updateNavigation();
        showJobs();
    }

    public void logout() {
        context.setCurrentUser(null);
        updateNavigation();
        showLogin();
    }

    public void showLogin() {
        showView(VIEW_LOGIN, loginPanel);
    }

    public void showJobs() {
        showView(VIEW_JOBS, jobsPanel);
    }

    public void showPostJob() {
        showView(VIEW_POST_JOB, postJobPanel);
    }

    public void showMyPostedJobs() {
        showView(VIEW_MY_POSTED_JOBS, myPostedJobsPanel);
    }

    private void showView(String viewName, JPanel panel) {
        if (panel instanceof RefreshableView) {
            RefreshableView refreshableView = (RefreshableView) panel;
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
            navigationPanel.add(createButton("Jobs", this::showJobs));

            if (currentUser.getRole() == Role.MO) {
                navigationPanel.add(createButton("Post New Job", this::showPostJob));
                navigationPanel.add(createButton("My Posted Jobs", this::showMyPostedJobs));
            }

            navigationPanel.add(createButton("Logout", this::logout));
        }

        navigationPanel.revalidate();
        navigationPanel.repaint();
    }

    private JButton createButton(String text, Runnable action) {
        JButton button = new JButton(text);
        button.addActionListener(event -> action.run());
        return button;
    }
}
