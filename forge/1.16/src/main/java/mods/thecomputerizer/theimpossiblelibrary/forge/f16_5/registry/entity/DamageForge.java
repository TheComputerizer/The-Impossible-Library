package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class DamageForge extends DamageAPI {

    private final DamageSource source;

    public DamageForge(DamageSource source, float amount) {
        super(Objects.nonNull(source.getEntity()) ? new EntityForge(source.getEntity()) : null,amount);
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
