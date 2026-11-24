package network;

import java.io.IOException;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;
import java.util.UUID;

import management.WeaponManager;
import model.Weapon;

public class WeaponServiceImpl extends UnicastRemoteObject implements WeaponService
{
    private static final long serialVersionUID = 1L;

    private final WeaponManager manager;

    public WeaponServiceImpl(WeaponManager manager) throws RemoteException
    {
        super();
        this.manager = manager;
    }

    @Override
    public synchronized void addWeapon(Weapon weapon) throws RemoteException 
    {
        manager.addWeapon(weapon);
        saveWeapons();
    }

    @Override
    public synchronized boolean removeWeapon(UUID id) throws RemoteException 
    {
        boolean removed = manager.removeWeapon(id);

        if (removed)
        {
            saveWeapons();
        }

        return removed;
    }

    @Override
    public synchronized boolean updateWeapon(UUID id, Weapon weapon) throws RemoteException 
    {
        boolean updated = manager.updateWeapon(id, weapon);

        if (updated)
        {
            saveWeapons();
        }

        return updated;
    }

    @Override 
    public synchronized Weapon getWeapon(UUID id) throws RemoteException
    {
        return manager.getWeapon(id);
    }

    @Override
    public synchronized ArrayList<Weapon> getWeapons() throws RemoteException
    {
        return manager.getWeapons();
    }

    private void saveWeapons() throws RemoteException
    {
        try
        {
            manager.save();
        }
        catch (IOException e)
        {
            throw new RemoteException("Failed to save weapons", e);
        }
    }
    
}
