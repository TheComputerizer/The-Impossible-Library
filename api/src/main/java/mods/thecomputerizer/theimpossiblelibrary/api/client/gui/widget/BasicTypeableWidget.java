package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Allignment;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.MathHelper;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector2d;
import org.joml.Vector3d;

import javax.annotation.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.LEFT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.RIGHT;

@SuppressWarnings("unused")
public class BasicTypeableWidget extends TextWidget implements Tickable, Typeable {
    
    public static BasicTypeableWidget from(TextAPI<?> text) {
        return from(TextBuffer.of(text),0d,0d,-1);
    }
    
    public static BasicTypeableWidget from(TextAPI<?> text, ColorCache color) {
        return from(TextBuffer.getBuilder(text).setColor(color).build(),0d,0d,-1);
    }
    
    public static BasicTypeableWidget from(TextBuffer buffer) {
        return from(buffer,0d,0d,-1);
    }
    
    public static BasicTypeableWidget from(TextAPI<?> text, double x, double y) {
        return from(TextBuffer.of(text),x,y,-1);
    }
    
    public static BasicTypeableWidget from(TextAPI<?> text, ColorCache color, double x, double y) {
        return from(TextBuffer.getBuilder(text).setColor(color).build(),x,y,-1);
    }
    
    public static BasicTypeableWidget from(TextBuffer buffer, double x, double y) {
        return from(buffer,x,y,-1);
    }
    
    public static BasicTypeableWidget from(TextAPI<?> text, double x, double y, int charLimit) {
        return from(TextBuffer.of(text),x,y,charLimit);
    }
    
    public static BasicTypeableWidget from(TextAPI<?> text, ColorCache color, double x, double y, int charLimit) {
        return from(TextBuffer.getBuilder(text).setColor(color).build(),x,y,charLimit);
    }
    
    public static BasicTypeableWidget from(TextBuffer buffer, double x, double y, int charLimit) {
        return new BasicTypeableWidget(buffer,x,y,charLimit);
    }
    
    public static BasicTypeableWidget literal(String literal) {
        return from(TextBuffer.literal(literal),0d,0d,-1);
    }
    
    public static BasicTypeableWidget literal(String literal, ColorCache color) {
        return from(TextBuffer.literalBuilder(literal).setColor(color).build(),0d,0d,-1);
    }
    
    public static BasicTypeableWidget literal(String literal, double x, double y) {
        return from(TextBuffer.literal(literal),x,y,-1);
    }
    
    public static BasicTypeableWidget literal(String literal, ColorCache color, double x, double y) {
        return from(TextBuffer.literalBuilder(literal).setColor(color).build(),x,y,-1);
    }
    
    public static BasicTypeableWidget literal(String literal, double x, double y, int charLimit) {
        return from(TextBuffer.literal(literal),x,y,charLimit);
    }
    
    public static BasicTypeableWidget literal(String literal, ColorCache color, double x, double y, int charLimit) {
        return from(TextBuffer.literalBuilder(literal).setColor(color).build(),x,y,charLimit);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args) {
        return from(TextBuffer.translated(key,args),0d,0d,-1);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args, ColorCache color) {
        return from(TextBuffer.translatedBuilder(key,args).setColor(color).build(),0d,0d,-1);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args, double x, double y) {
        return from(TextBuffer.translated(key,args),x,y,-1);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args, ColorCache color, double x, double y) {
        return from(TextBuffer.translatedBuilder(key,args).setColor(color).build(),x,y,-1);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args, double x, double y, int charLimit) {
        return from(TextBuffer.translated(key,args),x,y,charLimit);
    }
    
    public static BasicTypeableWidget translated(String key, Object[] args, ColorCache color, double x, double y,
            int charLimit) {
        return from(TextBuffer.translatedBuilder(key,args).setColor(color).build(),x,y,charLimit);
    }
    
    @Getter protected final int charLimit;
    private final ShapeWidget highligher;
    @Getter protected int cursorPos;
    protected int highlightStart;
    protected int highlightEnd;
    protected String buffer;
    private boolean cursorVisible;
    private int cursorBlinkCounter;
    
    public BasicTypeableWidget(TextBuffer text, double x, double y, int charLimit) {
        super(text,x,y);
        this.charLimit = charLimit;
        this.highligher = ShapeWidget.from(ShapeHelper.plane(Y,new Vector2d(0d,0d),new Vector2d(0d,0d)),
                                           ColorHelper.reverse(getColor()),x,y);
        this.buffer = toString();
        this.cursorPos = textLength();
        if(!this.text.isLeftAlligned()) setText(text.copyToBuilder().setAllignment(Allignment.LEFT).build());
    }
    
    @Override public boolean canBackspace() {
        return isNotEmpty();
    }
    
    @Override public boolean canCopy() {
        return isNotEmpty() && isHighlighting();
    }
    
    @Override public boolean canCut() {
        return isNotEmpty() && isHighlighting();
    }
    
    @Override public boolean canPaste(@Nullable String text) {
        return StringUtils.isNotEmpty(text) && (this.charLimit<=0 || this.charLimit>textLength());
    }
    
    @Override public boolean canType(char c) {
        return this.charLimit<=0 || this.charLimit>textLength();
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        super.draw(ctx,center,mouseX,mouseY);
        if(isHighlighting() && Objects.nonNull(this.highligher)) this.highligher.draw(ctx,center,mouseX,mouseY);
    }
    
