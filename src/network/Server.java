package network;

import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;

import management.WeaponManager;
import model.Weapon;

public class Server 
{
    public static void main(String[] args)
    {
        try
        {
            WeaponManager manager = new WeaponManager("data/weapons-server.ser");
            WeaponService service = new WeaponServiceImpl(manager);
            Registry registry = LocateRegistry.createRegistry(1099);
            registry.rebind("WeaponService", service);

            System.out.println("==============================");
            System.out.println("RPG Weapon Toolkit Server");
            System.out.println("Port: 1099");
            System.out.println("Status: Running");
            System.out.println("Weapons loaded: " + manager.getWeapons().size());
            System.out.println("==============================");
        }
        catch (Exception e)
        {
            System.out.println("Server failed to start.");
            e.printStackTrace();
        }
    }
    
}
