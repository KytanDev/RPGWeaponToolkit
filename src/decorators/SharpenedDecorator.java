package decorators;

import model.Weapon;
import model.WeaponStats;

public class SharpenedDecorator extends WeaponDecorator
{
    public SharpenedDecorator(Weapon weapon) {
        super(weapon);
    }
    
    @Override 
    public WeaponStats getStats()
    {
        WeaponStats stats = super.getStats();
        stats.setPhysicalDamage(stats.getPhysicalDamage() + 10);
        return stats;
    }

    @Override
    public String getDescription()
    {
        return super.getDescription() + " | Sharpened";
    }

    @Override 
    public double getValue()
    {
        return super.getValue() + 50;
    }
}