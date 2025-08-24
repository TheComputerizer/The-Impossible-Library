package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.WorldCreateSpawnPosEventForge;
import net.minecraftforge.event.world.WorldEvent.CreateSpawnPosition;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_CREATE_SPAWN_POS;

public class WorldCreateSpawnPosEventForge1_16_5 extends WorldCreateSpawnPosEventForge<CreateSpawnPosition> {
    
    @SubscribeEvent
    public static void onEvent(CreateSpawnPosition event) {
        WORLD_CREATE_SPAWN_POS.invoke(event);
    }
    
    @Override protected EventFieldWrapper<CreateSpawnPosition,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(CreateSpawnPosition::getWorld);
    }
}