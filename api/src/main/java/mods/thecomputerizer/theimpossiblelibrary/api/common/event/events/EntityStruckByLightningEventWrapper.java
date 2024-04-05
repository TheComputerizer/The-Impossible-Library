package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_SMITTEN;

public abstract class EntityStruckByLightningEventWrapper<E> extends CommonEventWrapper<E> {

    protected EntityStruckByLightningEventWrapper() {
        super(ENTITY_SMITTEN);
    }

    @Override
    protected void populate() {

    }
}