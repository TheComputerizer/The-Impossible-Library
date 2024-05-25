package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Wrapped;
import org.joml.Vector3d;

import java.util.Objects;

@SuppressWarnings("unused")
public class TextWidget extends Widget implements Wrapped<TextBuffer> {
    
    public static TextWidget from(TextAPI<?> text) {
        return new TextWidget(TextBuffer.of(text),0d,0d);
    }
    
    public static TextWidget from(TextAPI<?> text, double x, double y) {
        return new TextWidget(TextBuffer.of(text),x,y);
    }
    
    public static TextWidget from(TextBuffer buffer) {
        return new TextWidget(buffer,0d,0d);
    }
    
    public static TextWidget from(TextBuffer buffer, double x, double y) {
        return new TextWidget(buffer,x,y);
    }
    
    public static TextWidget literal(String literal) {
        return new TextWidget(TextBuffer.literal(literal),0d,0d);
    }
    
    public static TextWidget literal(String literal, double x, double y) {
        return new TextWidget(TextBuffer.literal(literal),x,y);
    }
    
    public static TextWidget translated(String key) {
        return new TextWidget(TextBuffer.translated(key),0d, 0d);
    }
    
    public static TextWidget translated(String key, Object ... args) {
        return new TextWidget(TextBuffer.translated(key,args),0d, 0d);
    }
    
    public static TextWidget translated(String key,double x, double y) {
        return new TextWidget(TextBuffer.translated(key),x,y);
    }
    
    public static TextWidget translated(String key, Object[] args, double x, double y) {
        return new TextWidget(TextBuffer.translated(key,args),x,y);
    }
    
    private TextBuffer text;
    
    public TextWidget(TextBuffer text, double x, double y) {
        this.text = text;
        setX(x);
        setY(y);
    }
    
    public ColorCache getColor() {
        return this.text.getColor();
    }
    
    @Override public TextWidget copy() {
        return new TextWidget(this.text.copy(),this.x,this.y);
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.text)) {
            center = getCenter(center.z).add(center.x,center.y,0d);
            double minX = center.x-(Objects.nonNull(this.parent) ? this.parent.getWidth() : getWidth())/2d;
            double minY = center.y-getHeight()/2d;
            double maxX = center.x+(Objects.nonNull(this.parent) ? this.parent.getWidth() : getWidth())/2d;
            double maxY = center.y+getHeight()/2d;
            this.text.draw(ctx,getCenter(center.z),minX,minY,maxX,maxY);
        }
    }
    
    @Override public double getHeight() {
        double maxWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 2d;
        return this.text.getHeight(RenderHelper.getContext(),maxWidth);
    }
    
    @Override public double getWidth() {
        double maxWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 2d;
        return this.text.getWidth(RenderHelper.getContext(),maxWidth);
    }
    
    @Override public TextBuffer getWrapped() {
        return this.text;
    }
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {}
    
    public TextWidget setColor(ColorCache color) {
        this.text.setColor(color);
        return this;
    }
    
    public TextWidget setText(String text) {
        ColorCache color = getColor();
        return setText(TextBuffer.literal(text)).setColor(color);
    }
    
    public TextWidget setText(TextAPI<?> text) {
        ColorCache color = getColor();
        return setText(TextBuffer.of(text)).setColor(color);
    }
    
    public TextWidget setText(TextBuffer text) {
        this.text = text;
        return this;
    }
    
    @Override public void setWidth(double width) {
        this.text.setMaxWidth(RenderContext.get(ClientHelper.getMinecraft()),width);
    }
}