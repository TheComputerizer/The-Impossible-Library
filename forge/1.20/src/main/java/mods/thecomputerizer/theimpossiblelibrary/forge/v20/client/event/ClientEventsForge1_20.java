package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.event.ClientEvents1_20;
import net.minecraftforge.eventbus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEventsForge1_20 extends ClientEvents1_20 {

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventForge1_20());
        CLICK_INPUT.setConnector(new InputClickEventForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventForge1_20());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventForge1_20());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventForge());
        FOG_COLORS.setConnector(new FogColorsEventForge1_20());
        FOG_DENSITY.setConnector(new FogDensityEventForge1_20());
        FOG_RENDER.setConnector(new FogRenderEventForge1_20());
        FOV_MODIFIER.setConnector(new FOVModifierEventForge1_20());
        FOV_UPDATE.setConnector(new FOVUpdateEventForge1_20());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventForge());
        KEY_INPUT.setConnector(new InputKeyEventForge1_20());
        MOUSE_INPUT.setConnector(new InputMouseEventForge());
        MOUSE_RAW.setConnector(new RawMouseEventForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventForge());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventForge());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge1_20());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge1_20());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge1_20());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge1_20());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge1_20());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge1_20());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge1_20());
        SOUND_LOAD.setConnector(new LoadSoundEventForge());
        SOUND_PLAY.setConnector(new PlaySoundEventForge1_20());
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