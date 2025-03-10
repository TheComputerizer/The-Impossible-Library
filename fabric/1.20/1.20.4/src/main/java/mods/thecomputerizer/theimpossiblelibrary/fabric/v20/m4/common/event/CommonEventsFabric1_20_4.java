package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.CommonEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m4.common.event.events.RegisterCommandsEventFabric1_20_4;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class CommonEventsFabric1_20_4 extends CommonEventsFabric1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventFabric1_20_4());
        super.defineEvents();
    }
}