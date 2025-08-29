package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.util.DamageSource;

public class Damage1_12_2 extends DamageAPI<DamageSource> {

    public Damage1_12_2(Object source, float amount) {
        super(source,DamageSource::getTrueSource,amount);
    }

    @Override public String getName() {
        return getIfNotNull(source -> source.damageType);
    }
}