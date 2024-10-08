package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.Misc;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4f;
import org.joml.Vector4i;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.BLACK;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.WHITE;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Alignment.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

@SuppressWarnings("unused")
public class TextBuffer {
    
    public static final RenderShape highlightShape = RenderShape.from(ShapeHelper.plane(Y,0.01d),
                                                                      WHITE.withAlpha(0.5f));
    
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
    @Getter private final Alignment alignment;
    @Getter private final double lineSpacing;
    @Getter private final double scaleX;
    @Getter private final double scaleY;
    @Getter private final double translateX;
    @Getter private final double translateY;
    @Getter private final RenderShape blinkerShape;
    @Getter @Setter private ColorCache color;
    @Getter @Setter private int blinkerPos;
    @Getter @Setter private boolean blinkerVisible;
    @Getter @Setter private int highlightStart;
    @Getter @Setter private int highlightEnd;
    @Getter private boolean cached;
    private List<String> lineCache;
    private double widthCache;
    private double heightCache;

    private TextBuffer(TextAPI<?> text, Alignment alignment, ColorCache color, double lineSpacing, double scaleX,
            double scaleY, double translateX, double translateY) {
        this.text = Objects.nonNull(text) ? text : TextHelper.getLiteral(" ");
        this.alignment = Objects.nonNull(alignment) ? alignment : CENTER;
        this.color = Objects.nonNull(color) ? color : WHITE;
        this.lineSpacing = lineSpacing;
        this.scaleX = scaleX;
        this.scaleY = scaleY;
        this.translateX = translateX;
        this.translateY = translateY;
        double fontHeight = RenderHelper.getScaledFontHeight();
        this.blinkerShape = RenderShape.from(ShapeHelper.plane(Y,RenderHelper.getScaledStringWidth("|")/2d,
                                                               fontHeight),BLACK);
        this.blinkerPos = textLength();
        highlightShape.setHeight(fontHeight);
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
        return copyToBuilder(this.text).build();
    }
    
    public TextBuffer copyTo(TextAPI<?> text) {
        return copyToBuilder(text).build();
    }
    
    public Builder copyToBuilder() {
        return copyToBuilder(this.text);
    }
    
    public Builder copyToBuilder(TextAPI<?> text) {
        Builder builder = new Builder(text).setAlignment(this.alignment).setColor(this.color)
                .setLineSpacing(this.lineSpacing).setScale(this.scaleX,this.scaleY)
                .setTranslation(this.translateX,this.translateY);
        builder.blinkerPos = this.blinkerPos;
        builder.blinkerVisible = this.blinkerVisible;
        builder.highlightEnd = this.highlightEnd;
        builder.highlightStart = this.highlightStart;
        return builder;
    }
    
    public void decrementBlinkerPos() {
        this.blinkerPos--;
    }
    
    public void decrementHighlight() {
        if(this.highlightEnd<=this.highlightStart) this.highlightEnd = this.blinkerPos;
        this.highlightStart = this.blinkerPos-1;
    }
    
    public void draw(RenderContext ctx, Vector3d center, double minX, double minY, double maxX, double maxY) {
        double width = Math.abs(maxX-minX);
        if(!this.cached) cache(ctx,width);
        draw(ctx,center,this.lineCache,this.highlightStart,this.highlightEnd,minX,minY,width,Math.abs(maxY-minY));
    }
    
    private void draw(RenderContext ctx, Vector3d center, List<String> lines, int charStart, int charEnd, double left,
            double bottom, double width, double height) {
        double lineHeight = ctx.getScaledFontHeight();
        double offset = 0;
        int start = charStart;
        int end = charEnd;
        int blinker = this.blinkerPos;
        for(String line : lines) {
            int lineLength = line.length();
            draw(ctx,VectorHelper.copy3D(center),line,start,Math.min(charEnd,lineLength),blinker,lineHeight,left,bottom,
                 width,height,offset);
            offset+=(lineHeight+this.lineSpacing);
            if(start>=0) start-=lineLength;
            if(end>=0) end-=lineLength;
            if(blinker>=0) blinker-=lineLength;
        }
    }
    
