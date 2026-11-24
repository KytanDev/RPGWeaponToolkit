package model;
import java.io.Serializable;

public class WeaponStats implements Serializable
{
    double physicalDamage;
    double fireDamage;
    double frostDamage;
    double attackSpeed;
    double criticalChance;
    double criticalMultiplier;
    double armourPenetration;
    double lifeSteal;
    int durability;
    double staminaCost;
    private static final long serialVersionUID = 1L;

    public WeaponStats(double armourPenetration, double attackSpeed, double criticalChance, double criticalMultiplier, int durability, double fireDamage, double frostDamage, double lifeSteal, double physicalDamage, double staminaCost) {
        this.armourPenetration = armourPenetration;
        this.attackSpeed = attackSpeed;
        this.criticalChance = criticalChance;
        this.criticalMultiplier = criticalMultiplier;
        this.durability = durability;
        this.fireDamage = fireDamage;
        this.frostDamage = frostDamage;
        this.lifeSteal = lifeSteal;
        this.physicalDamage = physicalDamage;
        this.staminaCost = staminaCost;
    }

    // Copy constructor
    public WeaponStats(WeaponStats other)
    {
        this.armourPenetration = other.armourPenetration;
        this.attackSpeed = other.attackSpeed;
        this.criticalChance = other.criticalChance;
        this.criticalMultiplier = other.criticalMultiplier;
        this.durability = other.durability;
        this.fireDamage = other.fireDamage;
        this.frostDamage = other.frostDamage;
        this.lifeSteal = other.lifeSteal;
        this.physicalDamage = other.physicalDamage;
        this.staminaCost = other.staminaCost;
    }
    
    public double getPhysicalDamage() {
        return physicalDamage;
    }
    public void setPhysicalDamage(double physicalDamage) {
        this.physicalDamage = physicalDamage;
    }
    public double getFireDamage() {
        return fireDamage;
    }
    public void setFireDamage(double fireDamage) {
        this.fireDamage = fireDamage;
    }
    public double getFrostDamage() {
        return frostDamage;
    }
    public void setFrostDamage(double frostDamage) {
        this.frostDamage = frostDamage;
    }
    public double getAttackSpeed() {
        return attackSpeed;
    }
    public void setAttackSpeed(double attackSpeed) {
        this.attackSpeed = attackSpeed;
    }
    public double getCriticalChance() {
        return criticalChance;
    }
    public void setCriticalChance(double criticalChance) {
        this.criticalChance = criticalChance;
    }
    public double getCriticalMultiplier() {
        return criticalMultiplier;
    }
    public void setCriticalMultiplier(double criticalMultiplier) {
        this.criticalMultiplier = criticalMultiplier;
    }
    public double getArmourPenetration() {
        return armourPenetration;
    }
    public void setArmourPenetration(double armourPenetration) {
        this.armourPenetration = armourPenetration;
    }
    public double getLifeSteal() {
        return lifeSteal;
    }
    public void setLifeSteal(double lifeSteal) {
        this.lifeSteal = lifeSteal;
    }
    public int getDurability() {
        return durability;
    }
    public void setDurability(int durability) {
        this.durability = durability;
    }
    public double getStaminaCost() {
        return staminaCost;
    }
    public void setStaminaCost(double staminaCost) {
        this.staminaCost = staminaCost;
    }


}
