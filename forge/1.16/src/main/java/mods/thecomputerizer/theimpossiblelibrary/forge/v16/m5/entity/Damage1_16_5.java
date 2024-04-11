package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class Damage1_16_5 extends DamageAPI {

    private final DamageSource source;

    public Damage1_16_5(DamageSource source, float amount) {
        super(Objects.nonNull(source.getEntity()) ? new Entity1_16_5(source.getEntity()) : null,amount);
        this.source = source;
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