    public String getHighlightedText() {
        if(!isHighlighting()) return "";
        if(this.highlightEnd<this.highlightStart) {
            int i = this.highlightEnd;
            this.highlightEnd = this.highlightStart;
            this.highlightStart = i;
        }
        int length = this.highlightEnd-this.highlightStart;
        return this.buffer.substring(this.highlightStart,this.highlightStart+length);
    }
    
    @Override public boolean isActivelyTicking() {
        return true;
    }
    
    public boolean isHighlighting() {
        return this.highlightStart!=this.highlightEnd && this.highlightEnd>=0 && this.highlightStart>=0;
    }
    
    @Override public boolean onBackspace() {
        if(canBackspace()) {
            if(!isHighlighting()) {
                this.highlightEnd = textLength();
                this.highlightStart = this.highlightEnd-1;
            }
            onTextRemoved();
            return true;
        }
        return false;
    }
    
    @Override public boolean onCharTyped(char c) {
        if(canType(c)) {
            onTextAdded(String.valueOf(c));
            return true;
        }
        return false;
    }
    
    @Nullable @Override public String onCopy() {
        return canCopy() ? getHighlightedText() : null;
    }
    
    @Nullable @Override public String onCut() {
        return canCut() ? onTextRemoved() : null;
    }
    
    @Override public boolean onKeyPressed(int keyCode) {
        if(KeyHelper.isArrow(keyCode)) {
            if(keyCode==KeyHelper.getKeyCode(LEFT) && this.cursorPos>0) {
                this.cursorPos--;
                return true;
            }
            else if(keyCode==KeyHelper.getKeyCode(RIGHT) && this.cursorPos<textLength()) {
                this.cursorPos++;
                return true;
            }
        }
        return false;
    }
    
    @Override public boolean onPaste(@Nullable String text) {
        if(canPaste(text)) {
            onTextAdded(String.valueOf(text));
            return true;
        }
        return false;
    }
    
    @Override public boolean onSelectAll() {
        if(isEmpty()) return false;
        this.highlightStart = 0;
        this.highlightEnd = this.buffer.length();
        return true;
    }
    
    protected void onTextAdded(String text) {
        int cutLength = 0;
        int pasteLength = text.length();
        int textLength = textLength();
        if(isHighlighting()) {
            String cut = getHighlightedText();
            cutLength = cut.length();
            if(cutLength<textLength) {
                if(this.highlightStart>0) text = this.buffer.substring(0,this.highlightStart)+text;
                if(this.highlightEnd<textLength) text+=this.buffer.substring(this.highlightStart+cutLength);
            }
            resetHighlight();
        } else {
            if(this.cursorPos<textLength()) text+=this.buffer.substring(this.cursorPos-1);
            if(this.cursorPos>0) text = this.buffer.substring(0,this.cursorPos)+text;
        }
        setText(text);
        this.cursorPos+=(pasteLength-cutLength);
    }
    
    protected String onTextRemoved() {
        String cut = getHighlightedText();
        int cutLength = cut.length();
        int textLength = textLength();
        String s = "";
        if(cutLength<textLength) {
            if(this.highlightStart>0) s+=this.buffer.substring(0,this.highlightStart);
            if(this.highlightEnd<textLength) s+=this.buffer.substring(this.highlightStart+cutLength);
        }
        this.cursorPos = this.highlightStart;
        setText(s);
        resetHighlight();
        return cut;
    }
    
    @Override public void onTick() {
        this.cursorBlinkCounter++;
        if(this.cursorBlinkCounter==10) {
            this.cursorVisible = !this.cursorVisible;
            TILDev.logInfo("Previous text = {} | Previous buffer = {}",this.text,this.buffer);
            setText(this.buffer);
            TILDev.logInfo("New text = {} | New buffer = {}",this.text,this.buffer);
            this.cursorBlinkCounter = 0;
            TILDev.logInfo("Text length = {} | Buffer length = {}",this.text.textLength(),this.buffer.length());
        }
    }
    
    public void resetHighlight() {
        this.highlightStart = 0;
        this.highlightEnd = 0;
    }
    
    @Override public BasicTypeableWidget setColor(ColorCache color) {
        if(Objects.nonNull(this.highligher)) this.highligher.setColor(ColorHelper.reverse(color));
        return (BasicTypeableWidget)super.setColor(color);
    }
    
    @Override public BasicTypeableWidget setText(TextBuffer text) {
        String str = String.valueOf(text);
        if(str.length()>2) str = str.substring(0,str.length()-2);
        this.buffer = str;
        this.cursorPos = MathHelper.clamp(this.cursorPos,0,str.length());
        this.text = text.copyToBuilder(TextHelper.getLiteral(withCursor(str))).setAllignment(Allignment.LEFT).build();
        return this;
    }
    
    @Override public void setX(double x) {
        super.setX(x);
        if(Objects.nonNull(this.highligher)) this.highligher.setX(x);
    }
    
    @Override public void setY(double y) {
        super.setY(y);
        if(Objects.nonNull(this.highligher)) this.highligher.setY(y);
    }
    
    @Override public int textLength() {
        return Objects.nonNull(this.buffer) ? this.buffer.length() : 0;
    }
    
    protected String withCursor(String text) {
        return (this.cursorPos>0 ? text.substring(0,this.cursorPos) : "")+
               (this.cursorVisible ? "|" : " ")+
               (this.cursorPos<text.length() ? text.substring(Math.max(0,this.cursorPos-1)) : "");
    }
}