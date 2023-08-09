package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.platform.Window;
import com.mojang.blaze3d.systems.RenderSystem;
import com.mojang.blaze3d.vertex.PoseStack;
import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.resources.ResourceLocation;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
@Environment(EnvType.CLIENT)
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

    protected void preRender(GuiGraphics graphics) {
        graphics.pose().pushPose();
        RenderSystem.enableBlend();
        RenderSystem.defaultBlendFunc();
    }

    @Override
    public void render(GuiGraphics graphics, Window res) {
        if(canRender()) {
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            int x = posX(resX,resY);
            int y = posY(resY);
            preRender(graphics);
            graphics.pose().scale(scaleX(resX,resY), scaleY(), 1f);
            GuiUtil.enforceAlphaTexture(graphics.pose(),x,y,resX,resY,Math.max(0.1f,getOpacity()),this.source);
            postRender(graphics);
        }
    }

    protected void postRender(GuiGraphics graphics) {
        graphics.pose().popPose();
    }
}