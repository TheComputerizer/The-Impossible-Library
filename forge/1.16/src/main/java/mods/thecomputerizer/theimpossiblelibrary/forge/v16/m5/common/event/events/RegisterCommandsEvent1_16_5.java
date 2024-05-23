package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterCommandsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.server.WrappedCommand1_16_5;
import net.minecraftforge.event.RegisterCommandsEvent;

public class RegisterCommandsEvent1_16_5 extends RegisterCommandsEventWrapper<RegisterCommandsEvent> {
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(RegisterCommandsEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override
    public void registerCommand(CommandAPI command) {
        WrappedCommand1_16_5.register(getEvent().getDispatcher(),command);
    }
}
