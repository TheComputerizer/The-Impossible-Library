package mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v21.client.event.events.ClientTickEventNeoForge1_21;
import mods.thecomputerizer.theimpossiblelibrary.shared.v21.client.event.ClientEvents1_21;
import net.neoforged.neoforge.common.util.TriState;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
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