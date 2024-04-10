package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonWorldEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_CREATE_SPAWN_POS;

public abstract class WorldCreateSpawnPosEventWrapper<E> extends CommonWorldEventType<E> {

    protected EventFieldWrapper<E,WorldSettingsAPI<?>> settings;

    protected WorldCreateSpawnPosEventWrapper() {
        super(WORLD_CREATE_SPAWN_POS);
    }

    public WorldSettingsAPI<?> getSettings() {
        return this.settings.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.settings = wrapSettingsField();
    }

    protected abstract EventFieldWrapper<E,WorldSettingsAPI<?>> wrapSettingsField();
}