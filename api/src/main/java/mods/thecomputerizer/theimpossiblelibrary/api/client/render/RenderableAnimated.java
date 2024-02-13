package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.resource.ResourceLocationAPI;

import java.io.IOException;
import java.util.Map;

/**
 * Preload a PNG animated sprite sheet with parameters for use in the Renderer class.
 */
public class RenderableAnimated extends RenderablePNG {

    private final int frames;
    private final long millisPerFrame;
    private int curFrame;
    private boolean startedRendering;
    private long prevMillis;
    private long milliCounter;

    public RenderableAnimated(ResourceLocationAPI source, Map<String, Object> parameters) throws IOException {
        super(source,parameters);
        int fps = getParameterAs("fps",20);
        this.millisPerFrame = (long)(1000f/((float)fps));
        this.milliCounter = 0;
        this.frames = source.getSpriteFrames();
        this.curFrame = 0;
        this.startedRendering = false;
        ReferenceAPI.logDebug("Initialized sprite with {} frames and FPS millis of {}",this.frames,this.millisPerFrame);
    }

    private void renderTick() {
        long curMillis = System.currentTimeMillis();
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
    public void render(MinecraftAPI mc) {
        if(this.startedRendering) renderTick();
        if(canRender()) {
            if(!this.startedRendering) {
                this.prevMillis = System.currentTimeMillis();
                this.startedRendering = true;
            }
            int resX = (int)mc.getWindow().getWidth();
            int resY = (int)mc.getWindow().getHeight();
            RenderAPI renderer = mc.getRenderer();
            preRender(renderer);
            renderer.scale(scaleX(resX,resY),scaleY(),1f);
            drawSprite(mc,posX(mc.getWindow()),posY(resY),resX,resY);
            postRender(renderer);
        }
    }

    private void drawSprite(MinecraftAPI mc, int x, int y, int width, int height) {
        float framePercent = 1f/((float)this.frames);
        float vMin = (float)Math.max(0d,this.curFrame*framePercent);
        float vMax = (float)Math.min(1d,(this.curFrame+1)*framePercent);
        RenderHelper.drawTexturedRect(mc,x,y,width,height,Math.max(0.1f,getOpacity()),this.source,0f,1f,vMin,vMax);
    }

    @Override
    public void stop() {
        super.stop();
        this.startedRendering = false;
    }
}