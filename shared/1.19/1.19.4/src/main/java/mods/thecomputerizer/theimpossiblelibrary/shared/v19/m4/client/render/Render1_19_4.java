package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render.Render1_19;
import org.joml.Matrix4f;

import java.util.Objects;

public class Render1_19_4 extends Render1_19 {
    
    public Render1_19_4() {
        super(new GL1_19_4());
    }

    @Override public void disableTexture() {}

    @Override public void enableTexture() {}
    
    @Override public void modelView() {
        if(Objects.nonNull(this.modelView)) {
            this.modelView.popPose();
            RenderSystem.applyModelViewMatrix();
            this.modelView = null;
        } else {
            this.modelView = RenderSystem.getModelViewStack();
            this.modelView.pushPose();
            this.modelView.mulPoseMatrix(getMatrix().last().pose());
            RenderSystem.applyModelViewMatrix();
        }
    }
    
    @Override public <B> B vertexWithMatrix(B buffer, Object matrix, float x, float y, float z) {
        ((BufferBuilder)buffer).vertex((Matrix4f)matrix, x, y, z);
        return buffer;
    }
}