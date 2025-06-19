package mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v21.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.event.ClientEvents1_21;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.eventbus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEventsForge1_21 extends ClientEvents1_21 {

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventForge1_21());
        CLICK_INPUT.setConnector(new InputClickEventForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventForge1_21());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventForge1_21());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventForge1_21());
        FOG_COLORS.setConnector(new FogColorsEventForge1_21());
        FOG_DENSITY.setConnector(new FogDensityEventForge1_21());
        FOG_RENDER.setConnector(new FogRenderEventForge1_21());
        FOV_MODIFIER.setConnector(new FOVModifierEventForge1_21());
        FOV_UPDATE.setConnector(new FOVUpdateEventForge1_21());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventForge());
        KEY_INPUT.setConnector(new InputKeyEventForge1_21());
        MOUSE_INPUT.setConnector(new InputMouseEventForge());
        MOUSE_RAW.setConnector(new RawMouseEventForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventForge());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventForge1_21());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge1_21());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge1_21());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge1_21());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge1_21());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge1_21());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge1_21());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge1_21());
        SOUND_LOAD.setConnector(new LoadSoundEventForge());
        SOUND_PLAY.setConnector(new PlaySoundEventForge1_21());
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
        return switch((RenderBlockScreenEffectEvent.OverlayType)blockType) {
            case FIRE -> OverlayType.FIRE;
            case WATER -> OverlayType.WATER;
            default -> OverlayType.BLOCK;
        };
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}