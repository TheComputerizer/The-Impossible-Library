package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events.*;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;

import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;

public class ClientEventsLegacy implements EventsAPI {

    public static OverlayType getOverlayBlockType(RenderBlockOverlayEvent.OverlayType type) {
        switch(type) {
            case FIRE: return OverlayType.FIRE;
            case WATER: return OverlayType.WATER;
            default: return OverlayType.BLOCK;
        }
    }

    public static OverlayType getOverlayElementType(ElementType type) {
        switch(type) {
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

    public static @Nullable RenderAPI initRenderer(Supplier<Float> partialTickSupplier) {
        RenderAPI renderer = RenderHelper.getRenderer();
        if(Objects.nonNull(renderer)) renderer.setPartialTicks(partialTickSupplier.get());
        return renderer;
    }

    private boolean defined;

    public ClientEventsLegacy() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventLegacy());
        CLICK_INPUT.setConnector(new InputClickEventLegacy());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventLegacy());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventLegacy());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventLegacy());
        FOG_COLORS.setConnector(new FogColorsEventLegacy());
        FOG_DENSITY.setConnector(new FogDensityEventLegacy());
        FOG_RENDER.setConnector(new FogRenderEventLegacy());
        FOV_MODIFIER.setConnector(new FOVModifierEventLegacy());
        FOV_UPDATE.setConnector(new FOVUpdateEventLegacy());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventLegacy());
        KEY_INPUT.setConnector(new InputKeyEventLegacy());
        MOUSE_INPUT.setConnector(new InputMouseEventLegacy());
        MOUSE_RAW.setConnector(new RawMouseEventLegacy());
        MOUSE_SCROLL.setConnector(new MouseScrollEventLegacy());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEventLegacy());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEventLegacy());
        REGISTER_MODELS.setConnector(new RegisterModelsEventLegacy());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventLegacy());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventLegacy());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventLegacy());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventLegacy());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventLegacy());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventLegacy());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventLegacy());
        SOUND_LOAD.setConnector(new LoadSoundEventLegacy());
        SOUND_PLAY.setConnector(new PlaySoundEventLegacy());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEventLegacy());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEventLegacy());
        SOUND_SETUP.setConnector(new SoundSetupEventLegacy());
        TICK_CLIENT.setConnector(new ClientTickEventLegacy());
        TICK_RENDER.setConnector(new RenderTickEventLegacy());
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