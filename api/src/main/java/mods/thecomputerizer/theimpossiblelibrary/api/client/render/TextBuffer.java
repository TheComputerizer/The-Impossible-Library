package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4f;
import org.joml.Vector4i;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Allignment.*;

@SuppressWarnings("unused")
public class TextBuffer {
    
    public static Builder getBuilder(TextAPI<?> text) {
        return new Builder(text);
    }
    
    public static TextBuffer literal(String literal) {
        return new Builder(TextHelper.getLiteral(literal)).build();
    }
    
    public static Builder literalBuilder(String literal) {
        return new Builder(TextHelper.getLiteral(literal));
    }
    
    public static TextBuffer of(TextAPI<?> text) {
        return new Builder(text).build();
    }
    
    public static TextBuffer translated(String key, Object ... args) {
        return new Builder(TextHelper.getTranslated(key,args)).build();
    }
    
    public static Builder translatedBuilder(String key, Object ... args) {
        return new Builder(TextHelper.getTranslated(key,args));
    }

    @Getter private final TextAPI<?> text;
    @Getter private final Allignment allignment;
    @Getter private final double lineSpacing;
    @Getter private final double scaleX;
    @Getter private final double scaleY;
    @Getter private final double translateX;
    @Getter private final double translateY;
    @Getter @Setter private ColorCache color;
    @Getter private boolean cached;
    private List<String> lineCache;
    private double widthCache;
    private double heightCache;

    private TextBuffer(TextAPI<?> text, Allignment allignment, ColorCache color, double lineSpacing, double scaleX,
            double scaleY, double translateX, double translateY) {
        this.text = Objects.nonNull(text) ? text : TextHelper.getLiteral(" ");
        this.allignment = Objects.nonNull(allignment) ? allignment : CENTER;
        this.color = Objects.nonNull(color) ? color : WHITE;
        this.lineSpacing = lineSpacing;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
    }
    
    public void cache(RenderContext ctx, double maxWidth) {
        List<String> lines = ctx.splitLines(getText().getApplied(),maxWidth);
        this.heightCache = (lines.size()*ctx.getScaledFontHeight()*this.scaleY)+
                           (Math.max(0,lines.size()-1)*this.lineSpacing*this.scaleY);
        this.widthCache = lines.size()==1 ? ctx.getScaledStringWidth(lines.get(0)) : maxWidth;
        this.lineCache = lines;
        this.cached = true;
    }
    
    public TextBuffer copy() {
        return new TextBuffer(this.text,this.allignment,this.color,this.lineSpacing,this.scaleX,this.scaleY,
                              this.translateX,this.translateY);
    }
    
    public void draw(RenderContext ctx, Vector3d center, double minX, double minY, double maxX, double maxY) {
        double width = Math.abs(maxX-minX);
        if(!this.cached) cache(ctx,width);
        draw(ctx,center,this.lineCache,minX,minY,width,Math.abs(maxY-minY));
    }
    
    private void draw(RenderContext ctx, Vector3d center, List<String> lines, double left, double bottom, double width,
            double height) {
        double lineHeight = ctx.getScaledFontHeight();
        double offset = 0;
        for(String line : lines) {
            draw(ctx,VectorHelper.copy3D(center),line,lineHeight,left,bottom,width,height,offset);
            offset+=(lineHeight+this.lineSpacing);
            //if(center.y-offset+(lineHeight/2d)<bottom) break;
        }
    }
    
    private void draw(RenderContext ctx, Vector3d center, String line, double lineHeight, double left, double bottom,
            double width, double height, double offset) {
        if(isTopAlligned()) center.y = bottom+height;
        else if(isBottomAlligned()) center.y = bottom+lineHeight;
        else center.y = bottom+(height/2d)+(lineHeight/2d);
        if(isLeftAlligned()) center.x = left;
        else if(isRightAlligned()) center.x = left-getWidth(ctx,width);
        else center.x = left+(width/2d);
        center.add(this.translateX,this.translateY-offset,0d);
        RenderAPI renderer = ctx.getRenderer();
        double scaledX = ctx.withScreenScaledX(center.x);
        double scaledY = ctx.withScreenScaledY(center.y);
        if(isLeftAlligned() || isRightAlligned()) renderer.drawString(ctx.getFont(),line,scaledX,scaledY,this.color.getColorI());
        else renderer.drawCenteredString(ctx.getFont(),line,scaledX,scaledY,this.color.getColorI());
    }
    
