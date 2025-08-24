package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event.ClientEventsNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events.ClientTickEventNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events.RenderOverlayBossEventNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events.RenderOverlayChatEventNeoForge1_20_6;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.m6.client.event.events.RenderOverlayTextEventNeoForge1_20_6;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.common.util.TriState;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.TICK_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.*;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.CHAT;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.HOTBAR;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ClientEventsNeoForge1_20_6 extends ClientEventsNeoForge1_20 {
    
    @Override protected void defineExtendedEvents() {
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventNeoForge1_20_6());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventNeoForge1_20_6());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventNeoForge1_20_6());
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge1_20_6());
    }
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        return switch((RenderBlockScreenEffectEvent.OverlayType)blockType) {
            case FIRE -> OverlayType.FIRE;
            case WATER -> OverlayType.WATER;
            default -> OverlayType.BLOCK;
        };
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        if(elementType==AIR_LEVEL) return AIR;
        if(elementType==ARMOR_LEVEL) return ARMOR;
        if(elementType==BOSS_OVERLAY) return BOSSINFO;
        if(elementType==CHAT) return OverlayType.CHAT;
        if(elementType==CROSSHAIR) return CROSSHAIRS;
        if(elementType==DEBUG_OVERLAY) return DEBUG;
        if(elementType==EFFECTS) return POTION_ICONS;
        if(elementType==EXPERIENCE_BAR) return EXPERIENCE;
        if(elementType==EXPERIENCE_LEVEL) return EXPERIENCE;
        if(elementType==FOOD_LEVEL) return FOOD;
        if(elementType==HOTBAR) return OverlayType.HOTBAR;
        if(elementType==JUMP_METER) return JUMPBAR;
        if(elementType==PLAYER_HEALTH) return HEALTH;
        if(elementType==SUBTITLE_OVERLAY) return SUBTITLES;
        if(elementType==TAB_LIST) return PLAYER_LIST;
        if(elementType==TITLE) return TEXT;
        return ALL;
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==TriState.DEFAULT ? DEFAULT : (result==TriState.FALSE ? DENY : ALLOW);
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public TriState setEventResult(Result result) {
        return result==DEFAULT ? TriState.DEFAULT : (result==DENY ? TriState.FALSE : TriState.TRUE);
    }
}