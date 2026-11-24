package gui;

import controller.WeaponController;
import model.Weapon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;
import java.util.ArrayList;

public class MainWindow extends JFrame {
    private final WeaponController controller;

    private JLabel connectionStatus;
    private JLabel weaponCount;
    private WeaponListPanel weaponListPanel;
    private WeaponEditorPanel weaponEditorPanel;
    private DecoratorPanel decoratorPanel;

    private JButton connectButton;

    public MainWindow(WeaponController controller) {
        this.controller = controller;

        setTitle("RPG Weapon Toolkit");
        setSize(1280, 800);
        setMinimumSize(new Dimension(1000, 650));

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);

        initialise();
        initialiseEvents();
        refreshWeapons();
    }

    private void initialise() {
        JPanel root = new JPanel(new BorderLayout(10, 10));

        root.setBackground(Theme.BACKGROUND);
        root.setBorder(new EmptyBorder(12, 12, 12, 12));

        setContentPane(root);

        root.add(createHeader(), BorderLayout.NORTH);
        root.add(createWorkspace(), BorderLayout.CENTER);
        root.add(createFooter(), BorderLayout.SOUTH);
    }

    private JPanel createHeader() {
        JPanel header = new JPanel(new BorderLayout());

        header.setBorder(new EmptyBorder(12, 15, 12, 15));

        JLabel title = new JLabel("RPG WEAPON TOOLKIT");

        title.setFont(Theme.TITLE_FONT);
        title.setForeground(Theme.ACCENT);

        connectionStatus = new JLabel("LOCAL MODE");
        connectionStatus.setForeground(Theme.SUCCESS);

        header.add(title, BorderLayout.WEST);
        header.add(connectionStatus, BorderLayout.EAST);

        return header;
    }

    private JPanel createWorkspace() {
        JPanel workspace = new JPanel(new GridBagLayout());

        workspace.setBackground(Theme.BACKGROUND);

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.BOTH;
        constraints.weighty = 1.0;
        constraints.gridy = 0;
        constraints.insets = new Insets(0, 4, 0, 4);

        // Left Panel - Weapon Library
        constraints.gridx = 0;
        constraints.weightx = 0.25;

        weaponListPanel = new WeaponListPanel();

        workspace.add(weaponListPanel, constraints);

        // Middle Panel - Weapon Editor
        constraints.gridx = 1;
        constraints.weightx = 0.45;

        weaponEditorPanel = new WeaponEditorPanel();

        workspace.add(weaponEditorPanel, constraints);

        // Right Panel - Decorators
        constraints.gridx = 2;
        constraints.weightx = 0.3;

        decoratorPanel = new DecoratorPanel();

        workspace.add(decoratorPanel, constraints);

        return workspace;
    }

    private JPanel createFooter() {
        JPanel footer = new JPanel(new BorderLayout());

        footer.setBorder(new EmptyBorder(8, 12, 8, 12));

        weaponCount = new JLabel("0 weapons loaded");
        weaponCount.setForeground(Theme.MUTED);

        connectButton = Theme.createButton("Connect to Server");

        footer.add(weaponCount, BorderLayout.WEST);
        footer.add(connectButton, BorderLayout.EAST);

        return footer;
    }

    private void initialiseEvents() {
        weaponListPanel.onNewWeapon(() -> {
            weaponListPanel.clearSelection();
            weaponEditorPanel.clearEditor();
            decoratorPanel.clearDecorators();
        });

        weaponListPanel.onDeleteWeapon(weapon -> {
            deleteWeapon(weapon);
        });

        weaponListPanel.onWeaponSelected(weapon -> {
            if (weapon == null) {
                weaponEditorPanel.clearEditor();
                decoratorPanel.clearDecorators();
                return;
            }

            Weapon baseWeapon = decoratorPanel.loadWeapon(weapon);

            weaponEditorPanel.loadWeapon(baseWeapon);
            decoratorPanel.updateStats(weapon);
        });

        weaponEditorPanel.onSaveWeapon(() -> {
            saveWeapon();
        });

        decoratorPanel.onDecoratorChanged(() -> {
            previewWeapon();
        });

        connectButton.addActionListener(event -> {
            toggleConnection();
        });
    }

    private void refreshWeapons() {
        try {
            ArrayList<Weapon> weapons = controller.getWeapons();

            weaponListPanel.setWeapons(weapons);
            weaponListPanel.clearSelection();
            weaponEditorPanel.clearEditor();
            decoratorPanel.clearDecorators();
            weaponCount.setText(weapons.size() + " weapons loaded");
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed to load weapons", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void deleteWeapon(Weapon weapon) {
        int confirmation = JOptionPane.showConfirmDialog(this, "Delete " + weapon.getName() + "?", "Delete Weapon",
                JOptionPane.YES_NO_OPTION);

        if (confirmation != JOptionPane.YES_OPTION) {
            return;
        }

        try {
            if (controller.removeWeapon(weapon.getId())) {
                refreshWeapons();
            } else {
                JOptionPane.showMessageDialog(this, "Weapon could not be found.");
            }
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed to delete weapon", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void toggleConnection() {
        if (controller.isConnected()) {
            controller.disconnect();

            connectionStatus.setText("LOCAL MODE");
            connectionStatus.setForeground(Theme.SUCCESS);
            connectButton.setText("Connect to Server");

            refreshWeapons();
            return;
        }

        String host = JOptionPane.showInputDialog(this, "Enter server address:", "localhost");

        if (host == null || host.isBlank()) {
            return;
        }

        try {
            controller.connect(host.trim());

            connectionStatus.setText("SERVER MODE");
            connectionStatus.setForeground(Theme.ACCENT);
            connectButton.setText("Disconnect from Server");

            refreshWeapons();
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "Could not connect to server.\n" + e.getMessage(), "Connection Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    private void saveWeapon() {
        try {
            UUID editingId = weaponEditorPanel.getEditingId();

            Weapon weapon = weaponEditorPanel.createWeapon();

            weapon = decoratorPanel.applyDecorators(weapon);

            if (editingId == null) {
                controller.addWeapon(weapon);
            } else {
                if (!controller.updateWeapon(editingId, weapon)) {
                    JOptionPane.showMessageDialog(this, "Weapon could not be updated.");
                    return;
                }
            }

            refreshWeapons();

            weaponListPanel.selectWeapon(weapon.getId());

            decoratorPanel.updateStats(weapon);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, e.getMessage(), "Failed to save weapon", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void previewWeapon() {
        try {
            Weapon weapon = weaponEditorPanel.createWeapon();

            weapon = decoratorPanel.applyDecorators(weapon);

            decoratorPanel.updateStats(weapon);
        } catch (IllegalArgumentException e) {
            decoratorPanel.updateStats(null);
        }
    }

}