    public double getHeight(@Nullable RenderContext ctx, double maxWidth) {
        if(!this.cached && Objects.nonNull(ctx)) cache(ctx,maxWidth);
        return this.heightCache;
    }
    
    public double getWidth(@Nullable RenderContext ctx, double maxWidth) {
        if(!this.cached && Objects.nonNull(ctx)) cache(ctx,maxWidth);
        return this.widthCache;
    }
    
    public boolean isBottomAlligned() {
        return Misc.equalsAny(this.allignment,BOTTOM_CENTER,BOTTOM_LEFT,BOTTOM_RIGHT);
    }
    
    public boolean isCenterAlligned() {
        return Misc.equalsAny(this.allignment,BOTTOM_CENTER,CENTER,TOP_CENTER);
    }
    
    public boolean isLeftAlligned() {
        return Misc.equalsAny(this.allignment,BOTTOM_LEFT,LEFT,TOP_LEFT);
    }
    
    public boolean isRightAlligned() {
        return Misc.equalsAny(this.allignment,BOTTOM_RIGHT,RIGHT,TOP_RIGHT);
    }
    
    public boolean isTopAlligned() {
        return Misc.equalsAny(this.allignment,TOP_CENTER,TOP_LEFT,TOP_RIGHT);
    }
    
    public void setMaxWidth(RenderContext ctx, double maxWidth) {
        cache(ctx,maxWidth);
    }

    public static class Builder {

        private final TextAPI<?> text;
        private Allignment allignment;
        private ColorCache color;
        private double lineSpacing;
        private double scaleX;
        private double scaleY;
        private double translateX;
        private double translateY;

        public Builder(TextAPI<?> text) {
            this.text = text;
            this.scaleX = 1d;
            this.scaleY = 1d;
        }

        public TextBuffer build() {
            return new TextBuffer(this.text,this.allignment,this.color,this.lineSpacing,this.scaleX, this.scaleY,
                                  this.translateX,this.translateY);
        }
        
        public Builder setAllignment(Allignment allignment) {
            this.allignment = allignment;
            return this;
        }

        /**
         * Assumes values from 0-1
         */
        public Builder setColor(Vector4f color) {
            return setColor(new ColorCache(color));
        }

        /**
         * Assumes values from 0-1
         */
        public Builder setColor(float r, float g, float b, float a) {
            return setColor(new ColorCache(r,b,g,a));
        }

        /**
         * Assumes values from 0-255
         */
        public Builder setColor(Vector4i color) {
            return setColor(new ColorCache(color));
        }

        /**
         * Assumes values from 0-255
         */
        public Builder setColor(int r, int g, int b, int a) {
            return setColor(new ColorCache(r,b,g,a));
        }
        
        /**
         * Assumes alpha from 0-1
         */
        public Builder setColor(ColorCache color, float alpha) {
            return setColor(color.withAlpha(alpha));
        }
        
        /**
         * Assumes alpha from 0-255
         */
        public Builder setColor(ColorCache color, int alpha) {
            return setColor(color.withAlpha(alpha));
        }

        public Builder setColor(ColorCache color) {
            this.color = color;
            return this;
        }
        
        public Builder setLineSpacing(double lineSpacing) {
            this.lineSpacing = lineSpacing;
            return this;
        }
        
        public Builder setScale(Vector2d scale) {
            this.scaleX = scale.x;
            this.scaleY = scale.y;
            return this;
        }
        
        public Builder setScale(double x, double y) {
            this.scaleX = x;
            this.scaleY = y;
            return this;
        }

        public Builder setScaleX(double scale) {
            this.scaleX = scale;
            return this;
        }

        public Builder setScaleY(double scale) {
            this.scaleY = scale;
            return this;
        }
        
        public Builder setTranslation(Vector2d translation) {
            this.translateX = translation.x;
            this.translateY = translation.y;
            return this;
        }
        
        public Builder setTranslation(double x, double y) {
            this.translateX = x;
            this.translateY = y;
            return this;
        }
        
        public Builder setTranslateX(double scale) {
            this.translateX = scale;
            return this;
        }
        
        public Builder setTranslateY(double scale) {
            this.translateY = scale;
            return this;
        }
    }
    
    public enum Allignment {
        BOTTOM_CENTER,
        BOTTOM_LEFT,
        BOTTOM_RIGHT,
        CENTER,
        LEFT,
        RIGHT,
        TOP_CENTER,
        TOP_LEFT,
        TOP_RIGHT
    }
}