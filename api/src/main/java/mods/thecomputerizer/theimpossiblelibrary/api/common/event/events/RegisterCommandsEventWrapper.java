package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public abstract class RegisterCommandsEventWrapper<E> extends CommonEventWrapper<E> {

    protected RegisterCommandsEventWrapper() {
        super(REGISTER_COMMANDS);
    }

    public abstract void registerCommand(CommandAPI command);

    @Override
    public void populate() {}
}
