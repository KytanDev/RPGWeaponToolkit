package decorators;

import model.Weapon;
import model.WeaponStats;

public class FlameDecorator extends WeaponDecorator
{
    public FlameDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Flame";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setFireDamage(stats.getFireDamage() + 15);
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 150;
    }
}