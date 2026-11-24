package management;

import java.util.ArrayList;
import java.util.UUID;
import model.Weapon;
import java.io.IOException;

public class WeaponManager 
{
    private final ArrayList<Weapon> weapons;
    private final WeaponStorage storage;

    public void addWeapon(Weapon weapon)
    {
        weapons.add(weapon);
    }

    public WeaponManager(String filePath) throws IOException, ClassNotFoundException
    {
        storage = new WeaponStorage(filePath);
        weapons = storage.loadWeapons();
    }

    public void save() throws IOException
    {
        storage.saveWeapons(weapons);
    }

    public boolean removeWeapon(UUID id)
    {
        for (int i = 0; i < weapons.size(); i++)
        {
            if (weapons.get(i).getId().equals(id))
            {
                weapons.remove(i);
                return true;
            }
        }
        return false;
    }

    public Weapon getWeapon(UUID id)
    {
        for (Weapon weapon : weapons)
        {
            if (weapon.getId().equals(id))
            {
                return weapon;
            }
        }
        return null;
    }

    public ArrayList<Weapon> getWeapons()
    {
        return new ArrayList<>(weapons);
    }

    public boolean updateWeapon(UUID id, Weapon weapon)
    {
        if (!weapon.getId().equals(id))
        {
            return false;
        }

        for (int i = 0; i < weapons.size(); i++)
        {
            if (weapons.get(i).getId().equals(id))
            {
                weapons.set(i, weapon);
                return true;
            }
        }
        return false;
    }

    public void clearWeapons()
    {
        weapons.clear();
    }

}
