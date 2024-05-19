package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.client.gui.screen.Screen;

public class ScreenWrapper1_16_5 extends Screen implements Wrapped<ScreenAPI> {
    
    private final ScreenAPI wrapped;

    public ScreenWrapper1_16_5(ScreenAPI wrapped) {
        super(((Text1_16_5)wrapped.getTitle()).getComponent());
        this.wrapped = wrapped;
    }
    
    @Override public ScreenAPI getWrapped() {
        return this.wrapped;
    }
}