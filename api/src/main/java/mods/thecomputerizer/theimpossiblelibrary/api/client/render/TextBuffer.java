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

@SuppressWarnings("unused")  @Getter
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

    private final TextAPI<?> text;
    private final Allignment allignment;
    private final double lineSpacing;
    private final double scaleX;
    private final double scaleY;
    private final double translateX;
    private final double translateY;
    @Setter private ColorCache color;

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
    
    public void draw(RenderContext ctx, Vector3d center, double minX, double minY, double maxX, double maxY) {
        double width = Math.abs(maxX-minX);
        List<String> lines = toLines(ctx,width);
        draw(ctx,center,lines,minX,minY,width,Math.abs(maxY-minY));
    }
    
    private void draw(RenderContext ctx, Vector3d center, List<String> lines, double left, double bottom, double width,
            double height) {
        double lineHeight = getHeight(ctx);
        double offset = 0;
        for(String line : lines) {
            Vector3d next = VectorHelper.copy3D(center).sub(0d,offset,0d);
            if(next.y+(lineHeight/2d)<bottom) break;
            draw(ctx,next,line,lineHeight,left,bottom,width,height);
            offset+=(lineHeight+this.lineSpacing);
        }
    }
    
    private void draw(RenderContext ctx, Vector3d center, String line, double lineHeight, double left, double bottom,
            double width, double height) {
        if(isTopAlligned()) center.y = bottom+height;
        else if(isBottomAlligned()) center.y = bottom+lineHeight;
        else center.y = bottom+(height/2d)+(lineHeight/2d);
        if(isLeftAlligned()) center.x = left;
        else if(isRightAlligned()) center.x = left-getWidth(ctx,line);
        else center.x = left+(width/2d);
        center.add(this.translateX,this.translateY,0d);
        RenderAPI renderer = ctx.getRenderer();
        double scaledX = ctx.withScaledX(center.x);
        double scaledY = ctx.withScaledY(center.y);
        if(isLeftAlligned() || isRightAlligned()) renderer.drawString(ctx.getFont(),this,scaledX,scaledY);
        else renderer.drawCenteredString(ctx.getFont(),this,scaledX,scaledY);
    }
    
    public double getHeight(@Nullable RenderContext ctx, double maxWidth) {
        if(Objects.isNull(ctx)) return 0d;
        List<String> lines = toLines(ctx,maxWidth/this.scaleX);
        return (ctx.getScaledFontHeight()*this.scaleY*lines.size())+(Math.max(0,lines.size()-1)*this.lineSpacing);
    }
    
    private double getHeight(RenderContext ctx, List<String> lines) {
        return (ctx.getScaledFontHeight()*this.scaleY*lines.size())+(Math.max(0,lines.size()-1)*this.lineSpacing);
    }
    
    private double getHeight(RenderContext ctx) {
        return ctx.getScaledFontHeight()*this.scaleY;
    }
    
    public double getWidth(@Nullable RenderContext ctx, double maxWidth) {
        if(Objects.isNull(ctx)) return 0d;
        List<String> lines = toLines(ctx,maxWidth/this.scaleX);
        double width = 0d;
        for(String line : lines) {
            double lWidth = ctx.getScaledStringWidth(this.text.getApplied())*this.scaleX;
            if(lWidth>width) width = lWidth;
        }
        return width;
    }
    
    private double getWidth(RenderContext ctx, List<String> lines) {
        double width = 0d;
        for(String line : lines) {
            double lWidth = getWidth(ctx,line);
            if(lWidth>width) width = lWidth;
        }
        return width;
    }
    
    private double getWidth(RenderContext ctx, String line) {
        return ctx.getScaledStringWidth(this.text.getApplied())*this.scaleX;
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
    
    public List<String> toLines(RenderContext ctx, double width) {
        return ctx.splitLines(getText().getApplied(),width);
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