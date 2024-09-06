package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.GL1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.render.Render1_16_5;
import net.minecraft.client.Minecraft;

import java.util.Objects;

import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_COLOR;
import static net.minecraft.client.renderer.vertex.DefaultVertexFormats.POSITION_TEX_COLOR;

@SuppressWarnings("deprecation") @Getter
public class RenderForge1_16_5 extends Render1_16_5 {

    private MatrixStack matrix;

    public RenderForge1_16_5() {
        super(new GL1_16_5());
    }

    @Override public void bindTexture(ResourceLocationAPI<?> location) {
        Minecraft.getInstance().getTextureManager().bind(location.unwrap());
    }
    
    @Override public VertexWrapper getBufferBuilderPC(int mode, int vertices) {
        return new VertexWrapperForge1_16_5(mode,POSITION_COLOR,vertices,3,4);
    }
    
    @Override public VertexWrapper getBufferBuilderPTC(int mode, int vertices) {
        return new VertexWrapperForge1_16_5(mode,POSITION_TEX_COLOR,vertices,3,2,4);
    }
    
    @Override public double getDirectMouseX() {
        return Minecraft.getInstance().mouseHandler.xpos();
    }
    
    @Override public double getDirectMouseY() {
        return Minecraft.getInstance().mouseHandler.ypos();
    }
    
    @Override public void popMatrix() {
        this.matrix.popPose();
    }
    
    @Override public void pushMatrix() {
        this.matrix.pushPose();
    }

    @Override public void scale(float x, float y, float z) {
        if(Objects.nonNull(this.matrix)) this.matrix.scale(x,y,z);
    }

    @Override public void setMatrix(Object matrix) {
        this.matrix = matrix instanceof MatrixStack ? (MatrixStack)matrix : null;
    }
}
