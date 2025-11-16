package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.CommonEventsForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.common.event.events.PlayerAdvancementEventForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.m4.common.event.events.RegisterCommandsEventForge1_20_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class CommonEventsForge1_20_4 extends CommonEventsForge1_20 {
    
    @Override public void defineVersionedEvents() {
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventForge1_20_4());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_20_4());
        super.defineVersionedEvents();
    }
}