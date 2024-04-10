package mods.thecomputerizer.theimpossiblelibrary.legacy.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.entity.DamageAPI;
import net.minecraft.util.DamageSource;

import java.util.Objects;

public class DamageLegacy extends DamageAPI {

    private final DamageSource source;

    public DamageLegacy(DamageSource source, float amount) {
        super(Objects.nonNull(source.getTrueSource()) ? new EntityLegacy(source.getTrueSource()) : null,amount);
        this.source = source;
    }

    @Override
    public String getName() {
        return this.source.damageType;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <S> S getSourceObject() {
        return (S)this.source;
    }
}
