package decorators;

import model.Weapon;
import model.WeaponStats;

public class FrostDecorator extends WeaponDecorator
{
    public FrostDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Frost";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setFrostDamage(stats.getFrostDamage() + 12);
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 120;
    }
}