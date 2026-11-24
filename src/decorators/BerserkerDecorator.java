package decorators;

import model.Weapon;
import model.WeaponStats;

public class BerserkerDecorator extends WeaponDecorator
{
    public BerserkerDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Berserker";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setPhysicalDamage(stats.getPhysicalDamage() * 1.5);
        stats.setDurability(Math.round(stats.getDurability() * 0.75f));
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 175;
    }
}