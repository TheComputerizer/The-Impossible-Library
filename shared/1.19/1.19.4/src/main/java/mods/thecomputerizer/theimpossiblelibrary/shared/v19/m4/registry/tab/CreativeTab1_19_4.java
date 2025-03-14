package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.tab;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.tab.CreativeTab1_19;

public class CreativeTab1_19_4 extends CreativeTab1_19 {
    
    public CreativeTab1_19_4(Object wrapped) {
        super(wrapped);
    }
    
    @Override public void addStack(ItemStackAPI<?> stack) {
        this.wrapped.getDisplayItems().add(stack.unwrap());
    }
}
