package mods.thecomputerizer.theimpossiblelibrary.forge.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.*;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.ModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ITEM;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public interface CommonForgeEventHelper {
    
    default void defaultEventDefinitions() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventForge());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventForge());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventForge());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventForge());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventForge());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventForge());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventForge());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventForge());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventForge());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventForge());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventForge());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventForge());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventForge());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventForge());
    }
    
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
