package model;

import java.io.Serializable;
import java.util.*;

public interface Weapon extends Serializable
{
    UUID getId();
    String getName();
    String getDescription();
    WeaponType getType();
    WeaponStats getStats();
    double getValue();
}
