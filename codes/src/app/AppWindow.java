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
import javax.swing.JPanel;

import model.Role;
import model.User;
import ui.JobsPanel;
import ui.LoginPanel;
import ui.MyPostedJobsPanel;
import ui.PostJobPanel;
import ui.RefreshableView;

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
        super("BUPT TA Recruitment System");
        this.context = context;
        this.cardLayout = new CardLayout();
        this.contentPanel = new JPanel(cardLayout);

        // 居中对齐的现代导航栏
        this.navigationPanel = new JPanel(new FlowLayout(FlowLayout.CENTER, 30, 15));
        this.navigationPanel.setBackground(Color.WHITE);

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
        setSize(1024, 768); // 稍微增大窗口，让留白更舒展
        setLocationRelativeTo(null);
        setLayout(new BorderLayout());

        // 给导航栏底部加一条极细的浅灰分割线
        navigationPanel.setBorder(BorderFactory.createMatteBorder(0, 0, 1, 0, new Color(230, 230, 235)));

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

    public void showLogin() { showView(VIEW_LOGIN, loginPanel); }
    public void showJobs() { showView(VIEW_JOBS, jobsPanel); }
    public void showPostJob() { showView(VIEW_POST_JOB, postJobPanel); }
    public void showMyPostedJobs() { showView(VIEW_MY_POSTED_JOBS, myPostedJobsPanel); }

    private void showView(String viewName, JPanel panel) {
        if (panel instanceof RefreshableView) {
            ((RefreshableView) panel).refreshView();
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

            if (currentUser.getRole() == Role.MO) {
                navigationPanel.add(createNavButton("Post New Job", this::showPostJob));
                navigationPanel.add(createNavButton("My Posted Jobs", this::showMyPostedJobs));
            }
            navigationPanel.add(createNavButton("Logout (" + currentUser.getName() + ")", this::logout));
        }
        navigationPanel.revalidate();
        navigationPanel.repaint();
    }

    // 扁平化文字导航按钮
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