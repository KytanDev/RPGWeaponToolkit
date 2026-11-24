package decorators;

import model.Weapon;
import model.WeaponStats;

public class LightweightDecorator extends WeaponDecorator
{
    public LightweightDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Lightweight";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setAttackSpeed(stats.getAttackSpeed() * 1.5);
        stats.setStaminaCost(stats.getStaminaCost() * 0.8);
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 175;
    }
}