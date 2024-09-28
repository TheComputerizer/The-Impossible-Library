package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;

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

    public RenderableAnimated(ResourceLocationAPI<?> source, Map<String, Object> parameters) throws IOException {
        super(source,parameters);
        int fps = getParameterAs("fps",20);
        this.millisPerFrame = (long)(1000f/((float)fps));
        this.milliCounter = 0;
        this.frames = source.getSpriteFrames();
        this.curFrame = 0;
        this.startedRendering = false;
        TILRef.logDebug("Initialized sprite with {} frames and FPS millis of {}",this.frames,this.millisPerFrame);
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

    @Override public void render(RenderContext ctx) {
        if(this.startedRendering) renderTick();
        if(canRender()) {
            if(!this.startedRendering) {
                this.prevMillis = System.currentTimeMillis();
                this.startedRendering = true;
            }
            preRender(ctx,Math.max(0.1f,getOpacity()));
            drawSprite(ctx);
            postRender(ctx.getRenderer());
        }
    }

    private void drawSprite(RenderContext ctx) {
        float framePercent = 1f/((float)this.frames);
        float vMin = (float)Math.max(0d,this.curFrame*framePercent);
        float vMax = (float)Math.min(1d,(this.curFrame+1)*framePercent);
        this.texture.getWrapped().getTexture().setV(vMin,vMax);
        this.texture.draw(ctx,VectorHelper.zero3D(),0d,0d);
    }

    @Override public void stop() {
        super.stop();
        this.startedRendering = false;
    }
}