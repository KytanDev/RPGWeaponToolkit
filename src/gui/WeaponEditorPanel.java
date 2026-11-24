package gui;

import model.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.UUID;

public class WeaponEditorPanel extends JPanel
{
    private JTextField nameField;
    private JTextArea descriptionField;
    private JComboBox<WeaponType> typeField;

    private JSpinner valueField;
    private JSpinner physicalDamageField;
    private JSpinner fireDamageField;
    private JSpinner frostDamageField;
    private JSpinner attackSpeedField;
    private JSpinner criticalChanceField;
    private JSpinner criticalMultiplierField;
    private JSpinner armourPenetrationField;
    private JSpinner lifeStealField;
    private JSpinner durabilityField;
    private JSpinner staminaCostField;

    private JButton saveButton;

    private UUID editingId;

    public WeaponEditorPanel()
    {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.PANEL);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        initialise();
        clearEditor();
    }

    private void initialise()
    {
        add(Theme.createHeader("WEAPON EDITOR"), BorderLayout.NORTH);

        JPanel form = new JPanel(new GridBagLayout());

        GridBagConstraints constraints = new GridBagConstraints();

        constraints.fill = GridBagConstraints.HORIZONTAL;
        constraints.insets = new Insets(5, 0, 5, 0);
        constraints.gridy = 0;

        nameField = new JTextField();

        descriptionField = new JTextArea(3, 20);
        descriptionField.setLineWrap(true);
        descriptionField.setWrapStyleWord(true);

        typeField = new JComboBox<>(WeaponType.values());

        valueField = createSpinner(100, 0, 1000000, 10);
        physicalDamageField = createSpinner(30, 0, 100000, 1);
        fireDamageField = createSpinner(0, 0, 100000, 1);
        frostDamageField = createSpinner(0, 0, 100000, 1);
        attackSpeedField = createSpinner(1, 0.1, 100, 0.1);
        criticalChanceField = createSpinner(5, 0, 100, 1);
        criticalMultiplierField = createSpinner(1.5, 1, 100, 0.1);
        armourPenetrationField = createSpinner(0, 0, 100, 1);
        lifeStealField = createSpinner(0, 0, 100, 1);
        durabilityField = createSpinner(100, 0, 100000, 1);
        staminaCostField = createSpinner(10, 0, 100000, 1);

        // General Weapon Information
        addField(form, constraints, "Name", nameField);
        addField(form, constraints, "Description", new JScrollPane(descriptionField));
        addField(form, constraints, "Weapon Type", typeField);
        addField(form, constraints, "Base Value", valueField);

        // Base Weapon Statistics
        addField(form, constraints, "Physical Damage", physicalDamageField);
        addField(form, constraints, "Fire Damage", fireDamageField);
        addField(form, constraints, "Frost Damage", frostDamageField);
        addField(form, constraints, "Attack Speed", attackSpeedField);
        addField(form, constraints, "Critical Chance (%)", criticalChanceField);
        addField(form, constraints, "Critical Multiplier", criticalMultiplierField);
        addField(form, constraints, "Armour Penetration", armourPenetrationField);
        addField(form, constraints, "Life Steal (%)", lifeStealField);
        addField(form, constraints, "Durability", durabilityField);
        addField(form, constraints, "Stamina Cost", staminaCostField);

        constraints.gridy++;
        constraints.weighty = 1.0;

        form.add(new JPanel(), constraints);

        JScrollPane scrollPane = new JScrollPane(form);

        scrollPane.setBorder(null);
        scrollPane.getVerticalScrollBar().setUnitIncrement(16);

        add(scrollPane, BorderLayout.CENTER);

        saveButton = Theme.createButton("Save Weapon");
        saveButton.setBackground(Theme.ACCENT);
        saveButton.setForeground(Theme.BACKGROUND);

        add(saveButton, BorderLayout.SOUTH);
    }

    private JSpinner createSpinner(double value, double minimum, double maximum, double step)
    {
        JSpinner spinner = new JSpinner(new SpinnerNumberModel(value, minimum, maximum, step));

        JSpinner.DefaultEditor editor = (JSpinner.DefaultEditor) spinner.getEditor();

        editor.getTextField().setBackground(Theme.INPUT);
        editor.getTextField().setForeground(Theme.TEXT);
        editor.getTextField().setCaretColor(Theme.TEXT);

        return spinner;
    }

    private void addField(JPanel panel, GridBagConstraints constraints, String name, JComponent component)
    {
        constraints.gridx = 0;
        constraints.weightx = 0.45;

        panel.add(new JLabel(name), constraints);

        constraints.gridx = 1;
        constraints.weightx = 0.55;

        panel.add(component, constraints);

        constraints.gridy++;
    }

    private double getValue(JSpinner spinner)
    {
        try
        {
            spinner.commitEdit();
        }
        catch (java.text.ParseException e)
        {
            throw new IllegalArgumentException("Invalid numerical value.");
        }

        return ((Number) spinner.getValue()).doubleValue();
    }

    // Create Weapon From Editor Values
    public Weapon createWeapon()
    {
        String name = nameField.getText().trim();
        String description = descriptionField.getText().trim();

        if (name.isEmpty())
        {
            throw new IllegalArgumentException("Weapon name cannot be empty.");
        }

        WeaponType type = (WeaponType) typeField.getSelectedItem();

        WeaponStats stats = new WeaponStats(
            getValue(armourPenetrationField),
            getValue(attackSpeedField),
            getValue(criticalChanceField) / 100,
            getValue(criticalMultiplierField),
            (int) getValue(durabilityField),
            getValue(fireDamageField),
            getValue(frostDamageField),
            getValue(lifeStealField) / 100,
            getValue(physicalDamageField),
            getValue(staminaCostField)
        );

        if (editingId != null)
        {
            return new BaseWeapon(editingId, description, name, stats, type, getValue(valueField));
        }

        return new BaseWeapon(description, name, stats, type, getValue(valueField));
    }

    // Load Base Weapon Into Editor
    public void loadWeapon(Weapon weapon)
    {
        if (weapon == null)
        {
            clearEditor();
            return;
        }

        editingId = weapon.getId();

        WeaponStats stats = weapon.getStats();

        nameField.setText(weapon.getName());
        descriptionField.setText(weapon.getDescription());
        typeField.setSelectedItem(weapon.getType());

        valueField.setValue(weapon.getValue());
        physicalDamageField.setValue(stats.getPhysicalDamage());
        fireDamageField.setValue(stats.getFireDamage());
        frostDamageField.setValue(stats.getFrostDamage());
        attackSpeedField.setValue(stats.getAttackSpeed());
        criticalChanceField.setValue(stats.getCriticalChance() * 100);
        criticalMultiplierField.setValue(stats.getCriticalMultiplier());
        armourPenetrationField.setValue(stats.getArmourPenetration());
        lifeStealField.setValue(stats.getLifeSteal() * 100);
        durabilityField.setValue(stats.getDurability());
        staminaCostField.setValue(stats.getStaminaCost());

        saveButton.setText("Update Weapon");
    }

    // Reset Editor For New Weapon
    public void clearEditor()
    {
        editingId = null;

        nameField.setText("");
        descriptionField.setText("");
        typeField.setSelectedIndex(0);

        valueField.setValue(100.0);
        physicalDamageField.setValue(30.0);
        fireDamageField.setValue(0.0);
        frostDamageField.setValue(0.0);
        attackSpeedField.setValue(1.0);
        criticalChanceField.setValue(5.0);
        criticalMultiplierField.setValue(1.5);
        armourPenetrationField.setValue(0.0);
        lifeStealField.setValue(0.0);
        durabilityField.setValue(100);
        staminaCostField.setValue(10.0);

        saveButton.setText("Create Weapon");
    }

    public UUID getEditingId()
    {
        return editingId;
    }

    public void onSaveWeapon(Runnable action)
    {
        saveButton.addActionListener(event -> { action.run(); });
    }
}