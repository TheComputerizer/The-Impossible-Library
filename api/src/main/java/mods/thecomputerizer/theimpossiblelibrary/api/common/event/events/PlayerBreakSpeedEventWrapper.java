package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerStateEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_BREAK_SPEED;

public abstract class PlayerBreakSpeedEventWrapper<E> extends CommonPlayerStateEventType<E> {

    protected EventFieldWrapper<E,Float> originalSpeed;
    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;
    protected EventFieldWrapper<E,Float> speed;

    protected PlayerBreakSpeedEventWrapper() {
        super(PLAYER_BREAK_SPEED);
    }

    public float getOriginalSpeed() {
        return this.originalSpeed.get(this.event);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    public float getSpeed() {
        return this.speed.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.originalSpeed = wrapOriginalSpeedField();
        this.pos = wrapPosField();
        this.speed = wrapSpeedField();
    }

    public void setSpeed(float speed) {
        this.speed.set(this.event,speed);
    }

    protected abstract EventFieldWrapper<E,Float> wrapOriginalSpeedField();
    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
    protected abstract EventFieldWrapper<E,Float> wrapSpeedField();
}