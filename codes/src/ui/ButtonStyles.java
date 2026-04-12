package ui;

import java.awt.Color;
import java.awt.Cursor;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.border.Border;
import javax.swing.plaf.basic.BasicButtonUI;

/**
 * Shared button styling helpers to keep custom Swing buttons visible across platforms.
 */
public final class ButtonStyles {
    private static final Color PRIMARY_BLUE = new Color(40, 116, 240);
    private static final Color DANGER_RED = new Color(180, 50, 50);
    private static final Color TEXT_DARK = new Color(60, 60, 60);
    private static final Border PRIMARY_BORDER = BorderFactory.createEmptyBorder(10, 16, 10, 16);
    private static final Border OUTLINE_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(PRIMARY_BLUE, 1, true),
            BorderFactory.createEmptyBorder(9, 15, 9, 15));
    private static final Border DANGER_OUTLINE_BORDER = BorderFactory.createCompoundBorder(
            BorderFactory.createLineBorder(DANGER_RED, 1, true),
            BorderFactory.createEmptyBorder(9, 15, 9, 15));
    private static final Border NAV_BORDER = BorderFactory.createEmptyBorder(8, 6, 8, 6);

    private ButtonStyles() {
    }

    public static void applyPrimary(JButton button) {
        installBase(button);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(false);
        button.setBackground(PRIMARY_BLUE);
        button.setForeground(Color.WHITE);
        button.setBorder(PRIMARY_BORDER);
    }

    public static void applyOutline(JButton button) {
        installBase(button);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBackground(Color.WHITE);
        button.setForeground(PRIMARY_BLUE);
        button.setBorder(OUTLINE_BORDER);
    }

    public static void applyDangerOutline(JButton button) {
        installBase(button);
        button.setOpaque(true);
        button.setContentAreaFilled(true);
        button.setBorderPainted(true);
        button.setBackground(Color.WHITE);
        button.setForeground(DANGER_RED);
        button.setBorder(DANGER_OUTLINE_BORDER);
    }

    public static void applyNavText(JButton button) {
        installBase(button);
        button.setOpaque(false);
        button.setContentAreaFilled(false);
        button.setBorderPainted(false);
        button.setBackground(new Color(0, 0, 0, 0));
        button.setForeground(TEXT_DARK);
        button.setBorder(NAV_BORDER);
    }

    private static void installBase(JButton button) {
        button.setUI(new BasicButtonUI());
        button.setFocusPainted(false);
        button.setRolloverEnabled(true);
        updateCursor(button);
        button.addPropertyChangeListener("enabled", event -> updateCursor(button));
    }

    private static void updateCursor(JButton button) {
        if (button.isEnabled()) {
            button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        } else {
            button.setCursor(Cursor.getDefaultCursor());
        }
    }
}
