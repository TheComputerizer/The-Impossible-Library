package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;

@Getter
public abstract class AttachCapabilitiesEventWrapper<O> implements EventAPI { //TODO Add API hooks for this

    protected final O object;

    public AttachCapabilitiesEventWrapper(O object) {
        this.object = object;
    }
}