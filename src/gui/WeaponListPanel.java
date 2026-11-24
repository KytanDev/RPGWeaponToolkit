package gui;

import model.Weapon;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import java.awt.*;
import java.util.ArrayList;
import java.util.UUID;
import java.util.function.Consumer;

public class WeaponListPanel extends JPanel
{
    private final DefaultListModel<Weapon> listModel;
    private final JList<Weapon> weaponList;

    private final JButton newButton;
    private final JButton deleteButton;

    public WeaponListPanel()
    {
        setLayout(new BorderLayout(10, 10));
        setBackground(Theme.PANEL);
        setBorder(new EmptyBorder(15, 15, 15, 15));

        listModel = new DefaultListModel<>();
        weaponList = new JList<>(listModel);

        newButton = Theme.createButton("+ New Weapon");
        deleteButton = Theme.createButton("Delete Weapon");

        initialise();
    }

    private void initialise()
    {
        add(Theme.createHeader("WEAPON LIBRARY"), BorderLayout.NORTH);

        weaponList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        weaponList.setFixedCellHeight(45);
        weaponList.setFont(Theme.FONT);
        weaponList.setBackground(Theme.INPUT);
        weaponList.setForeground(Theme.TEXT);

        // Display weapon names and types
        weaponList.setCellRenderer(new DefaultListCellRenderer()
        {
            @Override
            public Component getListCellRendererComponent(JList<?> list, Object value, int index, boolean isSelected, boolean cellHasFocus)
            {
                super.getListCellRendererComponent(list, value, index, isSelected, cellHasFocus);

                if (value instanceof Weapon)
                {
                    Weapon weapon = (Weapon) value;

                    setText(weapon.getName() + " | " + weapon.getType());
                }

                setBorder(new EmptyBorder(5, 10, 5, 10));

                return this;
            }
        });

        JScrollPane scrollPane = new JScrollPane(weaponList);

        scrollPane.setBorder(null);

        add(scrollPane, BorderLayout.CENTER);
        add(createButtons(), BorderLayout.SOUTH);

        weaponList.addListSelectionListener(event ->
        {
            deleteButton.setEnabled(getSelectedWeapon() != null);
        });
    }

    private JPanel createButtons()
    {
        JPanel buttons = new JPanel(new GridLayout(2, 1, 0, 8));

        newButton.setForeground(Theme.ACCENT);

        deleteButton.setForeground(Theme.DANGER);
        deleteButton.setEnabled(false);

        buttons.add(newButton);
        buttons.add(deleteButton);

        return buttons;
    }

    // Update the displayed weapon collection
    public void setWeapons(ArrayList<Weapon> weapons)
    {
        listModel.clear();

        for (Weapon weapon : weapons)
        {
            listModel.addElement(weapon);
        }
    }

    public Weapon getSelectedWeapon()
    {
        return weaponList.getSelectedValue();
    }

    public void selectWeapon(UUID id)
    {
        for (int i = 0; i < listModel.size(); i++)
        {
            if (listModel.getElementAt(i).getId().equals(id))
            {
                weaponList.setSelectedIndex(i);
                weaponList.ensureIndexIsVisible(i);
                return;
            }
        }
    }

    public void clearSelection()
    {
        weaponList.clearSelection();
    }

    // Weapon creation event
    public void onNewWeapon(Runnable action)
    {
        newButton.addActionListener(event ->
        {
            action.run();
        });
    }

    // Weapon deletion event
    public void onDeleteWeapon(Consumer<Weapon> action)
    {
        deleteButton.addActionListener(event ->
        {
            Weapon weapon = getSelectedWeapon();

            if (weapon != null)
            {
                action.accept(weapon);
            }
        });
    }

    // Weapon selection event
    public void onWeaponSelected(Consumer<Weapon> action)
    {
        weaponList.addListSelectionListener(event ->
        {
            if (!event.getValueIsAdjusting())
            {
                action.accept(getSelectedWeapon());
            }
        });
    }
}