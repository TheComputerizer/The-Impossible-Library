package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayTextEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Text;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;

public class RenderOverlayTextEventLegacy extends RenderOverlayTextEventWrapper<Text> {

    @SubscribeEvent
    public static void onEvent(Text event) {
        RENDER_OVERLAY_TEXT.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull Text event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected ClientOverlayEventType.OverlayType wrapOverlayType() {
        switch(Objects.isNull(this.event) ? RenderGameOverlayEvent.ElementType.ALL : this.event.getType()) {
            case AIR: return ClientOverlayEventType.OverlayType.AIR;
            case ARMOR: return ClientOverlayEventType.OverlayType.ARMOR;
            case BOSSHEALTH: return ClientOverlayEventType.OverlayType.BOSSHEALTH;
            case BOSSINFO: return ClientOverlayEventType.OverlayType.BOSSINFO;
            case CHAT: return ClientOverlayEventType.OverlayType.CHAT;
            case CROSSHAIRS: return ClientOverlayEventType.OverlayType.CROSSHAIRS;
            case DEBUG: return ClientOverlayEventType.OverlayType.DEBUG;
            case EXPERIENCE: return ClientOverlayEventType.OverlayType.EXPERIENCE;
            case FOOD: return ClientOverlayEventType.OverlayType.FOOD;
            case FPS_GRAPH: return ClientOverlayEventType.OverlayType.FPS_GRAPH;
            case HEALTH: return ClientOverlayEventType.OverlayType.HEALTH;
            case HEALTHMOUNT: return ClientOverlayEventType.OverlayType.HEALTHMOUNT;
            case HELMET: return ClientOverlayEventType.OverlayType.HELMET;
            case HOTBAR: return ClientOverlayEventType.OverlayType.HOTBAR;
            case JUMPBAR: return ClientOverlayEventType.OverlayType.JUMPBAR;
            case PLAYER_LIST: return ClientOverlayEventType.OverlayType.PLAYER_LIST;
            case PORTAL: return ClientOverlayEventType.OverlayType.PORTAL;
            case POTION_ICONS: return ClientOverlayEventType.OverlayType.POTION_ICONS;
            case SUBTITLES: return ClientOverlayEventType.OverlayType.SUBTITLES;
            case TEXT: return ClientOverlayEventType.OverlayType.TEXT;
            case VIGNETTE: return ClientOverlayEventType.OverlayType.VIGNETTE;
            default: return ClientOverlayEventType.OverlayType.ALL;
        }
    }

    @Override
    protected EventFieldWrapper<Text,List<String>> wrapLeftField() {
        return getFieldWrapperGetter(Text::getLeft,new ArrayList<>());
    }

    @Override
    protected EventFieldWrapper<Text,List<String>> wrapRightField() {
        return getFieldWrapperGetter(Text::getRight,new ArrayList<>());
    }
}