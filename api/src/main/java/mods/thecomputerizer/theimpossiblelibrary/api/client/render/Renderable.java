package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;

import java.util.Map;
import java.util.Objects;
import java.util.Random;

public abstract class Renderable {

    private final Map<String,Object> parameters;
    private long maxTime;
    private long totalTimer;
    private long maxFadeIn;
    private long fadeInTimer;
    private long maxFadeOut;
    private long fadeOutTimer;

    /**
     * Preload renderable object with parameters.
     */
    public Renderable(Map<String, Object> parameters) {
        this.parameters = parameters;
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
            ReferenceAPI.logDebug("Failed to parse value from parameter with name {}!",name,ex);
        }
        return defVal;
    }
    public void initializeTimers(Random rand) {
        this.maxTime = getParameterAs("time",100L);
        this.totalTimer = getParameterAs("time",100L);
        this.maxFadeIn = getParameterAs("fade_in",20L);
        this.fadeInTimer = getParameterAs("fade_in",20L);
        this.maxFadeOut = getParameterAs("fade_out",20L);
        this.fadeOutTimer = getParameterAs("fade_out",20L);
    }

    public boolean tick() {
        if(this.totalTimer<=0) {
            if(getParameterAs("loop",false)) {
                initializeTimers(RenderHelper.getRandom());
                return true;
            }
            else {
                stop();
                return false;
            }
        }
        long fadeIn = this.maxTime-this.totalTimer;
        if(this.fadeInTimer>0) this.fadeInTimer--;
        if(this.totalTimer<=this.fadeOutTimer) this.fadeOutTimer--;
        this.totalTimer--;
        return true;
    }

    /**
     * Gets parameter by name and tries to cast it. An empty optional is returned if the parameter does not exist or
     * the cast fails
     */
    public void setParameter(String name, Object val) {
        this.parameters.put(name,val);
    }

    public float getOpacity() {
        float def = getParameterAs("opacity",1f);
        if(this.fadeInTimer>0) return def*(1-(((float)this.fadeInTimer)/((float)this.maxFadeIn)));
        if(this.fadeOutTimer<this.maxFadeOut) return def*(((float)this.fadeOutTimer)/((float)this.maxFadeOut));
        return def;
    }

    public float scaleX(float resolutionX, float resolutionY) {
        return (resolutionY/resolutionX)*0.25f*getParameterAs("scale_x",1f);
    }

    public float scaleY() {
        return 0.25f*getParameterAs("scale_y",1f);
    }

    public int posX(MinecraftWindow window) {
        float width = window.getWidthF();
        float height = window.getHeightF();
        float scaleX  = scaleX(width,height);
        String alignment = getParameterAs("horizontal_alignment","center");
        int xOffset = alignment.matches("center") ? (int) ((width/2f)-(width*(scaleX/2f))) :
                alignment.matches("right") ? (int) (width-(width*(scaleX/2f))) : 0;
        return (int)((xOffset*(1/scaleX))+getParameterAs("x",0));
    }

    public int posY(float resolutionY) {
        float scaleY  = scaleY();
        String alignment = getParameterAs("vertical_alignment","center");
        int yOffset = alignment.matches("center") ? (int) (int) ((resolutionY/2f)-(resolutionY*(scaleY/2f))) :
                alignment.matches("top") ? 0 : (int) (resolutionY-(resolutionY*(scaleY/2f)));
        return (int)((yOffset*(1/scaleY))+getParameterAs("y",0));
    }

    public boolean canRender() {
        return this.totalTimer>0;
    }

    /**
     * Called when the timer is finished, or it can be called earlier if the image is no longer needed.
     */
    public void stop() {
        this.totalTimer = 0;
        this.maxTime = 0;
        this.fadeInTimer = 0;
        this.maxFadeIn = 0;
        this.fadeOutTimer = 0;
        this.maxFadeOut = 0;
    }

    abstract void render(MinecraftAPI mc);
}
