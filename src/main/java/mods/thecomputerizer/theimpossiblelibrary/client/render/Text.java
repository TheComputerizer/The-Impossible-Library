package mods.thecomputerizer.theimpossiblelibrary.client.render;

import mods.thecomputerizer.theimpossiblelibrary.util.client.GuiUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.renderer.GlStateManager;

import java.util.Map;
import java.util.Objects;

/**
 * Used to simulate a title command but with more versatility
 */
public class Text extends Renderable {

    private final String text;
    private final String subtext;
    public Text(Map<String, Object> parameters) {
        super(parameters);
        this.text = getParameterAs("title","",String.class);
        this.subtext = getParameterAs("subtitle","",String.class);
    }

    @Override
    public void render(ScaledResolution res) {
        if(canRender()) GuiUtil.drawMultiLineTitle(res,this.text, this.subtext,
                getParameterAs("centered", true, Boolean.class),
                getParameterAs("x", 0, Integer.class),
                getParameterAs("y", 0, Integer.class),
                getParameterAs("scale_x", 1f, Float.class),
                getParameterAs("scale_y", 1f, Float.class),
                getParameterAs("subtitle_scale", 0.75f, Float.class),
                getParameterAs("title_color", "red", String.class),
                getParameterAs("subtitle_color", "white", String.class),getOpacity(),getOpacity(),
                Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT+Minecraft.getMinecraft().fontRenderer.FONT_HEIGHT/2);
    }
}