    private void draw(RenderContext ctx, Vector3d center, String line, int charStart, int charEnd, int blinker,
            double lineHeight, double left, double bottom, double width, double height, double offset) {
        if(isTopAligned()) center.y = bottom+height;
        else if(isBottomAligned()) center.y = bottom+lineHeight;
        else center.y = bottom+(height/2d)+(lineHeight/2d);
        if(isLeftAligned()) center.x = left;
        else if(isRightAligned()) center.x = left-getWidth(ctx, width);
        else center.x = left+(width/2d);
        center.add(this.translateX,this.translateY-offset,0d);
        RenderAPI renderer = ctx.getRenderer();
        double scaledX = ctx.withScreenScaledX(center.x);
        double scaledY = ctx.withScreenScaledY(center.y);
        if(isLeftAligned() || isRightAligned())
            renderer.drawString(ctx.getFont(),line,scaledX,scaledY,this.color.getColorI());
        else {
            renderer.drawCenteredString(ctx.getFont(),line,scaledX,scaledY,this.color.getColorI());
            center.x-=(ctx.getScaledStringWidth(line)/2d);
        }
        double blinkerX = center.x;
        center.y-=(ctx.getScaledFontHeight()/2d);
        if(charStart>=0 && charStart<charEnd) {
            double highlightWidth = ctx.getScaledStringWidth(line.substring(charStart,charEnd));
            center.x+=(highlightWidth/2d);
            if(charStart>0) center.x+=ctx.getScaledStringWidth(line.substring(0,charStart));
            highlightShape.setWidth(highlightWidth);
            highlightShape.draw(ctx,center);
        }
        if(this.blinkerVisible && blinker>=0 && blinker<=line.length()) {
            center.x = blinkerX;
            if(blinker>0)
                center.x+=(ctx.getScaledStringWidth((line.substring(0,blinker)))-(this.blinkerShape.getWidth()/2d));
            this.blinkerShape.draw(ctx,center);
        }
    }
    
    public void flipBlinkerVisibility() {
        this.blinkerVisible = !this.blinkerVisible;
    }
    
    /**
     Returns -1 if the position is too far outside the drawn text or if the buffer is not yet cached.
     */
    public int getCharPos(RenderContext ctx, double x, double y, Vector3d center, double minX, double minY, double maxX, double maxY) {
        return this.cached ? getCharPos(ctx,x,y,center,this.lineCache,minX,minY,Math.abs(maxX-minX),Math.abs(maxY-minY)) : -1;
    }
    
    private int getCharPos(RenderContext ctx, double x, double y, Vector3d center, List<String> lines, double left,
            double bottom, double width, double height) {
        int pos = -1;
        double lineHeight = this.heightCache/lines.size();
        double offset = 0;
        int blinker = this.blinkerPos;
        for(String line : lines) {
            int lineLength = line.length();
            pos = getCharPos(ctx,x,y,VectorHelper.copy3D(center),line,lineHeight,left,bottom,width,height,offset);
            if(pos!=-1) break;
            offset+=(lineHeight);
        }
        return pos;
    }
    
    private int getCharPos(RenderContext ctx, double x, double y, Vector3d center, String line, double lineHeight,
            double left, double bottom, double width, double height, double offset) {
        if(isTopAligned()) center.y = bottom+height;
        else if(isBottomAligned()) center.y = bottom+lineHeight;
        else center.y = bottom+(height/2d)+(lineHeight/2d);
        double lineWidth = ctx.getScaledStringWidth(line);
        if(isLeftAligned()) center.x = left;
        else if(isRightAligned()) center.x = left-this.widthCache;
        else center.x = left+(width/2d)-(lineWidth/2d);
        center.add(this.translateX,this.translateY-offset,0d);
        if(x>=center.x && x<center.x+ctx.getScaledStringWidth(line+"|") && y<=center.y && y>center.y-lineHeight) {
            FontAPI<?> font = ctx.getFont();
            String trimmed = font.trimStringTo(line,font.getStringWidth(line)*((x-center.x)/lineWidth));
            return trimmed.length();
        }
        return -1;
    }
    
