package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import lombok.Getter;
import lombok.Setter;

@Getter @Setter
public abstract class CancellableEvent {

    private boolean cancelled;

    protected CancellableEvent() {}
}