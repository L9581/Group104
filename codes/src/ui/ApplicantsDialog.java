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
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;

import app.AppWindow;
import model.Application;
import model.ApplicationStatus;
import model.Job;
import service.ApplicationService;

public class ApplicantsDialog extends JDialog {
    private final ApplicationService applicationService;
    private final AppWindow appWindow;
    private final Job job;
    private final JPanel listPanel;

    public ApplicantsDialog(AppWindow appWindow, ApplicationService applicationService, Job job) {
        super(appWindow, "Applicants for: " + job.getJobName(), true);
        this.applicationService = applicationService;
        this.appWindow = appWindow;
        this.job = job;
        this.listPanel = new JPanel();

        initializeDialog();
        refreshApplicants();
    }

    private void initializeDialog() {
        setLayout(new BorderLayout());
        // 弹窗内部同样使用全局的灰蓝色背景
        getContentPane().setBackground(new Color(245, 247, 250));
        ((JPanel) getContentPane()).setBorder(BorderFactory.createEmptyBorder(20, 30, 20, 30));
        setSize(540, 450);
        setLocationRelativeTo(appWindow);

        listPanel.setLayout(new BoxLayout(listPanel, BoxLayout.Y_AXIS));
        listPanel.setOpaque(false); // 必须设置为透明，才能露出底部的灰蓝色

        JScrollPane scrollPane = new JScrollPane(listPanel);
        scrollPane.setBorder(null); // 移除滚动条丑陋的默认边框
        scrollPane.setOpaque(false);
        scrollPane.getViewport().setOpaque(false);
        add(scrollPane, BorderLayout.CENTER);
    }

    private void refreshApplicants() {
        listPanel.removeAll();
        List<Application> applications = applicationService.listApplicationsForJob(job.getJobId());

        if (applications.isEmpty()) {
            JLabel empty = new JLabel("No applicants yet.");
            empty.setFont(new Font("SansSerif", Font.ITALIC, 14));
            empty.setForeground(new Color(150, 150, 150));
            listPanel.add(empty);
        } else {
            for (Application app : applications) {
                listPanel.add(createApplicantCard(app));
            }
        }
        listPanel.revalidate();
        listPanel.repaint();
    }

    private JPanel createApplicantCard(Application application) {
        // 外层 Wrapper 用于制造卡片之间的垂直间距
        JPanel wrapper = new JPanel(new BorderLayout()) {
            @Override public Dimension getMaximumSize() { return new Dimension(Integer.MAX_VALUE, getPreferredSize().height); }
        };
        wrapper.setOpaque(false);
        wrapper.setBorder(BorderFactory.createEmptyBorder(0, 0, 10, 0));

        // 真实的纯白卡片
        JPanel card = new JPanel(new BorderLayout(15, 0));
        card.setBackground(Color.WHITE);
        card.setBorder(BorderFactory.createCompoundBorder(
                BorderFactory.createLineBorder(new Color(225, 228, 232), 1, true),
                BorderFactory.createEmptyBorder(15, 20, 15, 20)
        ));

        // 申请人名字及状态
        String labelText = application.getApplicantName();
        boolean isPending = application.getStatus() == ApplicationStatus.PENDING;
        if (!isPending) labelText += " • " + application.getStatus().name();

        JLabel nameLabel = new JLabel(labelText);
        nameLabel.setFont(new Font("SansSerif", isPending ? Font.BOLD : Font.PLAIN, 15));
        nameLabel.setForeground(isPending ? new Color(40, 40, 40) : new Color(150, 150, 150));
        card.add(nameLabel, BorderLayout.CENTER);

        // 录用按钮：现代化扁平设计
        JButton hireButton = new JButton("Hire Candidate");
        hireButton.setFont(new Font("SansSerif", Font.BOLD, 12));
        hireButton.setPreferredSize(new Dimension(130, 32));
        hireButton.setFocusPainted(false);
        hireButton.setCursor(new Cursor(Cursor.HAND_CURSOR));

        if (isPending) {
            hireButton.setForeground(Color.WHITE);
            hireButton.setBackground(new Color(40, 167, 69)); // 录用按钮用绿色，代表积极操作
            hireButton.setBorderPainted(false);
            hireButton.addActionListener(event -> hireApplicant(application));
        } else {
            hireButton.setEnabled(false);
            hireButton.setText("Closed");
        }

        // 使用 GridBagLayout 包裹按钮，使其在卡片右侧完美垂直居中
        JPanel btnWrapper = new JPanel(new GridBagLayout());
        btnWrapper.setOpaque(false);
        btnWrapper.add(hireButton);
        card.add(btnWrapper, BorderLayout.EAST);

        wrapper.add(card, BorderLayout.CENTER);
        return wrapper;
    }

    private void hireApplicant(Application application) {
        int choice = JOptionPane.showConfirmDialog(this, "Hire " + application.getApplicantName() + " and close this job?", "Confirm Decision", JOptionPane.YES_NO_OPTION);
        if (choice == JOptionPane.YES_OPTION) {
            try {
                applicationService.hireApplicant(job.getJobId(), application.getApplicantName());
                JOptionPane.showMessageDialog(this, "Applicant hired successfully!", "Success", JOptionPane.INFORMATION_MESSAGE);
                appWindow.showMyPostedJobs();
                dispose(); // 录用后关闭弹窗
            } catch (IllegalArgumentException exception) {
                JOptionPane.showMessageDialog(this, exception.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }
}