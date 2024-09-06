package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.DamageAPI;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class Damage1_12_2 extends DamageAPI {

    private final DamageSource source;

    public Damage1_12_2(DamageSource source, float amount) {
        super(Objects.nonNull(source.getTrueSource()) ? WrapperHelper.wrapEntity(source.getTrueSource()) : null, amount);
        this.source = source;
    }

    @Override public String getName() {
        return this.source.damageType;
    }

    @SuppressWarnings("unchecked")
    @Override public <S> S getSourceObject() {
        return (S)this.source;
    }
}