package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.world.damagesource.DamageSource;

public class Damage1_16_5 extends DamageAPI<DamageSource> {

    public Damage1_16_5(Object source, float amount) {
        super(source,DamageSource::getEntity,amount);
    }
    
    @Override public String getName() {
        return getIfNotNull(source -> source.msgId);
    }
}