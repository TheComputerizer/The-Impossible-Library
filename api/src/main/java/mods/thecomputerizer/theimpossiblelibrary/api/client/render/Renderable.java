package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.parameter.Parameter;
import mods.thecomputerizer.theimpossiblelibrary.api.parameter.TILParameterMap;

import java.util.Collection;
import java.util.Map;

public abstract class Renderable implements TILParameterMap {

    private final Map<String,Parameter<?>> parameters;
    private long totalTimer;
    private long maxFadeIn;
    private long fadeInTimer;
    private long maxFadeOut;
    private long fadeOutTimer;

    /**
     * Preload a renderable object with parameters.
     */
    public Renderable(Map<String,Parameter<?>> parameters) {
        this.parameters = parameters;
    }
    
    public boolean canRender() {
        return this.totalTimer>0;
    }
    
    protected double getAllignmentX() {
        String alignment = getParameterAsString("horizontal_alignment","center");
        return "center".equals(alignment) ? 0d : ("right".equals(alignment) ? 1d : -1d);
    }
    
    protected double getAllignmentY() {
        String alignment = getParameterAsString("vertical_alignment","center");
        return "center".equals(alignment) ? 0d : ("top".equals(alignment) ? 1d : -1d);
    }
    
    public float getOpacity() {
        float def = getParameterAsFloat("opacity",1f);
        if(this.fadeInTimer>0) return def*(1-(((float)this.fadeInTimer)/((float)this.maxFadeIn)));
        if(this.fadeOutTimer<this.maxFadeOut) return def*(((float)this.fadeOutTimer)/((float)this.maxFadeOut));
        return def;
    }
    
    @Override public Parameter<?> getParameter(String name) {
        return this.parameters.get(name);
    }
    
    public void initializeTimers() {
        this.totalTimer = getParameterAsLong("time",100L);
        this.maxFadeIn = getParameterAsLong("fade_in",20L);
        this.fadeInTimer = getParameterAsLong("fade_in",20L);
        this.maxFadeOut = getParameterAsLong("fade_out",20L);
        this.fadeOutTimer = getParameterAsLong("fade_out",20L);
    }
    
    @Override public Collection<String> keys() {
        return this.parameters.keySet();
    }
    
    @Override public Collection<Parameter<?>> parameters() {
        return this.parameters.values();
    }
    
    public abstract void pos(RenderContext ctx);
    
    public void scale(RenderContext ctx) {
        double smallerScale = ctx.getScale().getSmallerDimensionScale();
        float x = (float)(getParameterAsDouble("scale_x",1d)*0.25d*(ctx.isWide() ? smallerScale : 1d));
        float y = (float)(getParameterAsDouble("scale_y",1d)*0.25d*(ctx.isWide() ? 1d : smallerScale));
        ctx.getRenderer().scale(x,y,1f);
        translateScaled(ctx,x,y);
    }
    
    abstract void render(RenderContext ctx);

    /**
     * Called when the timer is finished, or it can be called earlier if the image is no longer needed.
     */
    public void stop() {
        this.totalTimer = 0;
        this.fadeInTimer = 0;
        this.maxFadeIn = 0;
        this.fadeOutTimer = 0;
        this.maxFadeOut = 0;
    }
    
    public boolean tick() {
        if(this.totalTimer<=0) {
            if(getParameterAsBoolean("loop",false)) {
                initializeTimers();
                return true;
            }
            else {
                stop();
                return false;
            }
        }
        if(this.fadeInTimer>0) this.fadeInTimer--;
        if(this.totalTimer<=this.fadeOutTimer) this.fadeOutTimer--;
        this.totalTimer--;
        return true;
    }
    
    protected void translateScaled(RenderContext ctx, float scaleX, float scaleY) {
        float width = (float)ctx.getScale().getScreenWidth();
        float height = (float)ctx.getScale().getScreenHeight();
        float translateX = (width/(scaleX*2f))-(width/2f);
        float translateY = (height/(scaleY*4f))-(height/2f);
        ctx.getRenderer().translate(translateX,translateY,0f);
    }
}
