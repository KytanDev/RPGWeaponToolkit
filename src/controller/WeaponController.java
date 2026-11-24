package controller;

import java.util.ArrayList;
import java.util.UUID;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import model.Weapon;
import management.WeaponManager;
import network.WeaponService;

public class WeaponController 
{
    private final WeaponManager localManager;
    private WeaponService remoteService;

    public WeaponController() throws IOException, ClassNotFoundException
    {
        localManager = new WeaponManager("data/weapons-local.ser");
        remoteService = null;
    }

    // Attempt to connect to RMI server
    public void connect(String host) throws Exception
    {
        Registry registry = LocateRegistry.getRegistry(host, 1099);
        WeaponService service = (WeaponService) registry.lookup("WeaponService");

        // Verify the service is responding
        service.getWeapons();

        remoteService = service;
    }

    // Return to local mode
    public void disconnect()
    {
        remoteService = null;
    }

    public boolean isConnected()
    {
        return remoteService != null;
    }

    // CREATE
    public void addWeapon(Weapon weapon) throws IOException
    {
        if (isConnected())
        {
            remoteService.addWeapon(weapon);
        }
        else
        {
            localManager.addWeapon(weapon);
            localManager.save();
        }
    }

    // READ
    public Weapon getWeapon(UUID id) throws IOException
    {
        if (isConnected())
        {
            return remoteService.getWeapon(id);
        }
        
        return localManager.getWeapon(id);
    }

    public ArrayList<Weapon> getWeapons() throws IOException
    {
        if (isConnected())
        {
            return remoteService.getWeapons();
        }

        return localManager.getWeapons();
    }

    // UPDATE
    public boolean updateWeapon(UUID id, Weapon weapon) throws IOException
    {
        if (isConnected())
        {
            return remoteService.updateWeapon(id, weapon);
        }

        boolean updated = localManager.updateWeapon(id, weapon);

        if (updated)
        {
            localManager.save();
        }

        return updated;
    }

    // DELETE
    public boolean removeWeapon(UUID id) throws IOException
    {
        if (isConnected())
        {
            return remoteService.removeWeapon(id);
        }

        boolean removed = localManager.removeWeapon(id);

        if (removed)
        {
            localManager.save();
        }

        return removed;
    }
}
