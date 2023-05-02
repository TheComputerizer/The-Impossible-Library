package mods.thecomputerizer.theimpossiblelibrary.util.client;

import net.minecraft.util.text.TextFormatting;

public class FontUtil {
    /**
         Converts a TextFormatting object into a single color integer with an optional alpha value
     */
    public static int convertTextFormatting(TextFormatting format, int a) {
        int r,b,g;
        switch (format) {
            case DARK_RED: return GuiUtil.makeRGBAInt(170,0,0,a);
            case RED: return GuiUtil.makeRGBAInt(255,85,85,a);
            case GOLD: return GuiUtil.makeRGBAInt(255,170,0,a);
            case YELLOW: return GuiUtil.makeRGBAInt(255,255,85,a);
            case DARK_GREEN: return GuiUtil.makeRGBAInt(0,170,0,a);
            case GREEN: return GuiUtil.makeRGBAInt(85,255,85,a);
            case AQUA: return GuiUtil.makeRGBAInt(85,255,255,a);
            case DARK_AQUA: return GuiUtil.makeRGBAInt(0,170,170,a);
            case DARK_BLUE: return GuiUtil.makeRGBAInt(0,0,170,a);
            case BLUE: return GuiUtil.makeRGBAInt(85,85,255,a);
            case LIGHT_PURPLE: return GuiUtil.makeRGBAInt(255,85,255,a);
            case DARK_PURPLE: return GuiUtil.makeRGBAInt(170,0,170,a);
            case GRAY: return GuiUtil.makeRGBAInt(170,170,170,a);
            case DARK_GRAY: return GuiUtil.makeRGBAInt(85,85,85,a);
            case BLACK: return GuiUtil.makeRGBAInt(0,0,0,a);
            default: return GuiUtil.makeRGBAInt(255,255,255,a);
        }
    }
}
