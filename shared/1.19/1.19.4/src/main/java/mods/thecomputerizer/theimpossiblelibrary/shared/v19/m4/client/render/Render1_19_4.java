package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.render;

import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.BufferBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.render.Render1_19;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import org.joml.Matrix4f;

import java.util.Collection;
import java.util.List;
import java.util.Objects;

public class Render1_19_4 extends Render1_19 {
    
    public Render1_19_4() {
        super(new GL1_19_4());
    }

    @Override public void disableTexture() {}
    
    @Override public void drawTooltip(FontAPI<?> font, Collection<TextAPI<?>> lines, Number x, Number y, Number width,
            Number height, Number maxWidth) {
        int iX = x.intValue();
        int iY = y.intValue();
        int iWidth = width.intValue();
        int iHeight = height.intValue();
        int iMaxWidth = maxWidth.intValue();
        Screen curScreen = Minecraft.getInstance().screen;
        if(Objects.nonNull(curScreen)) {
            List<Component> unwrapped = font.unwrapTooltipComponents(lines);
            curScreen.renderComponentTooltip(unwrapMatrix(),unwrapped,iX,iY);
        }
        else font.renderToolTip(this,lines,iX,iY,iWidth,iHeight,iMaxWidth);
    }

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