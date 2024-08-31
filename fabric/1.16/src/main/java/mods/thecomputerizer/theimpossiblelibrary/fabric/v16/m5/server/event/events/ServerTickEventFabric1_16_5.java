package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.server.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase;
import mods.thecomputerizer.theimpossiblelibrary.api.server.event.events.ServerTickEventWrapper;
import net.minecraft.server.MinecraftServer;

import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonTickableEventType.TickPhase.END;

public class ServerTickEventFabric1_16_5 extends ServerTickEventWrapper<Supplier<MinecraftServer>> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Supplier<MinecraftServer> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }

    @Override
    protected TickPhase wrapTickPhase() {
        return Objects.nonNull(this.event) ? END : DEFAULT;
    }
}