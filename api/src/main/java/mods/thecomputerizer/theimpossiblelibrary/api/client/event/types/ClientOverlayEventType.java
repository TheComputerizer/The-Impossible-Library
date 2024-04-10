package mods.thecomputerizer.theimpossiblelibrary.api.client.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

public abstract class ClientOverlayEventType<E> extends ClientRenderEventType<E> {

    protected EventFieldWrapper<E,OverlayType> overlayType;

    protected ClientOverlayEventType(ClientType<?> type) {
        super(type);
    }

    public OverlayType getOverlayType() {
        return this.overlayType.get(this.event);
    }

    public boolean isType(OverlayType type) {
        return getOverlayType()==type;
    }

    @Override
    public void populate() {
        super.populate();
        this.overlayType = wrapOverlayType();
    }

    protected abstract EventFieldWrapper<E,OverlayType> wrapOverlayType();

    public enum OverlayType {
        AIR, ALL, ARMOR, BOSSHEALTH, BOSSINFO,
        BLOCK, CHAT, CROSSHAIRS, DEBUG, EXPERIENCE,
        FIRE, FOOD, FPS_GRAPH, HEALTH, HEALTHMOUNT,
        HELMET, HOTBAR, JUMPBAR, PLAYER_LIST, PORTAL,
        POTION_ICONS, SUBTITLES, TEXT, VIGNETTE, WATER
    }
}