package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.gui.ScreenWrapper1_19;

import java.util.Objects;

public class ScreenWrapper1_19_4 extends ScreenWrapper1_19 {
    
    public ScreenWrapper1_19_4(ScreenAPI wrapped) {
        super(wrapped);
    }
    
    @Override public void init() {
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenOpened();
        this.isOpen = true;
    }
    
    @Override public void removed() {}
}