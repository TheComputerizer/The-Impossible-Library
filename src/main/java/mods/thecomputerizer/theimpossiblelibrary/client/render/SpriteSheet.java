package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.Constants;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.texture.PngSizeInfo;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.IResource;
import net.minecraft.util.ResourceLocation;

import java.io.IOException;
import java.util.Map;

public class SpriteSheet extends PNG {

    private final int frames;
    private final long millisPerFrame;
    private int curFrame;
    private boolean startedRendering;
    private long prevMillis;
    private long milliCounter;

    /**
     * Preload a PNG animated sprite sheet with parameters for use in the Renderer class.
     */
    protected SpriteSheet(ResourceLocation location, Map<String, Object> parameters) throws IOException {
        super(location, parameters);
        int fps = getParameterAs("fps",20, Integer.class);
        this.millisPerFrame = (int)(1000f/((float)fps));
        this.milliCounter = 0;
        PngSizeInfo size = PngSizeInfo.makeFromResource(getResource());
        this.frames = size.pngHeight/size.pngWidth;
        this.curFrame = 0;
        Constants.testLog("INITIALIZED SPRITE WITH {} FRAMES AND FPS MILLIS OF {}",this.frames,this.millisPerFrame);
    }

    private IResource getResource() throws IOException {
        return Minecraft.getMinecraft().getResourceManager().getResource(this.source);
    }

    private long getMillis() {
        return System.nanoTime()/1000000L;
    }

    private void renderTick() {
        long curMillis = getMillis();
        long elapsedMillis = curMillis-this.prevMillis;
        this.prevMillis = curMillis;
        this.milliCounter+=elapsedMillis;
        while(this.milliCounter>=this.millisPerFrame) {
            this.curFrame++;
            if(this.curFrame>=this.frames) this.curFrame = 0;
            this.milliCounter-=this.millisPerFrame;
        }
    }

    @Override
    public void render(ScaledResolution res) {
        if(this.startedRendering) renderTick();
        if(canRender()) {
            if(!this.startedRendering) {
                this.prevMillis = getMillis();
                this.startedRendering = true;
            }
            preRender();
            int resX = res.getScaledWidth();
            int resY = res.getScaledHeight();
            Minecraft.getMinecraft().getTextureManager().bindTexture(this.source);
            GlStateManager.scale(scaleX(resX,resY), scaleY(), 1f);
            drawSprite(posX(resX,resY),posY(resY),resX,resY);
            postRender();
        }
    }

    private void drawSprite(int x, int y, int width, int height) {
        float framePercent = 1f/((float)this.frames);
        double vMin = Math.max(0d,this.curFrame*framePercent);
        double vMax = Math.min(1d,(this.curFrame+1)*framePercent);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX);
        bufferbuilder.pos(x, y + height, 0).tex(0,vMax).endVertex();
        bufferbuilder.pos(x + width, y + height, 0).tex(1d, vMax).endVertex();
        bufferbuilder.pos(x + width, y, 0).tex(1d, vMin).endVertex();
        bufferbuilder.pos(x, y, 0).tex(0, vMin).endVertex();
        tessellator.draw();
    }

    @Override
    public void stop() {
        super.stop();
        this.startedRendering = false;
    }
}
