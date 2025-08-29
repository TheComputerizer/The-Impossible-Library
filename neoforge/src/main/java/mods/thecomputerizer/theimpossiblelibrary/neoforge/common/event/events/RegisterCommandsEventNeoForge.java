package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterCommandsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.server.CommandAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;

import java.util.Objects;
import java.util.function.BiConsumer;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_COMMANDS;

public abstract class RegisterCommandsEventNeoForge extends RegisterCommandsEventWrapper<RegisterCommandsEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterCommandsEvent event) {
        REGISTER_COMMANDS.invoke(event);
    }
    
    private final BiConsumer<Object,CommandAPI> registerFunc;
    
    protected RegisterCommandsEventNeoForge(BiConsumer<Object,CommandAPI> registerFunc) {
        this.registerFunc = registerFunc;
    }
    
    @Override public final void registerCommand(CommandAPI command) {
        if(Objects.nonNull(this.registerFunc)) this.registerFunc.accept(getEvent().getDispatcher(),command);
        else TILRef.logError("Failed to register command due to null register function??");
    }
    
    @Override public void setEvent(RegisterCommandsEvent event) {
        super.setEvent(event);
    }
}
