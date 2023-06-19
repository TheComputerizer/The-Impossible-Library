package mods.thecomputerizer.theimpossiblelibrary.client.render;


import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.gui.GuiGraphics;

import java.util.List;
import java.util.Map;
import java.util.Objects;

public abstract class Renderable {

    private final Map<String, Object> parameters;
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
    public <T> T getParameterAs(String name, T defVal, Class<T> genericClass) {
        try {
            Object val = this.parameters.get(name);
            if(Objects.isNull(val)) return defVal;
            if(val instanceof List<?>) return genericClass.cast(val);
            if(defVal instanceof String) return (T)val.toString();
            if(defVal instanceof Boolean) return (T)(Object)Boolean.parseBoolean(val.toString());
            if(defVal instanceof Number) {
                String num = val.toString();
                return (T)(defVal instanceof Long ? Long.parseLong(num) : defVal instanceof Integer ? Integer.parseInt(num) :
                        defVal instanceof Double ? Double.parseDouble(num) : defVal instanceof Float ? Float.parseFloat(num) :
                                defVal instanceof Byte ? Byte.parseByte(num) : defVal);
            }
        } catch (ClassCastException ignored) {}
        return defVal;
    }
    public void initializeTimers() {
        this.maxTime = getParameterAs("time",100L,Long.class);
        this.totalTimer = getParameterAs("time",100L,Long.class);
        this.maxFadeIn = getParameterAs("fade_in",20L,Long.class);
        this.fadeInTimer = getParameterAs("fade_in",20L,Long.class);
        this.maxFadeOut = getParameterAs("fade_out",20L,Long.class);
        this.fadeOutTimer = getParameterAs("fade_out",20L,Long.class);
    }

    public boolean tick() {
        if(this.totalTimer<=0) {
            if(getParameterAs("loop",false,Boolean.class)) {
                initializeTimers();
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
        float def = getParameterAs("opacity",1f, Float.class);
        if(this.fadeInTimer>0) return def*(1-(((float)this.fadeInTimer)/((float)this.maxFadeIn)));
        if(this.fadeOutTimer<this.maxFadeOut) return def*(((float)this.fadeOutTimer)/((float)this.maxFadeOut));
        return def;
    }

    public float scaleX(float resolutionX, float resolutionY) {
        return (resolutionY/resolutionX)*0.25f*getParameterAs("scale_x",1f, Float.class);
    }

    public float scaleY() {
        return 0.25f*getParameterAs("scale_y",1f, Float.class);
    }

    public int posX(float resolutionX, float resolutionY) {
        float scaleX  = scaleX(resolutionX, resolutionY);
        String alignment = getParameterAs("horizontal_alignment","center",String.class);
        int xOffset = alignment.matches("center") ? (int) ((resolutionX/2f)-(resolutionX*(scaleX/2f))) :
                alignment.matches("right") ? (int) (resolutionX-(resolutionX*(scaleX/2f))) : 0;
        return (int)((xOffset*(1/scaleX))+getParameterAs("x",0, Integer.class));
    }

    public int posY(float resolutionY) {
        float scaleY  = scaleY();
        String alignment = getParameterAs("vertical_alignment","center",String.class);
        int yOffset = alignment.matches("center") ? (int) (int) ((resolutionY/2f)-(resolutionY*(scaleY/2f))) :
                alignment.matches("top") ? 0 : (int) (resolutionY-(resolutionY*(scaleY/2f)));
        return (int)((yOffset*(1/scaleY))+getParameterAs("y",0, Integer.class));
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

    abstract void render(GuiGraphics graphics, Window res);
}