package mods.thecomputerizer.theimpossiblelibrary.fabric.v20.client;

import com.google.common.base.Strings;
import mods.thecomputerizer.theimpossiblelibrary.fabric.client.FabricHandlesClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

public class FabricHandlesClient1_20 extends FabricHandlesClient {
    
    @Override public void renderDebugText(Object graphicsObj, List<String> text, boolean left) {
        if(text.isEmpty()) return;
        GuiGraphics graphics = (GuiGraphics)graphicsObj;
        Minecraft mc = Minecraft.getInstance();
        for(int i=0;i<text.size();i++) {
            String string = text.get(i);
            if(!Strings.isNullOrEmpty(string)) {
                int lineSpacing = 9;
                int textWidth = mc.font.width(string);
                int x = left ? 2 : mc.getWindow().getGuiScaledWidth()-textWidth-2;
                int y = 2+lineSpacing*i;
                graphics.fill(x-1,y-1,x+textWidth+1,y+lineSpacing-1,-1873784752);
                graphics.drawString(mc.font,string,x,y,14737632);
            }
        }
    }
}