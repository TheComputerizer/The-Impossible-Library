package mods.thecomputerizer.theimpossiblelibrary.api.client.event;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.render.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.render.RenderOverlayPreEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.tick.ClientTickEventWrapper<?>;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;

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

    @Getter
    public static final class ClientType<E extends ClientEventWrapper<?>> extends EventType<E> {

        public static ClientType<ClientTickEventWrapper<?>> CAMERA_SETUP = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> CLICK_INPUT = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> CLIENT_CONNECTED = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> CLIENT_DISCONNECTED = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> CLIENT_RESPAWN = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> FOG_COLORS = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> FOG_DENSITY = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> FOV_MODIFIER = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> FOG_RENDER = new ClientType<>(false,true);
        public static ClientType<ClientTickEventWrapper<?>> FOV_UPDATE = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> ITEM_TOOLTIP = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> KEY_INPUT = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> MOUSE_INPUT = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> MOUSE_RAW = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> MOUSE_SCROLL = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> PLAYER_PUNCH_EMPTY = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> PLAYER_PUSH_OUT_OF_BLOCKS = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> REGISTER_MODELS = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> RENDER_OVERLAY_BOSS = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> RENDER_OVERLAY_CHAT = new ClientType<>(true,false);
        public static ClientType<RenderOverlayPostEventWrapper<?>> RENDER_OVERLAY_POST = new ClientType<>(true,false);
        public static ClientType<RenderOverlayPreEventWrapper<?>> RENDER_OVERLAY_PRE = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> RENDER_OVERLAY_TEXT = new ClientType<>(true,false);
        public static ClientType<ClientTickEventWrapper<?>> RENDER_WORLD_LAST = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> SOUND_LOAD = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> SOUND_PLAY = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> SOUND_PLAY_SOURCE = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> SOUND_PLAY_STREAMING = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> SOUND_SETUP = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> TICK_CLIENT = new ClientType<>(false,false);
        public static ClientType<ClientTickEventWrapper<?>> TICK_RENDER = new ClientType<>(false,false);

        private ClientType(boolean cancelable, boolean hasResult) {
            super(cancelable,hasResult);
        }

        @Override
        public boolean isClient() {
            return true;
        }
    }
}