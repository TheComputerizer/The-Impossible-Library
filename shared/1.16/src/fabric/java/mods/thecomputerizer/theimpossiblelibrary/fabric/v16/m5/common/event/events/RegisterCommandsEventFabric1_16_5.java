package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterCommandsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.server.WrappedCommand1_16_5;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT;

public class RegisterCommandsEventFabric1_16_5 extends RegisterCommandsEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return EVENT;
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_16_5.register(this.event[0],command);
    }
}