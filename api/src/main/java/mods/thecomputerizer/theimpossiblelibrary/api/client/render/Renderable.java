package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import java.util.Map;
import java.util.Objects;

public abstract class Renderable {

    private final Map<String,Object> parameters;
    private long totalTimer;
    private long maxFadeIn;
    private long fadeInTimer;
    private long maxFadeOut;
    private long fadeOutTimer;

    /**
     * Preload a renderable object with parameters.
     */
    public Renderable(Map<String, Object> parameters) {
        this.parameters = parameters;
    }
    
    public boolean canRender() {
        return this.totalTimer>0;
    }
    
    protected double getAllignmentX() {
        String alignment = getParameterAs("horizontal_alignment","center");
        return "center".equals(alignment) ? 0d : ("right".equals(alignment) ? 1d : -1d);
    }
    
    protected double getAllignmentY() {
        String alignment = getParameterAs("vertical_alignment","center");
        return "center".equals(alignment) ? 0d : ("top".equals(alignment) ? 1d : -1d);
    }
    
    public float getOpacity() {
        float def = getParameterAs("opacity",1f);
        if(this.fadeInTimer>0) return def*(1-(((float)this.fadeInTimer)/((float)this.maxFadeIn)));
        if(this.fadeOutTimer<this.maxFadeOut) return def*(((float)this.fadeOutTimer)/((float)this.maxFadeOut));
        return def;
    }

    /**
     * Gets parameter by name and tries to cast it to whatever the default input is. The default is returned if the
     * parameter name does not exist or the cast fails. Note that you must input both the default value and the class
     * of the default value separately for the cast to work properly
     */
    @SuppressWarnings("unchecked")
    public <T> T getParameterAs(String name, T defVal) {
        try {
            T casted = GenericUtils.castGenericType(this.parameters.get(name),(Class<T>)defVal.getClass());
            return Objects.nonNull(casted) ? casted : defVal;
        } catch(ClassCastException | NumberFormatException ex) {
            TILRef.logDebug("Failed to parse value from parameter with name {}!",name,ex);
        }
        return defVal;
    }
    
    public void initializeTimers() {
        this.totalTimer = getParameterAs("time",100L);
        this.maxFadeIn = getParameterAs("fade_in",20L);
        this.fadeInTimer = getParameterAs("fade_in",20L);
        this.maxFadeOut = getParameterAs("fade_out",20L);
        this.fadeOutTimer = getParameterAs("fade_out",20L);
    }
    
    public abstract void pos(RenderContext ctx);
    
    public void scale(RenderContext ctx) {
        ctx.getRenderer().scale(getParameterAs("scale_x",1f),getParameterAs("scale_y",1f),1f);
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
            if(getParameterAs("loop",false)) {
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
}
