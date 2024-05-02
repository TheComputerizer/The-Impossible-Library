package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

@Getter
public abstract class ClientEventWrapper<E> extends EventWrapper<E> {

    protected MinecraftAPI minecraft;

    protected ClientEventWrapper(ClientType<?> type) {
        super(type);
    }

    @Override
    public boolean isClient() {
        return true;
    }

    @Override
    public boolean isCommon() {
        return false;
    }

    @Override
    public boolean isServer() {
        return false;
    }
    
    @Override
    public void setEvent(E event) {
        super.setEvent(event);
        this.minecraft = TILRef.getClientSubAPI(ClientAPI::getMinecraft);
    }

    public static final class ClientType<E extends ClientEventWrapper<?>> extends EventType<E> {

        public static ClientType<CameraSetupEventWrapper<?>> CAMERA_SETUP = new ClientType<>(false,false);
        public static ClientType<InputClickEventWrapper<?>> CLICK_INPUT = new ClientType<>(true,false);
        public static ClientType<ClientConnectedEventWrapper<?>> CLIENT_CONNECTED = new ClientType<>(false,false);
        public static ClientType<ClientDisconnectedEventWrapper<?>> CLIENT_DISCONNECTED = new ClientType<>(false,false);
        public static ClientType<ClientRespawnEventWrapper<?>> CLIENT_RESPAWN = new ClientType<>(false,false);
        public static ClientType<FogColorsEventWrapper<?>> FOG_COLORS = new ClientType<>(false,false);
        public static ClientType<FogDensityEventWrapper<?>> FOG_DENSITY = new ClientType<>(true,false);
        public static ClientType<FogRenderEventWrapper<?>> FOG_RENDER = new ClientType<>(false,true);
        public static ClientType<FOVModifierEventWrapper<?>> FOV_MODIFIER = new ClientType<>(false,false);
        public static ClientType<FOVUpdateEventWrapper<?>> FOV_UPDATE = new ClientType<>(false,false);
        public static ClientType<ItemTooltipEventWrapper<?>> ITEM_TOOLTIP = new ClientType<>(false,false);
        public static ClientType<InputKeyEventWrapper<?>> KEY_INPUT = new ClientType<>(false,false);
        public static ClientType<InputMouseEventWrapper<?>> MOUSE_INPUT = new ClientType<>(false,false);
        public static ClientType<RawMouseEventWrapper<?>> MOUSE_RAW = new ClientType<>(true,false);
        public static ClientType<MouseScrollEventWrapper<?>> MOUSE_SCROLL = new ClientType<>(true,false);
        public static ClientType<PlayerPunchEmptyEventWrapper<?>> PLAYER_PUNCH_EMPTY = new ClientType<>(false,false);
        public static ClientType<PlayerPushOutOfBlocksEventWrapper<?>> PLAYER_PUSH_OUT_OF_BLOCKS = new ClientType<>(true,false);
        public static ClientType<RegisterModelsEventWrapper<?>> REGISTER_MODELS = new ClientType<>(false,false);
        public static ClientType<RenderOverlayBlockEventWrapper<?>> RENDER_OVERLAY_BLOCK = new ClientType<>(true,false);
        public static ClientType<RenderOverlayBossEventWrapper<?>> RENDER_OVERLAY_BOSS = new ClientType<>(true,false);
        public static ClientType<RenderOverlayChatEventWrapper<?>> RENDER_OVERLAY_CHAT = new ClientType<>(true,false);
        public static ClientType<RenderOverlayPostEventWrapper<?>> RENDER_OVERLAY_POST = new ClientType<>(true,false);
        public static ClientType<RenderOverlayPreEventWrapper<?>> RENDER_OVERLAY_PRE = new ClientType<>(true,false);
        public static ClientType<RenderOverlayTextEventWrapper<?>> RENDER_OVERLAY_TEXT = new ClientType<>(true,false);
        public static ClientType<RenderWorldLastEventWrapper<?>> RENDER_WORLD_LAST = new ClientType<>(false,false);
        public static ClientType<LoadSoundEventWrapper<?,?>> SOUND_LOAD = new ClientType<>(false,false);
        public static ClientType<PlaySoundEventWrapper<?,?>> SOUND_PLAY = new ClientType<>(false,false);
        public static ClientType<PlaySoundSourceEventWrapper<?,?>> SOUND_PLAY_SOURCE = new ClientType<>(false,false);
        public static ClientType<PlayStreamingSoundSourceEventWrapper<?,?>> SOUND_PLAY_STREAMING = new ClientType<>(false,false);
        public static ClientType<SoundSetupEventWrapper<?,?>> SOUND_SETUP = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> TICK_CLIENT = new ClientType<>(false,false);
        public static ClientType<RenderTickEventWrapper<?>> TICK_RENDER = new ClientType<>(false,false);

        private ClientType(boolean cancelable, boolean hasResult) {
            super(cancelable,hasResult);
        }

        @Override
        public boolean isClient() {
            return true;
        }

        @Override
        public boolean isCommon() {
            return false;
        }

        @Override
        public boolean isServer() {
            return false;
        }
    }
}