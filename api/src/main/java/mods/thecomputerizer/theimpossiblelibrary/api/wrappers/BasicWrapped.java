package mods.thecomputerizer.theimpossiblelibrary.api.wrappers;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

@IndirectCallers
public class BasicWrapped<W> extends AbstractWrapped<W> {
    
    public BasicWrapped(W wrapped) {
        super(wrapped);
    }
}