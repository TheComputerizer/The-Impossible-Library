package mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.events.RenderOverlayPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.event.ClientEventsLegacy;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.client.event.RenderGameOverlayEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.annotation.Nonnull;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_POST;

public class RenderOverlayPostEventLegacy extends RenderOverlayPostEventWrapper<Post> {

    @SubscribeEvent
    public static void onEvent(Post event) {
        RENDER_OVERLAY_POST.invoke(event);
    }

    @Override
    protected RenderAPI initRenderer(@Nonnull Post event) {
        return ClientEventsLegacy.initRenderer(event::getPartialTicks);
    }

    @Override
    protected OverlayType wrapOverlayType() {
        switch(Objects.isNull(this.event) ? ElementType.ALL : this.event.getType()) {
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
}
