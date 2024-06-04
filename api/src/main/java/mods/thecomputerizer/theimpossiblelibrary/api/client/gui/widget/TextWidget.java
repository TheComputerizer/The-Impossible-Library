package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.MinecraftWindow;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
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
    
    protected TextBuffer text;
    
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
            double width = getWidth();
            double parentWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 0d;
            double height = getHeight();
            this.text.draw(ctx,getCenter(center.z),getMinX(center.x,width,parentWidth),getMinY(center.y,height),
                           getMaxX(center.x,width,parentWidth),getMaxY(center.y,height));
        }
    }
    
    @Override public double getHeight() {
        double maxWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 2d;
        return this.text.getHeight(RenderHelper.getContext(),maxWidth);
    }
    
    public double getMaxX(double centerX, double width, double parentWidth) {
        return centerX+((parentWidth>0d ? parentWidth : width)/2d);
    }
    
    public double getMaxY(double centerY, double height) {
        return centerY+(height/2d);
    }
    
    public double getMinX(double centerX, double width, double parentWidth) {
        return centerX-((parentWidth>0d ? parentWidth : width)/2d);
    }
    
    public double getMinY(double centerY, double height) {
        return centerY-(height/2d);
    }
    
    @Override public double getWidth() {
        double maxWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 2d;
        return this.text.getWidth(RenderHelper.getContext(),maxWidth);
    }
    
    @Override public TextBuffer getWrapped() {
        return this.text;
    }
    
    public boolean isBlank() {
        return Objects.isNull(this.text) || this.text.isBlank();
    }
    
    public boolean isNotBlank() {
        return Objects.nonNull(this.text) && !this.text.isBlank();
    }
    
    public boolean isNotEmpty() {
        return Objects.nonNull(this.text) && !this.text.isEmpty();
    }
    
    public boolean isEmpty() {
        return Objects.isNull(this.text) || this.text.isEmpty();
    }
    
    @Override public void onResolutionUpdated(MinecraftWindow window) {}
    
    public TextWidget setColor(ColorCache color) {
        this.text.setColor(color);
        return this;
    }
    
    public TextWidget setText(String text) {
        return setText(this.text.copyTo(TextHelper.getLiteral(text)));
    }
    
    public TextWidget setText(TextAPI<?> text) {
        return setText(this.text.copyTo(text));
    }
    
    public TextWidget setText(TextBuffer text) {
        this.text = text;
        return this;
    }
    
    @Override public void setWidth(double width) {
        this.text.setMaxWidth(RenderContext.get(ClientHelper.getMinecraft()),width);
    }
    
    public int textLength() {
        return toString().length();
    }
    
    @Override public String toString() {
        return String.valueOf(this.text);
    }
}