package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.RenderOverlayEventWrapper.OverlayType.ALL;

@Getter
public abstract class RenderOverlayEventWrapper<E> extends RenderEventWrapper<E> {

    protected OverlayType overlayType = ALL;

    protected RenderOverlayEventWrapper(ClientType<?> type) {
        super(type);
    }

    public boolean isType(OverlayType type) {
        return this.overlayType==type;
    }

    public enum OverlayType {
        AIR, ALL, ARMOR, BOSSHEALTH, BOSSINFO,
        CHAT, CROSSHAIRS, DEBUG, EXPERIENCE, FOOD,
        FPS_GRAPH, HEALTH, HEALTHMOUNT, HELMET, HOTBAR,
        JUMPBAR, PLAYER_LIST, PORTAL, POTION_ICONS, SUBTITLES,
        TEXT, VIGNETTE
    }
}