package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.WorldUnloadEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraftforge.event.world.WorldEvent.Unload;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.WORLD_UNLOAD;

public class WorldUnloadEvent1_12_2 extends WorldUnloadEventWrapper<Unload> {

    @SubscribeEvent
    public static void onEvent(Unload event) {
        WORLD_UNLOAD.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Unload event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override protected EventFieldWrapper<Unload,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Unload::getWorld);
    }
}