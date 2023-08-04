package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.gui.GuiComponent;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
public class PNG extends Renderable {

    protected final ResourceLocation source;

    /**
     * Preload a PNG image with parameters for use in the Renderer class.
     */
    public PNG(ResourceLocation location, Map<String, Object> parameters) throws IOException {
        super(parameters);
        if(!location.getPath().endsWith(".png")) throw new IOException("Tried to initialize a non png file to a png " +
                "object! Make sure that you have the correct file extension on your resource location. ["+location+"]");
        this.source = location;
    }

    protected void preRender(PoseStack matrix) {
        matrix.pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
        RenderSystem.setShaderColor(1f, 1f, 1f, getOpacity());
        RenderSystem.setShaderTexture(0,this.source);
    }

    @Override
    public void render(PoseStack matrix, Window res) {
        if(canRender()) {
            preRender(matrix);
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            matrix.scale(scaleX(resX,resY), scaleY(), 1f);
            int x = posX(resX,resY);
            int y = posY(resY);
            GuiComponent.blit(matrix, x, y, 0, 0, resX, resY, resX, resY);
            postRender(matrix);
        }
    }

    protected void postRender(PoseStack matrix) {
        matrix.popPose();
    }
}