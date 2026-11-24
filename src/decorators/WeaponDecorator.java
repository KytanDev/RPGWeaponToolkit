package decorators;
import java.util.UUID;

import model.Weapon;
import model.WeaponStats;
import model.WeaponType;

public abstract class WeaponDecorator implements Weapon
{
    protected final Weapon weapon;

    public WeaponDecorator(Weapon weapon) {
        this.weapon = weapon;
    }

    public Weapon getWrappedWeapon()
    {
        return weapon;
    }

    @Override
    public String getDescription() {
        return weapon.getDescription();
    }

    @Override
    public UUID getId() {
        return weapon.getId();
    }

    @Override
    public String getName() {
        return weapon.getName();
    }

    @Override
    public WeaponStats getStats() {
        return weapon.getStats();
    }

    @Override
    public WeaponType getType() {
        return weapon.getType();
    }

    @Override
    public double getValue() {
        return weapon.getValue();
    }
}
