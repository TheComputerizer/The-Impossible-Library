package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.PotentialSpawnsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_POTENTIAL_SPAWNS;

public abstract class PotentialSpawnsEventForge extends PotentialSpawnsEventWrapper<PotentialSpawns> {
    
    @SubscribeEvent
    public static void onEvent(PotentialSpawns event) {
        WORLD_POTENTIAL_SPAWNS.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(PotentialSpawns event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<PotentialSpawns,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(PotentialSpawns::getPos);
    }

    @Override protected EventFieldWrapper<PotentialSpawns,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(PotentialSpawns::getWorld);
    }
}