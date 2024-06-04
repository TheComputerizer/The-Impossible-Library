package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import org.joml.Vector4f;
import org.joml.Vector4i;

/**
 * Reverses a color vector
 * All color values/vectors stored as floats are assumed to be from 0-1
 * All color values/vectors stored as ints are assumed to be from 0-255
 */
@SuppressWarnings("unused")
public class ColorHelper {

    public static final ColorCache AQUA = ColorCache.of(85,255,255);
    public static final ColorCache BLACK = ColorCache.grayscale(0);
    public static final ColorCache BLUE = ColorCache.of(85,85,255);
    public static final ColorCache DARK_AQUA = ColorCache.of(0,170,170);
    public static final ColorCache DARK_BLUE = ColorCache.of(0,0,170);
    public static final ColorCache DARK_GRAY = ColorCache.grayscale(85);
    public static final ColorCache DARK_GREEN = ColorCache.of(0,170,0);
    public static final ColorCache DARK_PURPLE = ColorCache.of(170,0,170);
    public static final ColorCache DARK_RED = ColorCache.of(170,0,0);
    public static final ColorCache GOLD = ColorCache.of(255,170,0);
    public static final ColorCache GRAY = ColorCache.grayscale(170);
    public static final ColorCache GREEN = ColorCache.of(85,255,85);
    public static final ColorCache LIGHT_PURPLE = ColorCache.of(255,85,255);
    public static final ColorCache RED = ColorCache.of(255,85,85);
    public static final ColorCache WHITE = ColorCache.grayscale(255);
    public static final ColorCache YELLOW = ColorCache.of(255,255,85);

    /**
     * Converts a color vector stored as 0-255 to a color vector stored as 0-1
     */
    public static Vector4f convert(Vector4i color) {
        return new Vector4f(((float)color.x)/255f,((float)color.y)/255f,
                ((float)color.z)/255f,((float)color.w)/255f);
    }

    /**
     * Converts a color vector stored as 0-1 to a color vector stored as 0-255
     */
    public static Vector4i convert(Vector4f color) {
        return new Vector4i((int)(color.x*255f),(int)(color.y*255f),(int)(color.z*255f),(int)(color.w*255f));
    }
    
    /**
     * Extracts a color that has been encoded as an integer
     */
    public static ColorCache decode(int encoded) {
        return ColorCache.of((encoded>>16)&0xFF,(encoded>>8)&0xFF,encoded&0xFF,(encoded>>24)&0xFF);
    }
    
    /**
     * Extracts a color that has been encoded as an integer and applies the alpha override
     */
    public static ColorCache decode(int encoded, float alphaOverride) {
        return ColorCache.of((encoded>>16)&0xFF,(encoded>>8)&0xFF,encoded&0xFF,alphaOverride);
    }
    
    /**
     * Extracts a color that has been encoded as an integer and applies the alpha override
     */
    public static ColorCache decode(int encoded, int alphaOverride) {
        return ColorCache.of((encoded>>16)&0xFF,(encoded>>8)&0xFF,encoded&0xFF,alphaOverride);
    }

    /**
     * Gets a cached color by name
     */
    public static ColorCache getColor(String color) {
        switch(color.toLowerCase()) {
            case "aqua": return AQUA;
            case "black": return BLACK;
            case "blue": return BLUE;
            case "dark_aqua": return DARK_AQUA;
            case "dark_blue": return DARK_BLUE;
            case "dark_gray": return DARK_GRAY;
            case "dark_green": return DARK_GREEN;
            case "dark_purple": return DARK_PURPLE;
            case "dark_red": return DARK_RED;
            case "gold": return GOLD;
            case "gray": return GRAY;
            case "green": return GREEN;
            case "light_purple": return LIGHT_PURPLE;
            case "red": return RED;
            case "yellow": return YELLOW;
            default: return WHITE;
        }
    }

    public static int getColorI(String color) {
        return getColor(color).getColorI();
    }

    public static int getColorI(String color, float alpha) {
        return getColor(color).getIntWithAlpha(alpha);
    }

    public static int getColorI(String color, int alpha) {
        return getColor(color).getIntWithAlpha(alpha);
    }

    public static Vector4f getColorVF(String color) {
        return getColor(color).getColorVF();
    }

    public static Vector4f getColorVF(String color, float alpha) {
        return getColor(color).getVFWithAlpha(alpha);
    }

    public static Vector4f getColorVF(String color, int alpha) {
        return getColor(color).getVFWithAlpha(alpha);
    }

    public static Vector4i getColorVI(String color) {
        return getColor(color).getColorVI();
    }

    public static Vector4i getColorVI(String color, float alpha) {
        return getColor(color).getVIWithAlpha(alpha);
    }

    public static Vector4i getColorVI(String color, int alpha) {
        return getColor(color).getVIWithAlpha(alpha);
    }

    /**
     * Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Vector4f colors) {
        return makeRGBAInt(colors.x(),colors.y(),colors.z(),colors.w());
    }

    /**
     * Converts a color tuple into a single integer
     */
    public static int makeRGBAInt(Vector4i colors) {
        return makeRGBAInt(colors.x(),colors.y(),colors.z(),colors.w());
    }

    /**
     * Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(float r, float g, float b, float a) {
        return makeRGBAInt((int)(r*255f),(int)(g*255f),(int)(b*255f),(int)(a*255f));
    }

    /**
     * Converts rgba integers into a single color integer
     */
    public static int makeRGBAInt(int r, int g, int b, int a) {
        return ((a&0xFF)<<24) | ((r&0xFF)<<16) | ((g&0xFF)<<8) | (b&0xFF);
    }
    
    public static ColorCache reverse(ColorCache color) {
        return new ColorCache(reverse(color.getColorVF()));
    }
    
    public static ColorCache reverse(ColorCache color, float alpha) {
        return reverse(color).withAlpha(alpha);
    }
    
    public static ColorCache reverse(ColorCache color, int alpha) {
        return reverse(color).withAlpha(alpha);
    }

    /**
     * Reverses a color vector
     */
    public static Vector4f reverse(Vector4f colors) {
        return new Vector4f(Math.abs(colors.x()-1f),Math.abs(colors.y()-1f),Math.abs(colors.z()-1f),colors.w());
    }

    /**
     * Reverses a color vector
     */
    public static Vector4i reverse(Vector4i colors) {
        return new Vector4i(Math.abs(colors.x()-255),Math.abs(colors.y()-255),Math.abs(colors.z()-255),colors.w());
    }

    /**
     * Sets the global state color
     */
    public static void setStateColor(RenderAPI renderer, Vector4f color) {
        setStateColor(renderer,color.x,color.y,color.z,color.w);
    }

    /**
     * Sets the global state color
     */
    public static void setStateColor(RenderAPI renderer, Vector4i color) {
        setStateColor(renderer,color.x,color.y,color.z,color.w);
    }

    /**
     * Sets the global state color
     */
    public static void setStateColor(RenderAPI renderer, int r, int g, int b, int a) {
        setStateColor(renderer,((float)r)/255f,((float)g)/255f,((float)b)/255f,((float)a)/255f);
    }

    /**
     * Sets the global state color
     */
    public static void setStateColor(RenderAPI renderer, float r, float g, float b, float a) {
        renderer.setColor(r,g,b,a);
    }
}
