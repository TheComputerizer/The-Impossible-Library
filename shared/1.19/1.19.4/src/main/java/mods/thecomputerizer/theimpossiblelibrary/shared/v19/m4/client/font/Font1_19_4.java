package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.font;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.Tesselator;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.font.Font1_19;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.MultiBufferSource.BufferSource;
import net.minecraft.util.FormattedCharSequence;
import org.joml.Matrix4f;

import static net.minecraft.client.gui.Font.DisplayMode.SEE_THROUGH;

public class Font1_19_4 extends Font1_19 {
    
    @Override public void draw(RenderAPI renderer, String text, float x, float y, int color) {
        PoseStack stack = getMatrix(renderer);
        stack.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderer.setFont(this.wrapped);
        BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        getWrapped().drawInBatch(text,x,y,color,true,stack.last().pose(),buffer,
                                 SEE_THROUGH,0,15728880);
        buffer.endBatch();
        stack.popPose();
    }
    
    @Override public void drawInBatch(Object text, float x, float y, int color, boolean shadow, Object matrix,
            Object source, boolean transparent, int bgColor, int light) {
        getWrapped().drawInBatch((FormattedCharSequence)text,x,y,color,shadow,(Matrix4f)matrix,
                                 (MultiBufferSource)source,SEE_THROUGH,bgColor,light);
    }
    
    @Override public void drawWithShadow(RenderAPI renderer, String text, float x, float y, int color) {
        PoseStack stack = getMatrix(renderer);
        stack.pushPose();
        RenderSystem.enableDepthTest();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        renderer.setFont(this.wrapped);
        BufferSource buffer = MultiBufferSource.immediate(Tesselator.getInstance().getBuilder());
        getWrapped().drawInBatch(text,x,y,color,true,stack.last().pose(),buffer,
                                 SEE_THROUGH,0,15728880);
        buffer.endBatch();
        stack.popPose();
    }
}
