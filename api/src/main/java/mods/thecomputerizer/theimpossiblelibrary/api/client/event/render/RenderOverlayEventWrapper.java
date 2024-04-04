package mods.thecomputerizer.theimpossiblelibrary.api.client.event.render;

public abstract class RenderOverlayEventWrapper<E> extends RenderEventWrapper<E> {

    protected RenderOverlayEventWrapper(ClientType<?> type) {
        super(type);
    }
    public abstract OverlayType getOverlayType();

    public boolean isType(OverlayType type) {
        return getOverlayType()==type;
    }

    public enum OverlayType {

        AIR,
        ALL,
        ARMOR,
        BOSSHEALTH, // All boss bars
        BOSSINFO, // Individual boss bar
        CHAT,
        CROSSHAIRS,
        DEBUG,
        EXPERIENCE,
        FOOD,
        FPS_GRAPH,
        HEALTH,
        HEALTHMOUNT,
        HELMET,
        HOTBAR,
        JUMPBAR,
        PLAYER_LIST,
        PORTAL,
        POTION_ICONS,
        SUBTITLES,
        TEXT,
        VIGNETTE
    }
}