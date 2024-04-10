package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.event.events.*;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;

public class ClientEventsForge implements EventsAPI {

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

    public static @Nullable RenderAPI initRenderer(Supplier<Float> partialTick, Supplier<MatrixStack> matrix) {
        RenderAPI renderer = RenderHelper.getRenderer();
        if(Objects.nonNull(renderer)) {
            renderer.setPartialTicks(partialTick.get());
            renderer.setMatrix(matrix.get());
        }
        return renderer;
    }

    private boolean defined;

    public ClientEventsForge() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
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