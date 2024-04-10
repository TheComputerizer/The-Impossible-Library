package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;

public abstract class CommonLivingStackUseEventType<E> extends CommonLivingStackEventType<E> {

    protected EventFieldWrapper<E,Integer> duration;

    protected CommonLivingStackUseEventType(CommonType<?> type) {
        super(type);
    }

    public int getDuration() {
        return this.duration.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.duration = wrapDurationField();
    }

    public void setDuration(int duration) {
        this.duration.set(this.event,duration);
    }

    protected abstract EventFieldWrapper<E,Integer> wrapDurationField();
}