package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event;

import com.mojang.blaze3d.matrix.MatrixStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.util.CustomTick1_16_5;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.common.MinecraftForge;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;

public class ClientEvents1_16_5 implements EventsAPI {

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

    public static @Nullable RenderContext initRenderer(Supplier<Float> partialTick, Supplier<MatrixStack> matrix) {
        RenderContext ctx = RenderHelper.getContext();
        if(Objects.nonNull(ctx)) {
            ctx.setPartialTicks(partialTick.get());
            ctx.getRenderer().setMatrix(matrix.get());
        }
        return ctx;
    }

    private boolean defined;

    public ClientEvents1_16_5() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEvent1_16_5());
        CLICK_INPUT.setConnector(new InputClickEvent1_16_5());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEvent1_16_5());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEvent1_16_5());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEvent1_16_5());
        FOG_COLORS.setConnector(new FogColorsEvent1_16_5());
        FOG_DENSITY.setConnector(new FogDensityEvent1_16_5());
        FOG_RENDER.setConnector(new FogRenderEvent1_16_5());
        FOV_MODIFIER.setConnector(new FOVModifierEvent1_16_5());
        FOV_UPDATE.setConnector(new FOVUpdateEvent1_16_5());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEvent1_16_5());
        KEY_INPUT.setConnector(new InputKeyEvent1_16_5());
        MOUSE_INPUT.setConnector(new InputMouseEvent1_16_5());
        MOUSE_RAW.setConnector(new RawMouseEvent1_16_5());
        MOUSE_SCROLL.setConnector(new MouseScrollEvent1_16_5());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEvent1_16_5());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEvent1_16_5());
        REGISTER_MODELS.setConnector(new RegisterModelsEvent1_16_5());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEvent1_16_5());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEvent1_16_5());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEvent1_16_5());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEvent1_16_5());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEvent1_16_5());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEvent1_16_5());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEvent1_16_5());
        SOUND_LOAD.setConnector(new LoadSoundEvent1_16_5());
        SOUND_PLAY.setConnector(new PlaySoundEvent1_16_5());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEvent1_16_5());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEvent1_16_5());
        SOUND_SETUP.setConnector(new SoundSetupEvent1_16_5());
        TICK_CLIENT.setConnector(new ClientTickEvent1_16_5());
        TICK_RENDER.setConnector(new RenderTickEvent1_16_5());
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public void postCustomTick(CustomTick ticker) {
        MinecraftForge.EVENT_BUS.post(new CustomTick1_16_5(ticker));
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.register(wrapper.getClass());
    }
}