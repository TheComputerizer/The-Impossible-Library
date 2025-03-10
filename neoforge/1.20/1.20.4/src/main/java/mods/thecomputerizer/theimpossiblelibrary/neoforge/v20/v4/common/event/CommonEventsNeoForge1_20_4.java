package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.CommonEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.v4.common.event.events.RegisterCommandsEventNeoForge1_20_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public class CommonEventsNeoForge1_20_4 extends CommonEventsNeoForge1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventNeoForge1_20_4());
        super.defineEvents();
    }
}