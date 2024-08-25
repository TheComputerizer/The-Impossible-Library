package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.entity.DamageForge;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class Damage1_16_5 extends DamageForge {

    public Damage1_16_5(DamageSource source, float amount) {
        super(source,Objects.nonNull(source.getEntity()) ? new Entity1_16_5(source.getEntity()) : null,amount);
    }
}
