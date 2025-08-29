package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.world.damagesource.DamageSource;

public class Damage1_20 extends DamageAPI<DamageSource> {

    public Damage1_20(Object source, float amount) {
        super(source,DamageSource::getEntity,amount);
    }
    
    @Override public String getName() {
        return getIfNotNull(DamageSource::getMsgId);
    }
}