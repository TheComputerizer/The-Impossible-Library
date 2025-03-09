package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.CommonEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.event.events.RegisterCommandsEventForge1_20_6;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

@SuppressWarnings("unused") public class CommonEventsForge1_20_6 extends CommonEventsForge1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_20_6());
        super.defineEvents();
    }
}