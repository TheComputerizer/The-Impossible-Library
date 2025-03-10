package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.event.ClientEvents1_20;
import net.neoforged.bus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

public class ClientEventsNeoForge1_20 extends ClientEvents1_20 {

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
        TICK_CLIENT.setConnector(new ClientTickEventNeoForge());
        TICK_RENDER.setConnector(new RenderTickEventNeoForge());
        super.defineEvents();
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