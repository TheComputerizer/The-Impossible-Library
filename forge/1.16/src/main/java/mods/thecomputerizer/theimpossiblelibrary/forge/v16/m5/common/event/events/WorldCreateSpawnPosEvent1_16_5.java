package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldCreateSpawnPosEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldSettingsAPI;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;

public class WorldCreateSpawnPosEvent1_16_5 extends WorldCreateSpawnPosEventWrapper<CreateSpawnPosition> {

    @Override
    protected EventFieldWrapper<CreateSpawnPosition,WorldSettingsAPI<?>> wrapSettingsField() { //TODO Implement this
        return wrapGenericGetter(event -> null,null);
    }

    @Override
    protected EventFieldWrapper<CreateSpawnPosition,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateSpawnPosition::getWorld);
    }
}