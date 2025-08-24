package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.CommonEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.LivingAttackedEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.LivingDamageEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.LivingDeathEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.LivingHurtEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.LootingLevelEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.PlayerTickEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.RegisterCommandsEventNeoForge1_20_4;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.common.event.events.WorldTickEventNeoForge1_20_4;
import net.neoforged.bus.api.Event;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.ModList;
import net.neoforged.fml.ModLoadingContext;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class CommonEventsNeoForge1_20_4 extends CommonEventsNeoForge1_20 {

    @Override public void defineEvents() {
        LIVING_ATTACKED.setConnector(new LivingAttackedEventNeoForge1_20_4());
        LIVING_DAMAGE.setConnector(new LivingDamageEventNeoForge1_20_4());
        LIVING_DEATH.setConnector(new LivingDeathEventNeoForge1_20_4());
        LIVING_HURT.setConnector(new LivingHurtEventNeoForge1_20_4());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventNeoForge1_20_4());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventNeoForge1_20_4());
        TICK_PLAYER.setConnector(new PlayerTickEventNeoForge1_20_4());
        TICK_WORLD.setConnector(new WorldTickEventNeoForge1_20_4());
        super.defineEvents();
    }
    
    @Nullable IEventBus getBusFor(@Nullable Object wrapper) {
        if(Objects.isNull(wrapper)) return EVENT_BUS;
        String simpleName = wrapper.getClass().getSimpleName();
        return getBusFor(simpleName.substring(0,simpleName.indexOf("Event")));
    }
    
    @Nullable IEventBus getBusFor(String simpleClassName) {
        return switch(simpleClassName) {
            case "RegisterBlockEntities", "RegisterBlocks", "RegisterEntities", "RegisterItems", "RegisterSounds" ->
                    getModBus();
            default -> EVENT_BUS;
        };
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Nullable IEventBus getModBus() {
        ModContainer container = ModLoadingContext.get().getActiveContainer();
        if(Objects.isNull(container)) {
            TILRef.logWarn("Active ModContainer not found for current context! Assuming context of {}", MODID);
            container = ModList.get().getModContainerById(MODID).orElse(null);
        }
        if(Objects.isNull(container)) {
            TILRef.logError("Failed to get ModContainer! Event bus will not be returned");
            return null;
        }
        return container.getEventBus();
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        IEventBus bus = getBusFor(wrapper);
        if(Objects.nonNull(bus)) bus.register(wrapper.getClass());
        else TILRef.logError("Unable to find an event bus to register event wrapper {}",wrapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}