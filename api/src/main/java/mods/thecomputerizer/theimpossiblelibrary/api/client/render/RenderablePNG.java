package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;

import java.io.IOException;
import java.util.Map;

public class RenderablePNG extends Renderable {

    protected final ResourceLocationAPI<?> source;

    public RenderablePNG(ResourceLocationAPI<?> source, Map<String, Object> parameters) throws IOException {
        super(parameters);
        if(!source.getPath().endsWith(".png"))
            throw new IOException("Tried to initialize a non png file to a png " +
                "object! Make sure that you have the correct file extension on your resource location. ["+source+"]");
        this.source = source;
    }

    protected void preRender(RenderAPI renderer) {
        renderer.pushMatrix();
        renderer.enableBlend();
        renderer.defaultBlendFunc();
        renderer.disableAlpha();
    }

    @Override
    void render(MinecraftAPI mc) {
        if(canRender()) {
            RenderAPI renderer = mc.getRenderer();
            preRender(renderer);
            MinecraftWindow window = mc.getWindow();
            int resX = (int)window.getWidth();
            int resY = (int)window.getHeight();
            renderer.scale(scaleX(resX,resY),scaleY(),1f);
            int x = posX(window);
            int y = posY(resY);
            RenderHelper.enforceAlphaTexture(mc,x,y,resX,resY,Math.max(0.1f,getOpacity()),this.source);
            postRender(renderer);
        }
    }

    protected void postRender(RenderAPI renderer) {
        renderer.popMatrix();
    }
}
