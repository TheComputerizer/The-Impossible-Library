package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import org.joml.Vector3d;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;

@SuppressWarnings("unused")
public class TextWidget extends Widget implements Wrapped<TextBuffer> {
    
    public static TextWidget from(TextAPI<?> text) {
        return new TextWidget(TextBuffer.of(text),WHITE,0d,0d);
    }
    
    public static TextWidget from(TextAPI<?> text, double x, double y) {
        return new TextWidget(TextBuffer.of(text),WHITE,x,y);
    }
    
    public static TextWidget from(TextAPI<?> text, ColorCache color) {
        return new TextWidget(TextBuffer.of(text),color,0d,0d);
    }
    
    public static TextWidget from(TextAPI<?> text, ColorCache color, double x, double y) {
        return new TextWidget(TextBuffer.of(text),color,x,y);
    }
    
    public static TextWidget from(TextBuffer buffer) {
        return new TextWidget(buffer,WHITE,0d,0d);
    }
    
    public static TextWidget from(TextBuffer buffer, double x, double y) {
        return new TextWidget(buffer,WHITE,x,y);
    }
    
    public static TextWidget from(TextBuffer buffer, ColorCache color) {
        return new TextWidget(buffer,color,0d,0d);
    }
    
    public static TextWidget from(TextBuffer buffer, ColorCache color, double x, double y) {
        return new TextWidget(buffer,color,x,y);
    }
    
    public static TextWidget literal(String literal) {
        return new TextWidget(TextBuffer.literal(literal),WHITE,0d,0d);
    }
    
    public static TextWidget literal(String literal, double x, double y) {
        return new TextWidget(TextBuffer.literal(literal),WHITE,x,y);
    }
    
    public static TextWidget literal(String literal, ColorCache color) {
        return new TextWidget(TextBuffer.literal(literal),color,0d,0d);
    }
    
    public static TextWidget literal(String literal, ColorCache color, double x, double y) {
        return new TextWidget(TextBuffer.literal(literal),color,x,y);
    }
    
    public static TextWidget translated(String key, Object ... args) {
        return new TextWidget(TextBuffer.translated(key,args),WHITE,0d,0d);
    }
    
    public static TextWidget translated(String key, double x, double y) {
        return new TextWidget(TextBuffer.translated(key),WHITE,x,y);
    }
    
    public static TextWidget translated(String key, Object[] args, double x, double y) {
        return new TextWidget(TextBuffer.translated(key,args),WHITE,x,y);
    }
    
    public static TextWidget translated(String key, ColorCache color) {
        return new TextWidget(TextBuffer.translated(key),color,0d, 0d);
    }
    
    public static TextWidget translated(String key, Object[] args, ColorCache color) {
        return new TextWidget(TextBuffer.translated(key,args),color,0d, 0d);
    }
    
    public static TextWidget translated(String key, ColorCache color, double x, double y) {
        return new TextWidget(TextBuffer.translated(key),color,x,y);
    }
    
    public static TextWidget translated(String key, Object[] args, ColorCache color, double x, double y) {
        return new TextWidget(TextBuffer.translated(key,args),color,x,y);
    }
    
    @Getter private ColorCache color;
    private TextBuffer text;
    
    public TextWidget(TextBuffer text, ColorCache color, double x, double y) {
        this.color = color;
        this.text = text;
        setX(MathHelper.clamp(x,-1d,1d));
        setY(MathHelper.clamp(y,-1d,1d));
    }
    
    @Override public TextWidget copy() {
        TextWidget copy = new TextWidget(this.text,this.color,this.x,this.y);
        copy.height = this.height;
        copy.width = this.width;
        return copy;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.text))
            ctx.drawText(center.add(0d,getHeight()/2d,0d,new Vector3d()),this.text,this.color);
    }
    
    @Override public double getHeight() {
        RenderContext ctx = RenderHelper.getContext();
        return Objects.nonNull(ctx) ? ctx.getScaledFontHeight() : 0d;
    }
    
    @Override public double getWidth() {
        RenderContext ctx = RenderHelper.getContext();
        return Objects.nonNull(ctx) && Objects.nonNull(this.text) ?
                ctx.getScaledStringWidth(this.text.getText().getApplied()) : 0d;
    }
    
    @Override public TextBuffer getWrapped() {
        return this.text;
    }
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {}
    
    public TextWidget setColor(ColorCache color) {
        this.color = color;
        return this;
    }
    
    public TextWidget setText(String text) {
        return setText(TextBuffer.literal(text));
    }
    
    public TextWidget setText(TextAPI<?> text) {
        return setText(TextBuffer.of(text));
    }
    
    public TextWidget setText(TextBuffer text) {
        this.text = text;
        return this;
    }
}