package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event.ClientEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m4.client.event.events.ClientTickEventNeoForge1_20_4;
import net.neoforged.bus.api.Event;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.gui.overlay.VanillaGuiOverlay;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ClientEventsNeoForge1_20_4 extends ClientEventsNeoForge1_20 {
    
    @Override protected void defineExtendedEvents() {
        super.defineExtendedEvents();
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge1_20_4());
    }
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        return switch((RenderBlockScreenEffectEvent.OverlayType)blockType) {
            case FIRE -> OverlayType.FIRE;
            case WATER -> OverlayType.WATER;
            default -> OverlayType.BLOCK;
        };
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        return switch(((VanillaGuiOverlay)elementType)) {
            case AIR_LEVEL -> OverlayType.AIR;
            case ARMOR_LEVEL -> OverlayType.ARMOR;
            case BOSS_EVENT_PROGRESS -> OverlayType.BOSSINFO;
            case CHAT_PANEL -> OverlayType.CHAT;
            case CROSSHAIR -> OverlayType.CROSSHAIRS;
            case DEBUG_SCREEN -> OverlayType.DEBUG;
            case EXPERIENCE_BAR -> OverlayType.EXPERIENCE;
            case FOOD_LEVEL -> OverlayType.FOOD;
            case HELMET -> OverlayType.HELMET;
            case HOTBAR -> OverlayType.HOTBAR;
            case JUMP_BAR -> OverlayType.JUMPBAR;
            case PLAYER_HEALTH -> OverlayType.HEALTH;
            case PLAYER_LIST -> OverlayType.PLAYER_LIST;
            case PORTAL -> OverlayType.PORTAL;
            case POTION_ICONS -> OverlayType.POTION_ICONS;
            case SUBTITLES -> OverlayType.SUBTITLES;
            case TITLE_TEXT -> OverlayType.TEXT;
            case VIGNETTE -> OverlayType.VIGNETTE;
            default -> OverlayType.ALL;
        };
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==net.neoforged.bus.api.Event.Result.DEFAULT ? DEFAULT : (result==net.neoforged.bus.api.Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public net.neoforged.bus.api.Event.Result setEventResult(Result result) {
        return result==DEFAULT ? net.neoforged.bus.api.Event.Result.DEFAULT : (result==DENY ? net.neoforged.bus.api.Event.Result.DENY : Event.Result.ALLOW);
    }
}