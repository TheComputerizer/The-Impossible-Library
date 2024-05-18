package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.TextWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Builder;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper.ZERO_3D;

/**
 * Used to simulate a title command but with more versatility
 */
public class RenderableText extends Renderable {
    
    private final TextWidget titleWidget;
    private final TextWidget subtitleWidget;
    private final List<?> potentialText;
    private final List<?> potentialSubtext;
    private String text = "";
    private String subtext = "";
    
    public RenderableText(Map<String, Object> parameters) {
        super(parameters);
        this.titleWidget = new TextWidget(null,0d,0d);
        this.subtitleWidget = new TextWidget(null,0d,0d);
        this.potentialText = getParameterAs("titles",new ArrayList<>());
        this.potentialSubtext = getParameterAs("subtitles",new ArrayList<>());
    }

    @Override
    public void initializeTimers() {
        super.initializeTimers();
        if(!this.potentialText.isEmpty())
            this.text = String.valueOf(RandomHelper.getBasicRandomEntry(this.potentialText));
        if(!this.potentialSubtext.isEmpty())
            this.subtext = String.valueOf(RandomHelper.getBasicRandomEntry(this.potentialSubtext));
    }
    
    @Override public void pos(RenderContext ctx) {
        double x = getAllignmentX()+getParameterAs("x",0d);
        double y = getAllignmentY()+getParameterAs("y",0d);
        this.titleWidget.setX(x);
        this.titleWidget.setY(y);
        this.subtitleWidget.setX(x);
        this.subtitleWidget.setY(y+(this.titleWidget.getHeight()*1.5d));
    }
    
    @Override void render(RenderContext ctx) {
        if(canRender()) {
            pos(ctx);
            //scale(ctx);
            float opacity = getOpacity();
            int fontHeight = ctx.getFont().getFontHeight();
            this.titleWidget.setText(createTitleBuffer(opacity,fontHeight));
            this.titleWidget.setColor(this.titleWidget.getWrapped().getColor());
            this.subtitleWidget.setText(createSubtitleBuffer(opacity,fontHeight));
            this.subtitleWidget.setColor(this.subtitleWidget.getWrapped().getColor());
            this.titleWidget.draw(ctx,ZERO_3D,0d,0d);
            double subScale = getParameterAs("subtitle_scale",0.75d);
            ctx.getScale().modScales(subScale,subScale,1d);
            this.subtitleWidget.draw(ctx,ZERO_3D,0d,0d);
            ctx.getScale().modScales(1d/subScale,1d/subScale,1d);
        }
    }

    private TextBuffer createSubtitleBuffer(float opacity, int spacing) {
        return new Builder(TextHelper.getLiteral(this.subtext))
                .setColor(ColorHelper.getColor(getParameterAs("subtitle_color","red")).withAlpha(opacity))
                .setSpacing(spacing)
                .build();
    }

    private TextBuffer createTitleBuffer(float opacity, int spacing) {
        return new Builder(TextHelper.getLiteral(this.text))
                .setColor(ColorHelper.getColor(getParameterAs("title_color","red")).withAlpha(opacity))
                .setSpacing(spacing*5)
                .build();
    }
}