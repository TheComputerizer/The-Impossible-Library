package mods.thecomputerizer.theimpossiblelibrary.client.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Map;

@SuppressWarnings("unused")
public class PNG extends Renderable {

    private final ResourceLocation source;

    /**
     * Preload a PNG image with parameters for use in the Renderer class.
     */
    public PNG(ResourceLocation location, Map<String, Object> parameters) throws IOException {
        super(parameters);
        if(!location.getPath().contains(".png")) throw new IOException("Tried to initialize a non png file to a png object! " +
                "Make sure that you have the correct file extension on your resource location.");
        this.source = location;
    }

    @Override
    public void render(ScaledResolution res) {
        if(canRender()) {
            int resX = res.getScaledWidth();
            int resY = res.getScaledHeight();
            GlStateManager.pushMatrix();
            GlStateManager.color(1f, 1f, 1f, getOpacity());
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.source);
            GlStateManager.scale(scaleX(resX,resY), scaleY(), 1f);
            int x = posX(resX,resY);
            int y = posY(resY);
            GuiScreen.drawModalRectWithCustomSizedTexture(x,y,resX,resY,resX,resY,resX,resY);
            GlStateManager.color(1F, 1F, 1F, 1);
            GlStateManager.popMatrix();
        }
    }
}
