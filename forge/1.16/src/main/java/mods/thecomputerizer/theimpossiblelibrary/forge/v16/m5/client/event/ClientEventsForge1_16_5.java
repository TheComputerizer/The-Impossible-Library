package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.event.ClientEvents1_16_5;
import net.minecraftforge.eventbus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEventsForge1_16_5 extends ClientEvents1_16_5 {

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventForge());
        CLICK_INPUT.setConnector(new InputClickEventForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventForge());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventForge());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventForge());
        FOG_COLORS.setConnector(new FogColorsEventForge());
        FOG_DENSITY.setConnector(new FogDensityEventForge());
        FOG_RENDER.setConnector(new FogRenderEventForge());
        FOV_MODIFIER.setConnector(new FOVModifierEventForge());
        FOV_UPDATE.setConnector(new FOVUpdateEventForge());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventForge());
        KEY_INPUT.setConnector(new InputKeyEventForge());
        MOUSE_INPUT.setConnector(new InputMouseEventForge());
        MOUSE_RAW.setConnector(new RawMouseEventForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventForge());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventForge());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge());
        SOUND_LOAD.setConnector(new LoadSoundEventForge());
        SOUND_PLAY.setConnector(new PlaySoundEventForge());
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