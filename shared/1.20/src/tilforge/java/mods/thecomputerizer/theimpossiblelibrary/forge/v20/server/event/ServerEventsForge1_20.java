package mods.thecomputerizer.theimpossiblelibrary.forge.v20.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.server.event.ServerForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.server.event.ServerEvents1_20;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ServerEventsForge1_20 extends ServerEvents1_20 implements ServerForgeEventHelper {
    
    @Override public void defineEvents() {
        defaultEventDefinitions();
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override @Nullable public IEventBus getModBus(ModContainer container) {
        return container instanceof FMLModContainer ? ((FMLModContainer)container).getEventBus() : null;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        registerForgeOrModBus(wrapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}
