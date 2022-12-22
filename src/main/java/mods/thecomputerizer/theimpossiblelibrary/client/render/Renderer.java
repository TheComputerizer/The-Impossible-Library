package mods.thecomputerizer.theimpossiblelibrary.client.render;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.AbstractGui;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector4f;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.apache.logging.log4j.Level;

import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Renderer {

    private static final ArrayList<PNG> renderablePngs = new ArrayList<>();

    public static PNG initializePng(ResourceLocation location) {
        try {
            return new PNG(location);
        } catch (IOException ex) {
            LogUtil.logInternal(Level.ERROR,"Failed to initialize png at resource location {}",location,ex);
        }
        return null;
    }

    /*
       accepts gifs, MP4s, and pngs
       horizontal accepts left, center, and right
       vertical accepts bottom, center, and top
       x and y are additional horizontal and vertical translations
       scalex and scale y are percentage scales
       all png images will be squares by default
       millis is how many milliseconds the image will be rendered for
       when using gifs and MP4 files, setting millis to 0 or less will render them for only 1 cycle
    */
    public static void renderAnyAcceptedFileTypeToBackground(Object holder, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(holder instanceof PNG) renderPNGToBackground(((PNG)holder),horizontal,vertical,x,y,scaleX,scaleY,millis);
        else LogUtil.logInternal(Level.WARN,"Object was not of the correct type. Accepted types are PNG objects. " +
                "Type {} was used",holder.getClass().getName());
    }
    public static void renderPNGToBackground(PNG png, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderablePngs.contains(png)) {
            png.setHorizontal(horizontal);
            png.setVertical(vertical);
            png.setX(x);
            png.setY(y);
            png.setScaleX(scaleX);
            png.setScaleY(scaleY);
            png.setMillis(millis);
            renderablePngs.add(png);
        }
    }

    //Do not call the stopRendering methods from outside threads!
    public static void stopRenderingAllPngs() {
        renderablePngs.clear();
    }

    public static void stopRenderingAllFileTypes() {
        stopRenderingAllPngs();
    }

    //Actual rendering methods
    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()== RenderGameOverlayEvent.ElementType.ALL) {
            int x = Minecraft.getInstance().getWindow().getScreenWidth();
            int y = Minecraft.getInstance().getWindow().getScreenHeight();
            Vector4f color = new Vector4f(1, 1, 1, 1);
            for(PNG png : renderablePngs) renderPng(e.getMatrixStack(),png,color,x,y);
        }
    }

    public static void renderPng(MatrixStack matrix, PNG png, Vector4f color, int resolutionX, int resolutionY) {
        RenderSystem.pushMatrix();
        RenderSystem.pushTextureAttributes();
        RenderSystem.enableAlphaTest();
        RenderSystem.enableBlend();
        png.loadToManager();
        RenderSystem.color4f(color.x(), color.y(), color.z(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*png.getScaleX();
        float scaleY = 0.25f*png.getScaleY();
        RenderSystem.scalef(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(png.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(png.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(png.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(png.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+png.getX();
        float posY = (yOffset*(1/scaleY))+png.getY();
        AbstractGui.blit(matrix,(int) posX, (int) posY, 0, 0, resolutionX, resolutionY, resolutionX, resolutionY);
        RenderSystem.popAttributes();
        RenderSystem.popMatrix();
    }
}
