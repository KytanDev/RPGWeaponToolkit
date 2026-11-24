package gui;

import model.*;
import decorators.*;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;

public class DecoratorPanel extends JPanel
{
    private final String[] decoratorTypes =
    {
        "Sharpened",
        "Tempered",
        "Flame",
        "Frost",
        "Vampiric",
        "Berserker",
        "Lightweight"
    };

    private DefaultListModel<String> decoratorModel;
    private JList<String> decoratorList;
    private JComboBox<String> decoratorSelection;

    private JButton addButton;
    private JButton removeButton;
    private JButton moveUpButton;
    private JButton moveDownButton;

    private JTextArea finalStats;

    private Runnable changeAction;

    public DecoratorPanel()
    {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.PANEL);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        initialise();
    }

    private void initialise()
    {
        add(Theme.createHeader("MODIFIERS & FINAL STATS"), BorderLayout.NORTH);

        JPanel content = new JPanel(new GridLayout(2, 1, 0, 15));

        content.add(createDecoratorSection());
        content.add(createStatsSection());

        add(content, BorderLayout.CENTER);

        initialiseEvents();
    }

    private JPanel createDecoratorSection()
    {
        JPanel section = new JPanel(new BorderLayout(5, 10));

        decoratorModel = new DefaultListModel<>();
        decoratorList = new JList<>(decoratorModel);

        decoratorList.setFixedCellHeight(35);
        decoratorList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

        JScrollPane scrollPane = new JScrollPane(decoratorList);

        scrollPane.setBorder(null);

        decoratorSelection = new JComboBox<>(decoratorTypes);

        addButton = Theme.createButton("Add Modifier");
        removeButton = Theme.createButton("Remove");
        moveUpButton = Theme.createButton("Move Up");
        moveDownButton = Theme.createButton("Move Down");

        addButton.setForeground(Theme.ACCENT);
        removeButton.setForeground(Theme.DANGER);

        JPanel selectionPanel = new JPanel(new BorderLayout(5, 5));

        selectionPanel.add(decoratorSelection, BorderLayout.CENTER);
        selectionPanel.add(addButton, BorderLayout.SOUTH);

        JPanel buttons = new JPanel(new GridLayout(1, 3, 5, 0));

        buttons.add(moveUpButton);
        buttons.add(moveDownButton);
        buttons.add(removeButton);

        section.add(selectionPanel, BorderLayout.NORTH);
        section.add(scrollPane, BorderLayout.CENTER);
        section.add(buttons, BorderLayout.SOUTH);

        return section;
    }

    private JPanel createStatsSection()
    {
        JPanel section = new JPanel(new BorderLayout(5, 10));

        section.add(Theme.createHeader("FINAL WEAPON STATISTICS"), BorderLayout.NORTH);

        finalStats = new JTextArea();

        finalStats.setEditable(false);
        finalStats.setFont(new Font("Consolas", Font.PLAIN, 13));
        finalStats.setBackground(Theme.INPUT);
        finalStats.setForeground(Theme.TEXT);
        finalStats.setBorder(new EmptyBorder(10, 10, 10, 10));

        JScrollPane scrollPane = new JScrollPane(finalStats);

        scrollPane.setBorder(null);

        section.add(scrollPane, BorderLayout.CENTER);

        return section;
    }

    private void initialiseEvents()
    {
        addButton.addActionListener(event ->
        {
            String decorator = (String) decoratorSelection.getSelectedItem();

            decoratorModel.addElement(decorator);
            decoratorList.setSelectedIndex(decoratorModel.size() - 1);

            notifyChange();
        });

        removeButton.addActionListener(event ->
        {
            int index = decoratorList.getSelectedIndex();

            if (index != -1)
            {
                decoratorModel.remove(index);
                notifyChange();
            }
        });

        moveUpButton.addActionListener(event ->
        {
            moveDecorator(-1);
        });

        moveDownButton.addActionListener(event ->
        {
            moveDecorator(1);
        });
    }

    private void moveDecorator(int direction)
    {
        int index = decoratorList.getSelectedIndex();
        int newIndex = index + direction;

        if (index == -1 || newIndex < 0 || newIndex >= decoratorModel.size())
        {
            return;
        }

        String decorator = decoratorModel.getElementAt(index);

        decoratorModel.remove(index);
        decoratorModel.add(newIndex, decorator);

        decoratorList.setSelectedIndex(newIndex);

        notifyChange();
    }

    // Apply Decorators In List Order
    public Weapon applyDecorators(Weapon weapon)
    {
        for (int i = 0; i < decoratorModel.size(); i++)
        {
            String decorator = decoratorModel.getElementAt(i);

            switch (decorator)
            {
                case "Sharpened":
                    weapon = new SharpenedDecorator(weapon);
                    break;

                case "Tempered":
                    weapon = new TemperedDecorator(weapon);
                    break;

                case "Flame":
                    weapon = new FlameDecorator(weapon);
                    break;

                case "Frost":
                    weapon = new FrostDecorator(weapon);
                    break;

                case "Vampiric":
                    weapon = new VampiricDecorator(weapon);
                    break;

                case "Berserker":
                    weapon = new BerserkerDecorator(weapon);
                    break;

                case "Lightweight":
                    weapon = new LightweightDecorator(weapon);
                    break;
            }
        }

        return weapon;
    }

    // Extract Decorators And Return Original Base Weapon
    public Weapon loadWeapon(Weapon weapon)
    {
        decoratorModel.clear();

        ArrayList<String> decorators = new ArrayList<>();

        while (weapon instanceof WeaponDecorator)
        {
            if (weapon instanceof SharpenedDecorator)
            {
                decorators.add("Sharpened");
            }
            else if (weapon instanceof TemperedDecorator)
            {
                decorators.add("Tempered");
            }
            else if (weapon instanceof FlameDecorator)
            {
                decorators.add("Flame");
            }
            else if (weapon instanceof FrostDecorator)
            {
                decorators.add("Frost");
            }
            else if (weapon instanceof VampiricDecorator)
            {
                decorators.add("Vampiric");
            }
            else if (weapon instanceof BerserkerDecorator)
            {
                decorators.add("Berserker");
            }
            else if (weapon instanceof LightweightDecorator)
            {
                decorators.add("Lightweight");
            }

            WeaponDecorator decorator = (WeaponDecorator) weapon;

            weapon = decorator.getWrappedWeapon();
        }

        Collections.reverse(decorators);

        for (String decorator : decorators)
        {
            decoratorModel.addElement(decorator);
        }

        return weapon;
    }

    // Display Calculated Weapon Statistics
    public void updateStats(Weapon weapon)
    {
        if (weapon == null)
        {
            finalStats.setText("");
            return;
        }

        WeaponStats stats = weapon.getStats();

        String text = String.format(
            "Physical Damage:     %.2f%n" +
            "Fire Damage:         %.2f%n" +
            "Frost Damage:        %.2f%n" +
            "Attack Speed:        %.2f%n" +
            "Critical Chance:     %.2f%%%n" +
            "Critical Multiplier: %.2f%n" +
            "Armour Penetration:  %.2f%n" +
            "Life Steal:          %.2f%%%n" +
            "Durability:          %d%n" +
            "Stamina Cost:        %.2f%n" +
            "%nWeapon Value:        %.2f",
            stats.getPhysicalDamage(),
            stats.getFireDamage(),
            stats.getFrostDamage(),
            stats.getAttackSpeed(),
            stats.getCriticalChance() * 100,
            stats.getCriticalMultiplier(),
            stats.getArmourPenetration(),
            stats.getLifeSteal() * 100,
            stats.getDurability(),
            stats.getStaminaCost(),
            weapon.getValue()
        );

        finalStats.setText(text);
        finalStats.setCaretPosition(0);
    }

    public void clearDecorators()
    {
        decoratorModel.clear();
        finalStats.setText("");
    }

    public void onDecoratorChanged(Runnable action)
    {
        changeAction = action;
    }

    private void notifyChange()
    {
        if (changeAction != null)
        {
            changeAction.run();
        }
    }
}