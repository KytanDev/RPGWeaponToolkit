package decorators;

import model.Weapon;
import model.WeaponStats;

public class VampiricDecorator extends WeaponDecorator
{
    public VampiricDecorator(Weapon weapon) {
        super(weapon);
    }

    @Override
    public String getDescription() {
        return super.getDescription() + " | Vampiric";
    }

    @Override
    public WeaponStats getStats() {
        WeaponStats stats = super.getStats();
        stats.setLifeSteal(stats.getLifeSteal() + 0.08);
        return stats;
    }

    @Override
    public double getValue() {
        return super.getValue() + 200;
    }
}