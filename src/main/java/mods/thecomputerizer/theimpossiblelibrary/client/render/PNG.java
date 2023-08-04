package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
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
    }

    @Override
    public void render(PoseStack matrix, Window res) {
        if(canRender()) {
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            int x = posX(resX,resY);
            int y = posY(resY);
            preRender(matrix);
            matrix.scale(scaleX(resX,resY), scaleY(), 1f);
            GuiUtil.enforceAlphaTexture(matrix,x,y,resX,resY,Math.max(0.1f,getOpacity()),this.source);
            postRender(matrix);
        }
    }

    protected void postRender(PoseStack matrix) {
        matrix.popPose();
    }
}