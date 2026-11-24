package model;
import java.util.*;

public class BaseWeapon implements Weapon
{
    private UUID id;
    private String name;   
    private String description;
    private WeaponType type;
    private WeaponStats stats;
    private double value;
    private static final long serialVersionUID = 1L;

    public BaseWeapon(String description, String name, WeaponStats stats, WeaponType type, double value)
    {
        this(UUID.randomUUID(), description, name, stats, type, value);
    }

    public BaseWeapon(UUID id, String description, String name, WeaponStats stats, WeaponType type, double value) 
    {
        this.id = id;
        this.description = description;
        this.name = name;
        this.stats = new WeaponStats(stats);
        this.type = type;
        this.value = value;

    }

    @Override
    public UUID getId() {
        return id;
    }
    @Override
    public String getName() {
        return name;
    }
    @Override
    public String getDescription() {
        return description;
    }
    @Override
    public WeaponType getType() {
        return type;
    }
    @Override
    public WeaponStats getStats() {
        return new WeaponStats(stats);
    }
    @Override
    public double getValue() {
        return value;
    }
    
}
