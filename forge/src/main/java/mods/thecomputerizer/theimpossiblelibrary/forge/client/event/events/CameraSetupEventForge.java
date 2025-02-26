package mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.CameraSetupEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import net.minecraftforge.eventbus.api.Event;

public abstract class CameraSetupEventForge<E extends Event> extends CameraSetupEventWrapper<E> {
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(E event) {
        super.setEvent(event);
        setCanceled(this.event.isCanceled());
    }

    @Override protected EventFieldWrapper<E,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(event -> null);
    }
}