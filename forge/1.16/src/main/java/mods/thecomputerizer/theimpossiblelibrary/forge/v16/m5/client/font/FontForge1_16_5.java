package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import net.minecraft.util.text.ITextComponent;
import net.minecraftforge.fml.client.gui.GuiUtils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FontForge1_16_5 extends Font1_16_5 {
    
    @Override public void renderToolTip(RenderAPI<?> renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<ITextComponent> components = new ArrayList<>();
        for(TextAPI<?> text : lines) components.add(text.getAsComponent());
        GuiUtils.drawHoveringText(getMatrix(renderer),components,x,y,width,height,maxWidth,getFont());
    }
}