    public double getHeight(@Nullable RenderContext ctx, double maxWidth) {
        if(!this.cached && Objects.nonNull(ctx)) cache(ctx,maxWidth);
        return this.heightCache;
    }
    
    public String getHighlighted() {
        if(!isHighlighting()) return "";
        if(this.highlightEnd<this.highlightStart) {
            int i = this.highlightEnd;
            this.highlightEnd = this.highlightStart;
            this.highlightStart = i;
        }
        int length = this.highlightEnd-this.highlightStart;
        return toString().substring(this.highlightStart,this.highlightStart+length);
    }
    
    public double getLeft(Vector3d center) {
        return center.x-(this.cached ? this.widthCache/2d : 0d);
    }
    
    public double getWidth(@Nullable RenderContext ctx, double maxWidth) {
        if(!this.cached && Objects.nonNull(ctx)) cache(ctx,maxWidth);
        return this.widthCache;
    }
    
    public void incrementBlinkerPos() {
        this.blinkerPos++;
    }
    
    public void incrementHighlight() {
        if(this.highlightEnd<=this.highlightStart) this.highlightStart = this.blinkerPos;
        this.highlightEnd = this.blinkerPos+1;
    }
    
    public boolean isBlank() {
        return Objects.isNull(this.text) || this.text.isAppliedBlank();
    }
    
    public boolean isBottomAligned() {
        return Misc.equalsAny(this.alignment,BOTTOM_CENTER,BOTTOM_LEFT,BOTTOM_RIGHT);
    }
    
    public boolean isCenterAligned() {
        return Misc.equalsAny(this.alignment,BOTTOM_CENTER,CENTER,TOP_CENTER);
    }
    
    public boolean isEmpty() {
        return Objects.isNull(this.text) || this.text.isOriginalEmpty();
    }
    
    public boolean isHighlighting() {
        return this.highlightStart<0 || this.highlightEnd<0 || this.highlightEnd!=this.highlightStart;
    }
    
    public boolean isLeftAligned() {
        return Misc.equalsAny(this.alignment,BOTTOM_LEFT,LEFT,TOP_LEFT);
    }
    
    public boolean isRightAligned() {
        return Misc.equalsAny(this.alignment,BOTTOM_RIGHT,RIGHT,TOP_RIGHT);
    }
    
    public boolean isTopAligned() {
        return Misc.equalsAny(this.alignment,TOP_CENTER,TOP_LEFT,TOP_RIGHT);
    }
    
    public void setMaxWidth(RenderContext ctx, double maxWidth) {
        cache(ctx,maxWidth);
    }
    
    public int textLength() {
        return toString().length();
    }
    
    @Override public String toString() {
        return String.valueOf(this.text);
    }

    public static class Builder {

        private final TextAPI<?> text;
        private Alignment alignment;
        private ColorCache color;
        private double lineSpacing;
        private double scaleX;
        private double scaleY;
        private double translateX;
        private double translateY;
        private int blinkerPos;
        private boolean blinkerVisible;
        private int highlightStart;
        private int highlightEnd;

        public Builder(TextAPI<?> text) {
            this.text = text;
            this.scaleX = 1d;
            this.scaleY = 1d;
        }

        public TextBuffer build() {
            TextBuffer built = new TextBuffer(this.text,this.alignment,this.color,this.lineSpacing,this.scaleX,
                                              this.scaleY,this.translateX,this.translateY);
            if(this.blinkerPos>=0) {
                built.blinkerPos = MathHelper.clamp(this.blinkerPos,0,this.text.toString().length());
                built.blinkerVisible = this.blinkerVisible;
            }
            if(this.highlightStart>=0) built.highlightStart = this.highlightStart;
            if(this.highlightEnd>=0) built.highlightEnd = this.highlightEnd;
            return built;
        }
        
        public Builder setAlignment(Alignment alignment) {
            this.alignment = alignment;
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
            return setColor(ColorCache.of(r,g,b,a));
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
            return setColor(ColorCache.of(r,g,b,a));
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
    
    public enum Alignment {
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