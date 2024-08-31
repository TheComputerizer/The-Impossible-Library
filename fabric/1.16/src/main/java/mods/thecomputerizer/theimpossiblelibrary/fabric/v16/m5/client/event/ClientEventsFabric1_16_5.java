package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.fabric.util.CustomTickFabric;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.event.ClientEvents1_16_5;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;

public class ClientEventsFabric1_16_5 extends ClientEvents1_16_5 {

    @Override
    public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventFabric());
        CLICK_INPUT.setConnector(new InputClickEventFabric());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventFabric());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventFabric());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventFabric());
        FOG_COLORS.setConnector(new FogColorsEventFabric());
        FOG_DENSITY.setConnector(new FogDensityEventFabric());
        FOG_RENDER.setConnector(new FogRenderEventFabric());
        FOV_MODIFIER.setConnector(new FOVModifierEventFabric());
        FOV_UPDATE.setConnector(new FOVUpdateEventFabric());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventFabric());
        KEY_INPUT.setConnector(new InputKeyEventFabric());
        MOUSE_INPUT.setConnector(new InputMouseEventFabric());
        MOUSE_RAW.setConnector(new RawMouseEventFabric());
        MOUSE_SCROLL.setConnector(new MouseScrollEventFabric());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventFabric());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventFabric());
        REGISTER_MODELS.setConnector(new RegisterModelsEventFabric());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventFabric());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventFabric());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventFabric());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventFabric());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventFabric());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventFabric());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventFabric());
        SOUND_LOAD.setConnector(new LoadSoundEventFabric());
        SOUND_PLAY.setConnector(new PlaySoundEventFabric());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEventFabric());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEventFabric());
        SOUND_SETUP.setConnector(new SoundSetupEventFabric());
        TICK_CLIENT.setConnector(new ClientTickEventFabric());
        TICK_RENDER.setConnector(new RenderTickEventFabric());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override
    public void postCustomTick(CustomTick ticker) {
        CustomTickFabric.CUSTOM_TICK.invoker().onTick(ticker);
    }
    
    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        Class<?> wrapperClass = wrapper.getClass();
        ReflectionHelper.invokeStaticMethod(wrapperClass, "register", new Class<?>[]{wrapperClass}, wrapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Object setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}