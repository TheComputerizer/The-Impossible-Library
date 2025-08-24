package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterCommandsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.server.WrappedCommand1_20_4;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.EVENT;

public class RegisterCommandsEventFabric1_20_4 extends RegisterCommandsEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return EVENT;
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_20_4.register(this.event[0], command);
    }
}