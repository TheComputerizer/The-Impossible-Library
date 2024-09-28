package mods.thecomputerizer.theimpossiblelibrary.api.client.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.client.event.types.ClientEntityViewEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.event.ClientEventWrapper.ClientType.CAMERA_SETUP;

public abstract class CameraSetupEventWrapper<E> extends ClientEntityViewEventType<E> {

    protected EventFieldWrapper<E,Float> pitch;
    protected EventFieldWrapper<E,Float> roll;
    protected EventFieldWrapper<E,Float> yaw;

    protected CameraSetupEventWrapper() {
        super(CAMERA_SETUP);
    }

    public float getPitch() {
        return this.pitch.get(this.event);
    }

    public float getRoll() {
        return this.roll.get(this.event);
    }

    public float getYaw() {
        return this.yaw.get(this.event);
    }

    @Override protected void populate() {
        super.populate();
        this.roll = wrapRollField();
        this.pitch = wrapPitchField();
        this.yaw = wrapYawField();
    }

    public void setPitch(float pitch) {
        this.pitch.set(this.event,pitch);
    }

    public void setRoll(float roll) {
        this.roll.set(this.event,roll);
    }

    public void setYaw(float yaw) {
        this.yaw.set(this.event,yaw);
    }

    protected abstract EventFieldWrapper<E,Float> wrapPitchField();
    protected abstract EventFieldWrapper<E,Float> wrapRollField();
    protected abstract EventFieldWrapper<E,Float> wrapYawField();
}