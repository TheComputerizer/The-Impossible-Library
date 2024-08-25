package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.util.CustomTick1_12_2;
import net.minecraftforge.client.event.RenderBlockOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEvents1_12_2 implements EventsAPI {

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

    public static @Nullable RenderContext initRenderer(Supplier<Float> partialTickSupplier) {
        RenderContext ctx = RenderHelper.getContext();
        if(Objects.nonNull(ctx)) ctx.setPartialTicks(partialTickSupplier.get());
        return ctx;
    }

    private boolean defined;

    public ClientEvents1_12_2() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEvent1_12_2());
        CLICK_INPUT.setConnector(new InputClickEvent1_12_2());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEvent1_12_2());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEvent1_12_2());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEvent1_12_2());
        FOG_COLORS.setConnector(new FogColorsEvent1_12_2());
        FOG_DENSITY.setConnector(new FogDensityEvent1_12_2());
        FOG_RENDER.setConnector(new FogRenderEvent1_12_2());
        FOV_MODIFIER.setConnector(new FOVModifierEvent1_12_2());
        FOV_UPDATE.setConnector(new FOVUpdateEvent1_12_2());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEvent1_12_2());
        KEY_INPUT.setConnector(new InputKeyEvent1_12_2());
        MOUSE_INPUT.setConnector(new InputMouseEvent1_12_2());
        MOUSE_RAW.setConnector(new RawMouseEvent1_12_2());
        MOUSE_SCROLL.setConnector(new MouseScrollEvent1_12_2());
        PLAYER_PUNCH_EMPTY.setConnector(new PlayerPunchEmptyEvent1_12_2());
        PLAYER_PUSH_OUT_OF_BLOCKS.setConnector(new PlayerPushOutOfBlocksEvent1_12_2());
        REGISTER_MODELS.setConnector(new RegisterModelsEvent1_12_2());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEvent1_12_2());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEvent1_12_2());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEvent1_12_2());
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEvent1_12_2());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEvent1_12_2());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEvent1_12_2());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEvent1_12_2());
        SOUND_LOAD.setConnector(new LoadSoundEvent1_12_2());
        SOUND_PLAY.setConnector(new PlaySoundEvent1_12_2());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEvent1_12_2());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEvent1_12_2());
        SOUND_SETUP.setConnector(new SoundSetupEvent1_12_2());
        TICK_CLIENT.setConnector(new ClientTickEvent1_12_2());
        TICK_RENDER.setConnector(new RenderTickEvent1_12_2());
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTick1_12_2(ticker));
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
}