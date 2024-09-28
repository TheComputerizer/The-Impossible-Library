package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterCommandsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.custom.RegisterCommands1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.server.WrappedCommand1_12_2;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public class RegisterCommandsEvent1_12_2 extends RegisterCommandsEventWrapper<RegisterCommands1_12_2> {

    @SubscribeEvent
    public static void onEvent(RegisterCommands1_12_2 event) {
        REGISTER_COMMANDS.invoke(event);
    }

    @Override public void registerCommand(CommandAPI command) {
        getEvent().registerCommand(new WrappedCommand1_12_2(command));
    }
}
