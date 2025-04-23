package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.common.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.CommonEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m1.common.event.events.RegisterCommandsEventForge1_20_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

@SuppressWarnings("unused") public class CommonEventsForge1_20_1 extends CommonEventsForge1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_20_1());
        super.defineEvents();
    }
}