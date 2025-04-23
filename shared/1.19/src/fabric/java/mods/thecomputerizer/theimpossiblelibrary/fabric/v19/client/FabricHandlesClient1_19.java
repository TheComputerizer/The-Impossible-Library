package mods.thecomputerizer.theimpossiblelibrary.fabric.v19.client;

import com.google.common.base.Strings;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.FabricHandlesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Gui;

import java.util.List;

public class FabricHandlesClient1_19 extends FabricHandlesClient {
    
    @Override public void renderDebugText(Object matrix, List<String> text, boolean left) {
        if(text.isEmpty()) return;
        PoseStack stack = (PoseStack)matrix;
        Minecraft mc = Minecraft.getInstance();
        for(int i=0;i<text.size();i++) {
            String string = text.get(i);
            if(!Strings.isNullOrEmpty(string)) {
                int lineSpacing = 9;
                int textWidth = mc.font.width(string);
                int x = left ? 2 : mc.getWindow().getGuiScaledWidth()-textWidth-2;
                int y = 2+lineSpacing*i;
                Gui.fill(stack,x-1,y-1,x+textWidth+1,y+lineSpacing-1,-1873784752);
                mc.font.draw(stack,string,(float)x,(float)y,14737632);
            }
        }
    }
}