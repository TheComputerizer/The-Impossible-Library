package mods.thecomputerizer.theimpossiblelibrary.forge.server.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.CommonForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.server.event.events.ServerTickEventForge;

import static mods.thecomputerizer.theimpossiblelibrary.api.server.event.ServerEventWrapper.ServerType.TICK_SERVER;

public interface ServerForgeEventHelper extends CommonForgeEventHelper {
    
    @Override default void defaultEventDefinitions() {
        TICK_SERVER.setConnector(new ServerTickEventForge());
    }
}