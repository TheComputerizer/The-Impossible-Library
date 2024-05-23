package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.TextWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Builder;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

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
        this.titleWidget = TextWidget.literal("");
        this.subtitleWidget = TextWidget.literal("");
        this.potentialText = getParameterAs("titles",new ArrayList<>());
        this.potentialSubtext = getParameterAs("subtitles",new ArrayList<>());
    }
    
    private TextBuffer createSubtitleBuffer(float opacity, int spacing) {
        return new Builder(TextHelper.getLiteral(this.subtext))
                .setColor(ColorHelper.getColor(getParameterAs("subtitle_color","white")).withAlpha(opacity))
                .setLineSpacing(spacing)
                .build();
    }
    
    private TextBuffer createTitleBuffer(float opacity, int spacing) {
        return new Builder(TextHelper.getLiteral(this.text))
                .setColor(ColorHelper.getColor(getParameterAs("title_color","red")).withAlpha(opacity))
                .setLineSpacing(spacing*5)
                .build();
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
            float opacity = Math.max(0.1f,getOpacity());
            int fontHeight = ctx.getFont().getFontHeight();
            this.titleWidget.setText(createTitleBuffer(opacity,fontHeight));
            this.titleWidget.setColor(this.titleWidget.getWrapped().getColor());
            this.subtitleWidget.setText(createSubtitleBuffer(opacity,fontHeight));
            this.subtitleWidget.setColor(this.subtitleWidget.getWrapped().getColor());
            ctx.getRenderer().pushMatrix();
            pos(ctx);
            scaleTitle(ctx,getParameterAs("scale_x",1f),getParameterAs("scale_y",1f));
            this.titleWidget.draw(ctx,VectorHelper.zero3D(),0d,0d);
            scaleSubtitle(ctx,getParameterAs("subtitle_scale",1f));
            this.subtitleWidget.draw(ctx,VectorHelper.zero3D(),0d,0d);
            ctx.getRenderer().popMatrix();
        }
    }
    
    protected void scaleSubtitle(RenderContext ctx, float scale) {
        scale*=0.75f;
        ctx.getRenderer().scale(scale,scale,scale);
        translateScaled(ctx,scale,scale);
    }
    
    protected void scaleTitle(RenderContext ctx, float scaleX, float scaleY) {
        scaleX*=5f;
        scaleY*=5f;
        ctx.getRenderer().scale(scaleX,scaleY,1f);
        translateScaled(ctx,scaleX,scaleY);
    }
}