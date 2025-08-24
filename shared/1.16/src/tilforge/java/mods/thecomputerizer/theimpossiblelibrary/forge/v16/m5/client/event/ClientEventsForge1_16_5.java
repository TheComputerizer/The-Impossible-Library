package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.ClientForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;


import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEventsForge1_16_5 extends ClientEvents1_16_5 implements ClientForgeEventHelper {

    @Override public void defineEvents() {
        defaultEventDefinitions();
        CAMERA_SETUP.setConnector(new CameraSetupEventForge1_16_5());
        CLICK_INPUT.setConnector(new InputClickEventForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventForge());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventForge());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventForge1_16_5());
        FOG_COLORS.setConnector(new FogColorsEventForge1_16_5());
        FOG_DENSITY.setConnector(new FogDensityEventForge1_16_5());
        FOG_RENDER.setConnector(new FogRenderEventForge1_16_5());
        FOV_MODIFIER.setConnector(new FOVModifierEventForge1_16_5());
        FOV_UPDATE.setConnector(new FOVUpdateEventForge1_16_5());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventForge());
        KEY_INPUT.setConnector(new InputKeyEventForge1_16_5());
        MOUSE_INPUT.setConnector(new InputMouseEventForge());
        MOUSE_RAW.setConnector(new RawMouseEventForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge1_16_5());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge1_16_5());
        SOUND_LOAD.setConnector(new LoadSoundEventForge());
        SOUND_PLAY.setConnector(new PlaySoundEventForge1_16_5());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEventForge());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEventForge());
        SOUND_SETUP.setConnector(new SoundSetupEventForge());
        TICK_CLIENT.setConnector(new ClientTickEventForge());
        TICK_RENDER.setConnector(new RenderTickEventForge());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        switch((RenderBlockOverlayEvent.OverlayType)blockType) {
            case FIRE: return OverlayType.FIRE;
            case WATER: return OverlayType.WATER;
            default: return OverlayType.BLOCK;
        }
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        switch((ElementType)elementType) {
            case AIR: return OverlayType.AIR;
            case ARMOR: return OverlayType.ARMOR;
            case BOSSHEALTH: return OverlayType.BOSSHEALTH;
            case BOSSINFO: return OverlayType.BOSSINFO;
            case CHAT: return OverlayType.CHAT;
            case CROSSHAIRS: return OverlayType.CROSSHAIRS;
            case DEBUG: return OverlayType.DEBUG;
            case EXPERIENCE: return OverlayType.EXPERIENCE;
            case FOOD: return OverlayType.FOOD;
            case FPS_GRAPH: return OverlayType.FPS_GRAPH;
            case HEALTH: return OverlayType.HEALTH;
            case HEALTHMOUNT: return OverlayType.HEALTHMOUNT;
            case HELMET: return OverlayType.HELMET;
            case HOTBAR: return OverlayType.HOTBAR;
            case JUMPBAR: return OverlayType.JUMPBAR;
            case PLAYER_LIST: return OverlayType.PLAYER_LIST;
            case PORTAL: return OverlayType.PORTAL;
            case POTION_ICONS: return OverlayType.POTION_ICONS;
            case SUBTITLES: return OverlayType.SUBTITLES;
            case TEXT: return OverlayType.TEXT;
            case VIGNETTE: return OverlayType.VIGNETTE;
            default: return OverlayType.ALL;
        }
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