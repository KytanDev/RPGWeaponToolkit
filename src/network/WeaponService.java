package network;

import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.ArrayList;
import java.util.UUID;

import model.Weapon;

public interface WeaponService extends Remote 
{
    void addWeapon(Weapon weapon) throws RemoteException;

    boolean removeWeapon(UUID id) throws RemoteException;

    Weapon getWeapon(UUID id) throws RemoteException;

    ArrayList<Weapon> getWeapons() throws RemoteException;

    boolean updateWeapon(UUID id, Weapon weapon) throws RemoteException;
}
