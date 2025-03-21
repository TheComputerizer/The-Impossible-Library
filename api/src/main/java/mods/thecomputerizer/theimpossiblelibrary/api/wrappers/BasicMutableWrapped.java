package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

public class BasicMutableWrapped<W> extends MutableWrapped<W> {
    
    public BasicMutableWrapped() {
        super();
    }
    
    @IndirectCallers
    public BasicMutableWrapped(W wrapped) {
        super(wrapped);
    }
}