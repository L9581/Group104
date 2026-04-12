package app;

import java.util.Locale; // 引入 Locale
import javax.swing.SwingUtilities;

/**
 * Application entry point.
 */
public class Main {
    public static void main(String[] args) {
        
        // 全局修复 Issue #16：强制 JVM 使用英文环境
        // 这样全系统所有的 JOptionPane 弹窗按钮都会统一显示为 OK, Yes, No, Cancel
        Locale.setDefault(Locale.ENGLISH);

        SwingUtilities.invokeLater(() -> {
            AppContext context = new AppContext();
            AppWindow appWindow = new AppWindow(context);
            appWindow.setVisible(true);
        });
    }
}