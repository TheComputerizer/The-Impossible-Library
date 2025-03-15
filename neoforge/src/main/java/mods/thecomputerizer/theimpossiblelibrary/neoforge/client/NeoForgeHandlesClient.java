package mods.thecomputerizer.theimpossiblelibrary.neoforge.client;

import com.google.common.base.Strings;
import mods.thecomputerizer.theimpossiblelibrary.api.client.SharedHandlesClient;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;

/**
 * Only keybinds & RenderType stuff needs to be handled in 1.20.4+
 */
public abstract class NeoForgeHandlesClient extends SharedHandlesClient {
    
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
    
    @Override public void renderToolTip(RenderAPI r, List<?> l, int x, int y, int w, int h, int maxW) {}
}