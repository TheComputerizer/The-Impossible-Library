package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientEntityViewEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.FOG_COLORS;

public abstract class FogColorsEventWrapper<E> extends ClientEntityViewEventType<E> {

    protected EventFieldWrapper<E,Float> blue;
    protected EventFieldWrapper<E,Float> green;
    protected EventFieldWrapper<E,Float> red;

    protected FogColorsEventWrapper() {
        super(FOG_COLORS);
    }

    public float getBlue() {
        return this.blue.get(this.event);
    }

    public float getGreen() {
        return this.green.get(this.event);
    }

    public float getRed() {
        return this.red.get(this.event);
    }

    @Override
    protected void populate() {
        super.populate();
        this.blue = wrapBlue();
        this.green = wrapGreen();
        this.red = wrapRed();
    }

    public void setBlue(float blue) {
        this.blue.set(this.event,blue);
    }

    public void setGreen(float green) {
        this.green.set(this.event,green);
    }

    public void setRed(float red) {
        this.red.set(this.event,red);
    }

    protected abstract EventFieldWrapper<E,Float> wrapBlue();
    protected abstract EventFieldWrapper<E,Float> wrapGreen();
    protected abstract EventFieldWrapper<E,Float> wrapRed();
}