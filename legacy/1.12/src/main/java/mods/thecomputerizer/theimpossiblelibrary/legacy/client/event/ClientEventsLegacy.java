package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events.RenderOverlayPostEventLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events.RenderOverlayPreEventLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events.ClientTickEventLegacy;
import net.minecraftforge.common.MinecraftForge;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;

public class ClientEventsLegacy implements EventsAPI {

    private boolean defined;

    public ClientEventsLegacy() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        CAMERA_SETUP.setConnector(null);
        CLICK_INPUT.setConnector(null);
        CLIENT_CONNECTED.setConnector(null);
        CLIENT_DISCONNECTED.setConnector(null);
        CLIENT_RESPAWN.setConnector(null);
        FOG_COLORS.setConnector(null);
        FOG_DENSITY.setConnector(null);
        FOV_MODIFIER.setConnector(null);
        FOG_RENDER.setConnector(null);
        FOV_UPDATE.setConnector(null);
        ITEM_TOOLTIP.setConnector(null);
        KEY_INPUT.setConnector(null);
        MOUSE_INPUT.setConnector(null);
        MOUSE_RAW.setConnector(null);
        MOUSE_SCROLL.setConnector(null);
        PLAYER_PUNCH_EMPTY.setConnector(null);
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(null);
        REGISTER_MODELS.setConnector(null);
        RENDER_OVERLAY_BOSS.setConnector(null);
        RENDER_OVERLAY_CHAT.setConnector(null);
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventLegacy());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventLegacy());
        RENDER_OVERLAY_TEXT.setConnector(null);
        RENDER_WORLD_LAST.setConnector(null);
        SOUND_LOAD.setConnector(null);
        SOUND_PLAY.setConnector(null);
        SOUND_PLAY_SOURCE.setConnector(null);
        SOUND_PLAY_STREAMING.setConnector(null);
        SOUND_SETUP.setConnector(null);
        TICK_CLIENT.setConnector(new ClientTickEventLegacy());
        TICK_RENDER.setConnector(null);
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.register(wrapper.getClass());
    }
}