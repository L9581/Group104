package app;

import javax.swing.SwingUtilities;
import javax.swing.UIManager;

public final class RecruitmentApp {
    private RecruitmentApp() {
    }

    public static void main(String[] args) {
        configureLookAndFeel();

        SwingUtilities.invokeLater(() -> {
            AppWindow window = new AppWindow();
            window.setVisible(true);
        });
    }

    private static void configureLookAndFeel() {
        try {
            UIManager.setLookAndFeel(UIManager.getSystemLookAndFeelClassName());
        } catch (Exception ignored) {
        }
    }
}
