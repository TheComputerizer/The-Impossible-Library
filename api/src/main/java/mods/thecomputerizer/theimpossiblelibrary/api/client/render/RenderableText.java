package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Random;

/**
 * Used to simulate a title command but with more versatility
 */
public class RenderableText extends Renderable {
    private final List<?> potentialText;
    private final List<?> potentialSubtext;
    private String text = "";
    private String subtext = "";
    public RenderableText(Map<String, Object> parameters) {
        super(parameters);
        this.potentialText = getParameterAs("titles",new ArrayList<>());
        this.potentialSubtext = getParameterAs("subtitles",new ArrayList<>());
    }

    @Override
    public void initializeTimers(Random rand) {
        super.initializeTimers(rand);
        if(!this.potentialText.isEmpty())
            this.text = this.potentialText.get(rand.nextInt(this.potentialText.size())).toString();
        if(!this.potentialSubtext.isEmpty())
            this.subtext = this.potentialSubtext.get(rand.nextInt(this.potentialSubtext.size())).toString();
    }

    @Override
    public int posX(MinecraftWindow window) {
        float scaleX  = getParameterAs("scale_x",1f)*5f;
        String alignment = getParameterAs("horizontal_alignment","center");
        float width = window.getWidthF();
        int xOffset = alignment.matches("center") ? (int) ((width/2f)-(width*(scaleX/2f))) :
                alignment.matches("right") ? (int) (width-(width*(scaleX/2f))) : 0;
        return (int)((xOffset*(1/scaleX))+getParameterAs("x",0));
    }

    @Override
    public int posY(float resolutionY) {
        float scaleY  = getParameterAs("scale_y",1f)*5f;
        String alignment = getParameterAs("vertical_alignment","center");
        int yOffset = alignment.matches("center") ? (int) (int) ((resolutionY/2f)-(resolutionY*(scaleY/2f))) :
                alignment.matches("top") ? 0 : (int) (resolutionY-(resolutionY*(scaleY/2f)));
        return (int)((yOffset*(1/scaleY))+getParameterAs("y",0));
    }

    @Override
    public void render(MinecraftAPI mc) {
        if(canRender()) {
            float opacity = getOpacity();
            int fontHeight = mc.getFont().getFontHeight();
            TextBuffer title = createTitleBuffer(opacity,fontHeight);
            TextBuffer subtitle = createSubtitleBuffer(opacity,fontHeight);
            RenderHelper.drawMultiLineTitle(mc,title,subtitle, getParameterAs("centered",true),
                    posX(mc.getWindow()),posY(mc.getWindow().getHeightF()));
        }
    }

    private TextBuffer createSubtitleBuffer(float opacity, int spacing) {
        return new TextBuffer.Builder(this.subtext)
                .setScaleX(getParameterAs("subtitle_scale",0.75f))
                .setScaleY(getParameterAs("subtitle_scale",0.75f))
                .setColor(ColorHelper.getColorI(getParameterAs("subtitle_color","red"),opacity))
                .setSpacing(spacing)
                .build();
    }

    private TextBuffer createTitleBuffer(float opacity, int spacing) {
        return new TextBuffer.Builder(this.text)
                .setScaleX(getParameterAs("scale_x",1f)*5f)
                .setScaleY(getParameterAs("scale_y",1f)*5f)
                .setColor(ColorHelper.getColorI(getParameterAs("title_color","red"),opacity))
                .setSpacing(spacing*5)
                .build();
    }
}