package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import net.minecraft.client.Minecraft;

public class ScreenHelper1_16_5 implements ScreenHelperAPI {
    
    @Override public void open(ScreenAPI screen) {
        Minecraft.getInstance().setScreen(new ScreenWrapper1_16_5(screen));
    }
}