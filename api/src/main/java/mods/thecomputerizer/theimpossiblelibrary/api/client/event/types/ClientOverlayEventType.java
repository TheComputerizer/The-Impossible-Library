package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import lombok.Getter;

@Getter
public abstract class ClientOverlayEventType<E> extends ClientRenderEventType<E> {

    protected OverlayType overlayType;

    protected ClientOverlayEventType(ClientType<?> type) {
        super(type);
    }

    public boolean isType(OverlayType type) {
        return this.overlayType==type;
    }

    @Override
    public void populate() {
        super.populate();
        this.overlayType = wrapOverlayType();
    }

    protected abstract OverlayType wrapOverlayType();

    public enum OverlayType {
        AIR, ALL, ARMOR, BOSSHEALTH, BOSSINFO,
        BLOCK, CHAT, CROSSHAIRS, DEBUG, EXPERIENCE,
        FIRE, FOOD, FPS_GRAPH, HEALTH, HEALTHMOUNT,
        HELMET, HOTBAR, JUMPBAR, PLAYER_LIST, PORTAL,
        POTION_ICONS, SUBTITLES, TEXT, VIGNETTE, WATER
    }
}