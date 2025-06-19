package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.event.events.ClientTickEventNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.event.ClientEvents1_21;
import net.neoforged.neoforge.client.event.RenderBlockScreenEffectEvent;
import net.neoforged.neoforge.client.gui.VanillaGuiLayers;
import net.neoforged.neoforge.common.util.TriState;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.ALL;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.CHAT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.EXPERIENCE;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.FOOD;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.HEALTH;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.HOTBAR;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.JUMPBAR;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.PLAYER_LIST;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.POTION_ICONS;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.SUBTITLES;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType.TEXT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.*;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.SUBTITLE_OVERLAY;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.TAB_LIST;
import static net.neoforged.neoforge.client.gui.VanillaGuiLayers.TITLE;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ClientEventsNeoForge1_21 extends ClientEvents1_21 {

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventNeoForge());
        CLICK_INPUT.setConnector(new InputClickEventNeoForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventNeoForge());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventNeoForge());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventNeoForge());
        FOG_COLORS.setConnector(new FogColorsEventNeoForge());
        FOG_DENSITY.setConnector(new FogDensityEventNeoForge());
        FOG_RENDER.setConnector(new FogRenderEventNeoForge());
        FOV_MODIFIER.setConnector(new FOVModifierEventNeoForge());
        FOV_UPDATE.setConnector(new FOVUpdateEventNeoForge());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventNeoForge());
        KEY_INPUT.setConnector(new InputKeyEventNeoForge());
        MOUSE_INPUT.setConnector(new InputMouseEventNeoForge());
        MOUSE_RAW.setConnector(new RawMouseEventNeoForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventNeoForge());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventNeoForge());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventNeoForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventNeoForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventNeoForge());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventNeoForge());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventNeoForge());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventNeoForge());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventNeoForge());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventNeoForge());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventNeoForge());
        SOUND_LOAD.setConnector(new LoadSoundEventNeoForge());
        SOUND_PLAY.setConnector(new PlaySoundEventNeoForge());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEventNeoForge());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEventNeoForge());
        SOUND_SETUP.setConnector(new SoundSetupEventNeoForge());
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge1_21());
        TICK_RENDER.setConnector(new RenderTickEventNeoForge());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==TriState.DEFAULT ? DEFAULT : (result==TriState.FALSE ? DENY : ALLOW);
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
        if(elementType==VanillaGuiLayers.CHAT) return CHAT;
        if(elementType==CROSSHAIR) return CROSSHAIRS;
        if(elementType==DEBUG_OVERLAY) return DEBUG;
        if(elementType==EFFECTS) return POTION_ICONS;
        if(elementType==EXPERIENCE_BAR) return EXPERIENCE;
        if(elementType==EXPERIENCE_LEVEL) return EXPERIENCE;
        if(elementType==FOOD_LEVEL) return FOOD;
        if(elementType==VanillaGuiLayers.HOTBAR) return HOTBAR;
        if(elementType==JUMP_METER) return JUMPBAR;
        if(elementType==PLAYER_HEALTH) return HEALTH;
        if(elementType==SUBTITLE_OVERLAY) return SUBTITLES;
        if(elementType==TAB_LIST) return PLAYER_LIST;
        if(elementType==TITLE) return TEXT;
        return ALL;
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