package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.event;

import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.common.event.CommonEventsFabric1_20;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v20.m1.event.events.RegisterCommandsEventFabric1_20_1;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public class CommonEventsFabric1_20_1 extends CommonEventsFabric1_20 {

    @Override public void defineEvents() {
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventFabric1_20_1());
        super.defineEvents();
    }
}