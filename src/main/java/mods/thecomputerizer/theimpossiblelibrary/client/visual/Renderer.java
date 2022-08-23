package mods.thecomputerizer.theimpossiblelibrary.client.visual;

import mods.thecomputerizer.theimpossiblelibrary.TheImpossibleLibrary;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import javax.vecmath.Vector4f;
import java.io.IOException;
import java.util.ArrayList;

@SuppressWarnings("unused")
public class Renderer {

    private static final ArrayList<GIF> renderableGifs = new ArrayList<>();
    private static final ArrayList<MP4> renderableMp4s = new ArrayList<>();
    private static final ArrayList<PNG> renderablePngs = new ArrayList<>();

    public static MP4 initializeMp4(ResourceLocation location) {
        try {
            return new MP4(location);
        } catch (IOException ex) {
            TheImpossibleLibrary.logError("Failed to initialize mp4 at resource location "+location,ex);
        }
        return null;
    }

    public static GIF initializeGif(ResourceLocation location) {
        try {
            return new GIF(location);
        } catch (IOException ex) {
            TheImpossibleLibrary.logError("Failed to initialize gif at resource location "+location,ex);
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
        else if(holder instanceof GIF) renderGifToBackground(((GIF)holder),horizontal,vertical,x,y,scaleX,scaleY,millis);
        else if(holder instanceof MP4) renderMP4ToBackground(((MP4)holder),horizontal,vertical,x,y,scaleX,scaleY,millis);
        else TheImpossibleLibrary.logWarning("Object was not of the correct type. Accepted types are PNG, GIF, and MP4 objects. " +
                    "Type "+holder.getClass().getName()+" was used",null);
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

    public static void renderMP4ToBackground(MP4 mp4, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderableMp4s.contains(mp4)) {
            mp4.setHorizontal(horizontal);
            mp4.setVertical(vertical);
            mp4.setX(x);
            mp4.setY(y);
            mp4.setScaleX(scaleX);
            mp4.setScaleY(scaleY);
            mp4.setMillis(millis);
            renderableMp4s.add(mp4);
        }
    }

    public static void renderGifToBackground(GIF gif, String horizontal, String vertical, int x, int y, float scaleX, float scaleY, long millis) {
        if(!renderableGifs.contains(gif)) {
            gif.setHorizontal(horizontal);
            gif.setVertical(vertical);
            gif.setX(x);
            gif.setY(y);
            gif.setScaleX(scaleX);
            gif.setScaleY(scaleY);
            gif.setMillis(millis);
            renderableGifs.add(gif);
        }
    }

    //Do not call the stopRendering methods from outside threads!
    public static void stopRenderingAllGifs() {
        renderableGifs.clear();
    }

    public static void stopRenderingAllMp4s() {
        renderableMp4s.clear();
    }

    public static void stopRenderingAllPngs() {
        renderablePngs.clear();
    }

    public static void stopRenderingAllFileTypes() {
        stopRenderingAllGifs();
        stopRenderingAllMp4s();
        stopRenderingAllPngs();
    }

    //Actual rendering methods
    @SubscribeEvent
    public static void renderAllBackgroundStuff(RenderGameOverlayEvent.Post e) {
        if(e.getType()== RenderGameOverlayEvent.ElementType.ALL) {
            ScaledResolution res = e.getResolution();
            int x = res.getScaledWidth();
            int y = res.getScaledHeight();
            Vector4f color = new Vector4f(1, 1, 1, 1);
            for(GIF gif : renderableGifs) renderGif(gif,color,x,y);
            for(MP4 mp4 : renderableMp4s) renderMp4(mp4,color,x,y);
            for(PNG png : renderablePngs) renderPng(png,color,x,y);
            renderableGifs.removeIf(gif -> {
                if(!gif.checkMilli((long)(50f*e.getPartialTicks())))
                    gif.isFinished = true;
                return gif.isFinished;
            });
            renderableMp4s.removeIf(mp4 -> {
                if(!mp4.checkMilli((long)(50f*e.getPartialTicks())))
                    mp4.isFinished = true;
                return mp4.isFinished;
            });
        }
    }

    public static void renderGif(GIF gif, Vector4f color, int resolutionX, int resolutionY) {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        gif.loadCurrentFrame(false,false);
        GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*gif.getScaleX();
        float scaleY = 0.25f*gif.getScaleY();
        if(gif.getWidthRatio()>=1f) scaleX*=gif.getWidthRatio();
        if(gif.getHeightRatio()>=1f) scaleY*=gif.getHeightRatio();
        GlStateManager.scale(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(gif.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(gif.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(gif.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(gif.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+gif.getX();
        float posY = (yOffset*(1/scaleY))+gif.getY();
        GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,resolutionX,resolutionY,resolutionX,resolutionY,resolutionX,resolutionY);
        GlStateManager.color(1F, 1F, 1F, 1);
        GlStateManager.popMatrix();
    }

    public static void renderMp4(MP4 mp4, Vector4f color, int resolutionX, int resolutionY) {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        mp4.loadCurrentFrame(false,false);
        GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*mp4.getScaleX();
        float scaleY = 0.25f*mp4.getScaleY();
        if(mp4.getWidthRatio()>=1f) scaleX*=mp4.getWidthRatio();
        if(mp4.getHeightRatio()>=1f) scaleY*=mp4.getHeightRatio();
        GlStateManager.scale(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(mp4.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(mp4.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(mp4.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(mp4.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+mp4.getX();
        float posY = (yOffset*(1/scaleY))+mp4.getY();
        GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,resolutionX,resolutionY,resolutionX,resolutionY,resolutionX,resolutionY);
        GlStateManager.color(1F, 1F, 1F, 1);
        GlStateManager.popMatrix();
    }

    public static void renderPng(PNG png, Vector4f color, int resolutionX, int resolutionY) {
        GlStateManager.enableBlend();
        GlStateManager.pushMatrix();
        png.loadToManager();
        GlStateManager.color(color.getX(), color.getY(), color.getZ(), 1f);
        float scaleX  = (0.25f*((float)resolutionY/(float)resolutionX))*png.getScaleX();
        float scaleY = 0.25f*png.getScaleY();
        GlStateManager.scale(scaleX,scaleY,1f);
        int xOffset = 0;
        int yOffset = 0;
        if(png.getHorizontal().matches("center")) xOffset = (int) ((resolutionX/2f)-((float)resolutionX*(scaleX/2f)));
        else if(png.getHorizontal().matches("right")) xOffset = (int) (resolutionX-((float)resolutionX*(scaleX/2f)));
        if(png.getVertical().matches("center")) yOffset = (int) ((resolutionY/2f)-((float)resolutionY*(scaleY/2f)));
        else if(png.getVertical().matches("top")) yOffset = (int) (resolutionY-((float)resolutionY*(scaleY/2f)));
        float posX = (xOffset*(1/scaleX))+png.getX();
        float posY = (yOffset*(1/scaleY))+png.getY();
        GuiScreen.drawModalRectWithCustomSizedTexture((int)posX,(int)posY,resolutionX,resolutionY,resolutionX,resolutionY,resolutionX,resolutionY);
        GlStateManager.color(1F, 1F, 1F, 1);
        GlStateManager.popMatrix();
    }
}
