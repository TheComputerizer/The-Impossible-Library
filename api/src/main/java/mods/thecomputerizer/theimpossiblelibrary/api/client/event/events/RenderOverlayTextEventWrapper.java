package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientOverlayEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.RENDER_OVERLAY_TEXT;

public abstract class RenderOverlayTextEventWrapper<E> extends ClientOverlayEventType<E> {

    protected EventFieldWrapper<E,List<String>> left;
    protected EventFieldWrapper<E,List<String>> right;

    protected RenderOverlayTextEventWrapper() {
        super(RENDER_OVERLAY_TEXT);
    }

    public List<String> getLeft() {
        return this.left.get(this.event);
    }

    public List<String> getRight() {
        return this.right.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.left = wrapLeftField();
        this.right = wrapRightField();
    }

    protected abstract EventFieldWrapper<E,List<String>> wrapLeftField();
    protected abstract EventFieldWrapper<E,List<String>> wrapRightField();
}