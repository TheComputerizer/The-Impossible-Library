package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import net.minecraft.client.Minecraft;

public class ScreenHelper1_12_2 implements ScreenHelperAPI {
    
    @Override public void open(ScreenAPI screen) {
        Minecraft.getMinecraft().displayGuiScreen(new ScreenWrapper1_12_2(screen));
    }
}