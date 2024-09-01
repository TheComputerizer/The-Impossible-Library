package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class Damage1_16_5 extends DamageAPI {
    
    private final DamageSource source;

    public Damage1_16_5(Object source, float amount) {
        super(Objects.nonNull(((DamageSource)source).getEntity()) ? new Entity1_16_5(((DamageSource)source).getEntity()) : null, amount);
        this.source = (DamageSource)source;
    }
    
    @Override
    public String getName() {
        return this.source.msgId;
    }
    
    @SuppressWarnings("unchecked")
    @Override
    public <S> S getSourceObject() {
        return (S)this.source;
    }
}
