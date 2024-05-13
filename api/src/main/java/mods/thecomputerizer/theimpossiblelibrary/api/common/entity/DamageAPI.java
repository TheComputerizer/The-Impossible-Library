package mods.thecomputerizer.theimpossiblelibrary.api.common.entity;

import lombok.Getter;
import lombok.Setter;

import javax.annotation.Nullable;

@SuppressWarnings("unused") @Getter
public abstract class DamageAPI {

    private final EntityAPI<?,?> entity;
    @Setter private float amount;

    protected DamageAPI(@Nullable EntityAPI<?,?> entity, float amount) {
        this.entity = entity;
        this.amount = amount;
    }

    public abstract String getName();

    public abstract <S> S getSourceObject();
}