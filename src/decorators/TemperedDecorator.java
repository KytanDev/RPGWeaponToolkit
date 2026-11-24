package decorators;

import model.Weapon;
import model.WeaponStats;

public class TemperedDecorator extends WeaponDecorator
{
    public TemperedDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Tempered";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setPhysicalDamage(stats.getPhysicalDamage() * 1.25);
        stats.setDurability(stats.getDurability() + 20);
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 100;
    }
    
    

}