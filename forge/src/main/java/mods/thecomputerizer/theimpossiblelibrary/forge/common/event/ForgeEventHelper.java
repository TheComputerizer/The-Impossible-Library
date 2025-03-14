package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;

import javax.annotation.Nullable;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public interface ForgeEventHelper {
    
    default @Nullable IEventBus getBusFor(@Nullable Object wrapper) {
        if(Objects.isNull(wrapper)) return EVENT_BUS;
        String simpleName = wrapper.getClass().getSimpleName();
        return getBusFor(simpleName.substring(0,simpleName.indexOf("Event")));
    }
    
    default @Nullable IEventBus getBusFor(String simpleClassName) {
        switch(simpleClassName) {
            case "RegisterBlockEntities":
            case "RegisterBlocks":
            case "RegisterEntities":
            case "RegisterItems":
            case "RegisterSounds": return getModBus();
            default: return EVENT_BUS;
        }
    }
    
    default @Nullable IEventBus getModBus() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if(Objects.isNull(container)) {
            TILRef.logWarn("Active ModContainer not found for current context! Assuming context of {}", MODID);
            container = ModList.get().getModContainerById(MODID).orElse(null);
        }
        if(Objects.isNull(container)) {
            TILRef.logError("Failed to get ModContainer! Event bus will not be returned");
            return null;
        }
        return getModBus(container);
    }
    
    @Nullable IEventBus getModBus(ModContainer container);
    
    default <E extends EventWrapper<?>> void registerForgeOrModBus(E wrapper) {
        IEventBus bus = getBusFor(wrapper);
        if(Objects.nonNull(bus)) bus.register(wrapper.getClass());
        else TILRef.logError("Unable to find an event bus to register event wrapper {}",wrapper);
    }
}
