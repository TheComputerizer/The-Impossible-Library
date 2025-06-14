package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.gui;

import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.gui.ScreenWrapper1_19;


import java.util.Objects;

public class ScreenWrapper1_19_4 extends ScreenWrapper1_19 {
    
    private static final boolean FORGE = CoreAPI.isForge();
    
    public ScreenWrapper1_19_4(ScreenAPI wrapped) {
        super(wrapped);
    }
    
    @Override public void init() {
        if(Objects.nonNull(this.wrapped)) this.wrapped.onScreenOpened();
        this.isOpen = true;
    }
    
    @Override public void removed() {}
    
    @Override public void render(PoseStack matrix, int mouseX, int mouseY, float partialTicks) {
        if(Objects.nonNull(this.wrapped)) {
            RenderContext ctx = RenderContext.get(ClientHelper.getMinecraft());
            ctx.setPartialTicks(partialTicks);
            ctx.getRenderer().setMatrix(matrix);
            double x = -1d+((double)mouseX)*ctx.getScale().getScreenScaleX();
            double y = 1d-((double)mouseY)*ctx.getScale().getScreenScaleY();
            if(FORGE) matrix.translate(0d,0d,-200d); //maybe?
            this.wrapped.draw(ctx,x,y);
        }
    }
}