package mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType.OverlayType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.ClientForgeEventHelper;
import mods.thecomputerizer.theimpossiblelibrary.forge.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v20.client.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.event.ClientEvents1_20;
import net.minecraftforge.client.event.RenderBlockScreenEffectEvent;
import net.minecraftforge.client.gui.overlay.VanillaGuiOverlay;
import net.minecraftforge.eventbus.api.Event;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.ModContainer;
import net.minecraftforge.fml.javafmlmod.FMLModContainer;
import org.jetbrains.annotations.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

public class ClientEventsForge1_20 extends ClientEvents1_20 implements ClientForgeEventHelper {

    @Override public void defineEvents() {
        CAMERA_SETUP.setConnector(new CameraSetupEventForge1_20());
        CLICK_INPUT.setConnector(new InputClickEventForge());
        CLIENT_CONNECTED.setConnector(new ClientConnectedEventForge1_20());
        CLIENT_DISCONNECTED.setConnector(new ClientDisconnectedEventForge1_20());
        CLIENT_RESPAWN.setConnector(new ClientRespawnEventForge1_20());
        FOG_COLORS.setConnector(new FogColorsEventForge1_20());
        FOG_DENSITY.setConnector(new FogDensityEventForge1_20());
        FOG_RENDER.setConnector(new FogRenderEventForge1_20());
        FOV_MODIFIER.setConnector(new FOVModifierEventForge1_20());
        FOV_UPDATE.setConnector(new FOVUpdateEventForge1_20());
        ITEM_TOOLTIP.setConnector(new ItemTooltipEventForge());
        KEY_INPUT.setConnector(new InputKeyEventForge1_20());
        MOUSE_INPUT.setConnector(new InputMouseEventForge());
        MOUSE_RAW.setConnector(new RawMouseEventForge());
        MOUSE_SCROLL.setConnector(new MouseScrollEventForge());
        REGISTER_MODELS.setConnector(new RegisterModelsEventForge());
        RENDER_OVERLAY_BLOCK.setConnector(new RenderOverlayBlockEventForge1_20());
        RENDER_OVERLAY_BOSS.setConnector(new RenderOverlayBossEventForge1_20());
        RENDER_OVERLAY_CHAT.setConnector(new RenderOverlayChatEventForge1_20());
        RENDER_OVERLAY_TEXT.setConnector(new RenderOverlayTextEventForge1_20());
        RENDER_WORLD_LAST.setConnector(new RenderWorldLastEventForge1_20());
        SOUND_LOAD.setConnector(new LoadSoundEventForge());
        SOUND_PLAY.setConnector(new PlaySoundEventForge1_20());
        SOUND_PLAY_SOURCE.setConnector(new PlaySoundSourceEventForge());
        SOUND_PLAY_STREAMING.setConnector(new PlayStreamingSoundSourceEventForge());
        SOUND_SETUP.setConnector(new SoundSetupEventForge());
        TICK_CLIENT.setConnector(new ClientTickEventForge());
        TICK_RENDER.setConnector(new RenderTickEventForge());
        defineVersionedEvents();
        super.defineEvents();
    }
    
    protected void defineVersionedEvents() {
        RENDER_OVERLAY_POST.setConnector(new RenderOverlayPostEventForge1_20());
        RENDER_OVERLAY_PRE.setConnector(new RenderOverlayPreEventForge1_20());
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }
    
    @Override public <B> OverlayType getOverlayBlockType(B blockType) {
        return switch((RenderBlockScreenEffectEvent.OverlayType)blockType) {
            case FIRE -> OverlayType.FIRE;
            case WATER -> OverlayType.WATER;
            default -> OverlayType.BLOCK;
        };
    }
    
    @Override public <E> OverlayType getOverlayElementType(E elementType) {
        return switch(((VanillaGuiOverlay)elementType)) {
            case AIR_LEVEL -> OverlayType.AIR;
            case ARMOR_LEVEL -> OverlayType.ARMOR;
            case BOSS_EVENT_PROGRESS -> OverlayType.BOSSINFO;
            case CHAT_PANEL -> OverlayType.CHAT;
            case CROSSHAIR -> OverlayType.CROSSHAIRS;
            case DEBUG_TEXT -> OverlayType.DEBUG;
            case EXPERIENCE_BAR -> OverlayType.EXPERIENCE;
            case FOOD_LEVEL -> OverlayType.FOOD;
            case HELMET -> OverlayType.HELMET;
            case HOTBAR -> OverlayType.HOTBAR;
            case JUMP_BAR -> OverlayType.JUMPBAR;
            case PLAYER_HEALTH -> OverlayType.HEALTH;
            case PLAYER_LIST -> OverlayType.PLAYER_LIST;
            case PORTAL -> OverlayType.PORTAL;
            case POTION_ICONS -> OverlayType.POTION_ICONS;
            case SUBTITLES -> OverlayType.SUBTITLES;
            case TITLE_TEXT -> OverlayType.TEXT;
            case VIGNETTE -> OverlayType.VIGNETTE;
            default -> OverlayType.ALL;
        };
    }
    
    @Override @Nullable public IEventBus getModBus(ModContainer container) {
        return container instanceof FMLModContainer ? ((FMLModContainer)container).getEventBus() : null;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickForge(ticker));
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        registerForgeOrModBus(wrapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}