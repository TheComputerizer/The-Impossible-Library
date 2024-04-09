package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_BOSS;

public abstract class RenderOverlayBossEventWrapper<E> extends ClientOverlayEventType<E> {

    protected EventFieldWrapper<E,Integer> increment;
    protected EventFieldWrapper<E,Integer> x;
    protected EventFieldWrapper<E,Integer> y;

    protected RenderOverlayBossEventWrapper() {
        super(RENDER_OVERLAY_BOSS);
    }

    public int getIncrement() {
        return this.increment.get(this.event);
    }

    public int getX() {
        return this.x.get(this.event);
    }

    public int getY() {
        return this.y.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.increment = wrapIncrementField();
        this.x = wrapXField();
        this.y = wrapYField();
    }

    public void setIncrement(int i) {
        this.increment.set(this.event,i);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapIncrementField();
    protected abstract EventFieldWrapper<E,Integer> wrapXField();
    protected abstract EventFieldWrapper<E,Integer> wrapYField();
}