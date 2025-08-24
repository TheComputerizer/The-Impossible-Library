package mods.thecomputerizer.theimpossiblelibrary.fabric.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterCommandsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.server.WrappedCommand1_18_2;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.command.v1.CommandRegistrationCallback.EVENT;

public class RegisterCommandsEventFabric1_18_2 extends RegisterCommandsEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return EVENT;
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_18_2.register(this.event[0],command);
    }
}