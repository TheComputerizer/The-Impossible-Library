package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.font;

import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.core.FabricHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font.Font1_16_5;
import net.minecraft.client.gui.Font;
import net.minecraft.network.chat.MutableComponent;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class FontFabric1_16_5 extends Font1_16_5 {
    
    @SuppressWarnings("DataFlowIssue") 
    private Font getFontAs() {
        return (Font)(Object)getFont();
    }
    
    @SuppressWarnings("DataFlowIssue")
    private PoseStack getMatrixAs(RenderAPI<?> renderer) {
        return (PoseStack)(Object)getMatrix(renderer);
    }
    
    @Override public void renderToolTip(RenderAPI<?> renderer, Collection<TextAPI<?>> lines, int x, int y, int width, int height, int maxWidth) {
        List<MutableComponent> components = new ArrayList<>();
        for(TextAPI<?> text : lines) components.add(text.getAsComponent());
        FabricHelper.renderTooltip(getMatrixAs(renderer),components,x,y,width,height,maxWidth,getFontAs());
    }
}