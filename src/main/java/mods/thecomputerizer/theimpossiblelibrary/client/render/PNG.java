package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import net.minecraft.client.MainWindow;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
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
    public void render(MatrixStack matrix, MainWindow res) {
        if(canRender()) {
            int resX = res.getGuiScaledWidth();
            int resY = res.getGuiScaledHeight();
            matrix.pushPose();
            RenderSystem.color4f(1f, 1f, 1f, getOpacity());
            Minecraft.getInstance().getTextureManager().bind(this.source);
            matrix.scale(scaleX(resX,resY), scaleY(), 1f);
            int x = posX(resX,resY);
            int y = posY(resY);
            AbstractGui.blit(matrix, x, y, 0, 0, resX, resY, resX, resY);
            matrix.popPose();
        }
    }
}