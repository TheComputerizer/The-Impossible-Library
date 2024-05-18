package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import org.joml.Vector3d;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

public class TextWidget extends Widget implements Wrapped<TextBuffer> {
    
    @Getter @Setter private ColorCache color;
    @Setter private TextBuffer text;
    @Getter private FontAPI font;
    
    public TextWidget(TextBuffer text, double x, double y) {
        this(text,x,y,WHITE);
    }
    
    public TextWidget(TextBuffer text, double x, double y, ColorCache color) {
        this.color = color;
        this.text = text;
        this.x = MathHelper.clamp(x,-1d,1d);
        this.y = MathHelper.clamp(y,-1d,1d);
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.text)) {
            this.font = ctx.getFont();
            ctx.drawText(center,this.text,this.color);
        }
    }
    
    @Override public double getHeight() {
        return Objects.nonNull(this.font) ? this.font.getFontHeight() : 0d;
    }
    
    @Override public double getWidth() {
        return Objects.nonNull(this.font) && Objects.nonNull(this.text) ?
                this.font.getStringWidth(this.text.getText().getApplied()) : 0d;
    }
    
    @Override public TextBuffer getWrapped() {
        return this.text;
    }
}
