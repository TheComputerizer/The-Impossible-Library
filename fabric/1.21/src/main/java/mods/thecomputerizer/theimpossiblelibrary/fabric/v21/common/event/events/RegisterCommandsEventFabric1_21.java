package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.RegisterCommandsEventFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.server.WrappedCommand1_21;
import net.fabricmc.fabric.api.event.Event;

import static net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback.EVENT;

public class RegisterCommandsEventFabric1_21 extends RegisterCommandsEventFabric {
    
    @Override public Event<?> getEventInstance() {
        return EVENT;
    }
    
    @Override public void registerCommand(CommandAPI command) {
        WrappedCommand1_21.register(this.event[0], command);
    }
}