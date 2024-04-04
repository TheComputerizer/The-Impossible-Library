package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.DamageAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.LIVING_DEATH;

@Getter
public abstract class LivingDeathEventWrapper<E> extends LivingEventWrapper<E> {

    protected DamageAPI damage;

    protected LivingDeathEventWrapper() {
        super(LIVING_DEATH);
    }
}