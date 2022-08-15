package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import mods.thecomputerizer.theimpossiblelibrary.util.CustomTick;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class Renderer {

    private static final Map<GIF, Long> renderableGifs = new HashMap<>();

    public static GIF initializeGif(ResourceLocation location) {
        try {
            return new GIF(location);
        } catch (IOException ex) {
            TheImpossibleLibrary.logError("Failed to initialize gif at resource location "+location,ex);
        }
        return null;
    }

    /*
        horizontal accepts left, center, and right
        vertical accepts bottom, center, and top
        x and y are additional horizontal and vertical translations
        scalex and scale y are percentage scales
        set millis to 0 or less to only render the gif for 1 cycle
     */
    public static void renderGifToBackground(GIF gif, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderableGifs.containsKey(gif)) {
            gif.setHorizontal(horizontal);
            gif.setVertical(vertical);
            gif.setX(x);
            gif.setY(y);
            gif.setScaleX(scaleX);
            gif.setScaleY(scaleY);
            gif.setMillis(millis);
            renderableGifs.put(gif, gif.getDelay());
        }
    }

    public static void stopRenderingAllGifs() {
        renderableGifs.clear();
    }

    @SubscribeEvent
    public static void tickGifs(CustomTick ev) {
        for(GIF gif : renderableGifs.keySet()) {
            if (ev.checkTickRate(renderableGifs.get(gif)))
                if(!gif.incrementFrame()) renderableGifs.remove(gif);
        }
    }

    @SubscribeEvent
    public static void renderGifs(RenderGameOverlayEvent.Post e) {
        if(e.getType()== RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution res = e.getResolution();
            int x = res.getScaledWidth();
            int y = res.getScaledHeight();
            Vector4f color = new Vector4f(1, 1, 1, 1);
            for(GIF gif : renderableGifs.keySet()) {
                GlStateManager.enableBlend();
                GlStateManager.pushMatrix();
                gif.loadCurrentFrame(false,false);
                GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
                float scaleX  = (0.25f*((float)y/(float)x))*gif.getScaleX();
                float scaleY = 0.25f*gif.getScaleY();
                GlStateManager.scale(scaleX,scaleY,1f);
                int xOffset = 0;
                int yOffset = 0;
                if(gif.getHorizontal().matches("center")) xOffset = (x/2)+(gif.getScaledWidth()/2);
                else if(gif.getHorizontal().matches("right")) xOffset = x-gif.getScaledWidth();
                if(gif.getVertical().matches("center")) yOffset = (y/2)+(gif.getScaledHeight()/2);
                else if(gif.getVertical().matches("top")) yOffset = y-gif.getScaledHeight();
                float posX = (xOffset*(1/scaleX))+gif.getX();
                float posY = (yOffset*(1/scaleY))+gif.getY();
                GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,x,y,x,y,x,y);
                GlStateManager.color(1F, 1F, 1F, 1);
                GlStateManager.popMatrix();
            }
        }
    }
}
