package gui;

import java.awt.Color;
import java.awt.Font;
import javax.swing.*;
import javax.swing.border.EmptyBorder;

public class Theme 
{
    public static final Color BACKGROUND = new Color(23, 25, 30);
    public static final Color PANEL = new Color(34, 37, 44);
    public static final Color INPUT = new Color(44, 48, 56);
    public static final Color ACCENT = new Color(197, 165, 114);
    public static final Color TEXT = new Color(232, 232, 232);
    public static final Color MUTED = new Color(153, 157, 168);
    public static final Color SUCCESS = new Color(100, 180, 130);
    public static final Color DANGER = new Color(190, 80, 80);

    public static final Font FONT = new Font("Segoe UI", Font.PLAIN, 14);
    public static final Font TITLE_FONT = new Font("Segoe UI", Font.BOLD, 20);
    public static final Font HEADER_FONT = new Font("segoe UI", Font.BOLD, 14);

    private Theme()
    {
    }

    public static void apply()
    {
        UIManager.put("Panel.background", PANEL);

        UIManager.put("Label.foreground", TEXT);
        UIManager.put("Label.font", FONT);

        UIManager.put("Button.background", INPUT);
        UIManager.put("Button.foreground", TEXT);
        UIManager.put("Button.font", FONT);

        UIManager.put("TextField.background", INPUT);
        UIManager.put("TextField.foreground", TEXT);
        UIManager.put("TextField.caretForeground", TEXT);

        UIManager.put("TextArea.background", INPUT);
        UIManager.put("TextArea.foreground", TEXT);
        UIManager.put("TextArea.caretForeground", TEXT);

        UIManager.put("List.background", INPUT);
        UIManager.put("List.foreground", TEXT);
        UIManager.put("List.selectionBackground", ACCENT);
        UIManager.put("List.selectionForeground", BACKGROUND);

        UIManager.put("ComboBox.background", INPUT);
        UIManager.put("ComboBox.foreground", TEXT);

        UIManager.put("ScrollPane.background", PANEL);

        UIManager.put("OptionPane.background", PANEL);
        UIManager.put("OptionPane.messageForeground", TEXT);
    }

    public static JButton createButton(String text)
    {
        JButton button = new JButton(text);

        button.setBackground(INPUT);
        button.setForeground(TEXT);
        button.setFont(FONT);
        button.setFocusPainted(false);
        button.setBorder(new EmptyBorder(10, 15, 10, 15));

        return button;
    }

    public static JLabel createHeader(String text)
    {
        JLabel label = new JLabel(text);

        label.setFont(HEADER_FONT);
        label.setForeground(ACCENT);

        return label;
    }
}
