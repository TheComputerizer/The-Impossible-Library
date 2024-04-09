package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_CHAT;

public abstract class RenderOverlayChatEventWrapper<E> extends ClientOverlayEventType<E> {

    protected EventFieldWrapper<E,Integer> posX;
    protected EventFieldWrapper<E,Integer> posY;

    protected RenderOverlayChatEventWrapper() {
        super(RENDER_OVERLAY_CHAT);
    }

    public int getPosX() {
        return this.posX.get(this.event);
    }

    public int getPosY() {
        return this.posY.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.posX = wrapPosXField();
        this.posY = wrapPosYField();
    }

    public void setPosX(int x) {
        this.posX.set(this.event,x);
    }

    public void setPosY(int y) {
        this.posY.set(this.event,y);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapPosXField();
    protected abstract EventFieldWrapper<E,Integer> wrapPosYField();
}