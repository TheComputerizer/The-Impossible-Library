package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyStateCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.apache.commons.lang3.StringUtils;
import org.joml.Vector3d;

import javax.annotation.Nullable;

import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.LEFT;
import static mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyAPI.Action.RIGHT;

@SuppressWarnings("unused")
public class BasicTypeableWidget extends TextWidget implements Clickable, Selectable, Tickable, Typeable {
    
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
    protected String buffer;
    protected int cursorBlinkCounter;
    @Getter @Setter protected ColorCache colorOverride;
    protected boolean selected = true;
    
    public BasicTypeableWidget(TextBuffer text, double x, double y, int charLimit) {
        super(text,x,y);
        this.charLimit = charLimit;
        this.buffer = toString();
        this.text.setBlinkerPos(this.buffer.length());
    }
    
    @Override public boolean canBackspace() {
        return canInteract(true) && (this.text.isHighlighting() || this.text.getBlinkerPos()>0);
    }
    
    @Override public boolean canCopy() {
        return canInteract(true) && this.text.isHighlighting();
    }
    
    @Override public boolean canCut() {
        return canInteract(true) && this.text.isHighlighting();
    }
    
    public boolean canInteract(boolean checkEmpty) {
        return isSelected() && (!checkEmpty || isNotEmpty());
    }
    
    @Override public boolean canPaste(@Nullable String text) {
        return canInteract(false) && StringUtils.isNotEmpty(text) &&
               (this.charLimit<=0 || this.charLimit>textLength());
    }
    
    @Override public boolean canType(char c) {
        return canInteract(false) && this.charLimit<=0 || this.charLimit>textLength();
    }
    
    @Override public BasicTypeableWidget copy() {
        BasicTypeableWidget copy = new BasicTypeableWidget(this.text.copy(),this.x,this.y,this.charLimit);
        copy.copyBasic(this);
        this.cursorBlinkCounter = copy.cursorBlinkCounter;
        this.selected = copy.selected;
        return copy;
    }
    
    @Override public void draw(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        if(Objects.nonNull(this.text)) {
            ColorCache color = this.text.getColor();
            if(Objects.nonNull(this.colorOverride)) this.text.setColor(this.colorOverride);
            super.draw(ctx,center,mouseX,mouseY);
            this.text.setColor(color);
        }
    }
    
    @Override public void drawSelected(RenderContext ctx, Vector3d center, double mouseX, double mouseY) {
        draw(ctx,center,mouseX,mouseY);
    }
    
    @Override public boolean isActivelyTicking() {
        return this.selected;
    }
    
    @Override public boolean isSelected() {
        return this.selected;
    }
    
    @Override public boolean onBackspace() {
        if(canBackspace()) {
            if(!this.text.isHighlighting()) {
                this.text.setHighlightEnd(this.text.getBlinkerPos());
                this.text.setHighlightStart(this.text.getBlinkerPos()-1);
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
        return canCopy() ? this.text.getHighlighted() : null;
    }
    
    @Nullable @Override public String onCut() {
        return canCut() ? onTextRemoved() : null;
    }
    
    @Override public boolean onKeyPressed(KeyStateCache cache, int keyCode) {
        if(canInteract(true) && KeyHelper.isArrow(keyCode)) {
            if(keyCode==KeyHelper.getKeyCode(LEFT) && this.text.getBlinkerPos()>0) {
                if(cache.isHoldingCtrl()) {
                    if(cache.isHoldingShift()) {
                        this.text.setHighlightStart(0);
                        this.text.setHighlightEnd(this.text.getBlinkerPos());
                    } else resetHighlight();
                    this.text.setBlinkerPos(0);
                } else {
                    if(cache.isHoldingShift()) this.text.decrementHighlight();
                    else resetHighlight();
                    this.text.decrementBlinkerPos();
                }
                return true;
            }
            else if(keyCode==KeyHelper.getKeyCode(RIGHT)) {
                int textLength = textLength();
                if(this.text.getBlinkerPos()<textLength) {
                    if(cache.isHoldingCtrl()) {
                        if(cache.isHoldingShift()) {
                            this.text.setHighlightStart(this.text.getBlinkerPos());
                            this.text.setHighlightEnd(textLength);
                        } else resetHighlight();
                        this.text.setBlinkerPos(textLength);
                    } else {
                        if(cache.isHoldingShift()) this.text.incrementHighlight();
                        else resetHighlight();
                        this.text.incrementBlinkerPos();
                    }
                }
                return true;
            }
        }
        return false;
    }
    
    @Override public boolean onLeftClick(double x, double y) {
        double width = getWidth();
        double parentWidth = Objects.nonNull(this.parent) ? this.parent.getWidth() : 0d;
        double height = getHeight();
        Vector3d center = getCenter(0d);
        int pos = this.text.getCharPos(RenderHelper.getContext(),x,y,getCenter(0d),
                getMinX(center.x,width,parentWidth),getMinY(center.y,height),getMaxX(center.x,width,parentWidth),
                                       getMaxY(center.y,height));
        if(pos!=-1) {
            this.selected = true;
            this.text.setBlinkerPos(pos);
            return true;
        }
        this.selected = false;
        this.text.setBlinkerVisible(false);
        return false;
    }
    
    @Override public boolean onPaste(@Nullable String text) {
        if(canPaste(text)) {
            onTextAdded(String.valueOf(text));
            return true;
        }
        return false;
    }
    
    @Override public boolean onRightClick(double x, double y) {
        return false;
    }
    
    @Override public void onScreenClosed() {
        this.cursorBlinkCounter = 0;
        this.selected = false;
    }
    
    @Override public boolean onSelectAll() {
        if(canInteract(true)) {
            this.text.setHighlightStart(0);
            this.text.setHighlightEnd(textLength());
            return true;
        }
        return false;
    }
    
    protected void onTextAdded(String text) {
        int pasteLength = text.length();
        int blinkerPos = this.text.getBlinkerPos();
        int newBlinkerPos = this.text.getBlinkerPos()+pasteLength;
        int textLength = textLength();
        if(this.text.isHighlighting()) {
            String cut = this.text.getHighlighted();
            int cutLength = cut.length();
            if(cutLength<textLength) {
                int highlightStart = this.text.getHighlightStart();
                if(highlightStart>0) text = this.buffer.substring(0,highlightStart)+text;
                if(this.text.getHighlightEnd()<textLength)
                    text+=this.buffer.substring(highlightStart+cutLength);
                if(highlightStart!=newBlinkerPos) newBlinkerPos-=(newBlinkerPos-highlightStart);
            }
            resetHighlight();
        } else {
            if(blinkerPos>0) text = this.buffer.substring(0,blinkerPos)+text;
            if(blinkerPos<textLength) text+=this.buffer.substring(blinkerPos);
        }
        this.text.setBlinkerPos(newBlinkerPos);
        setText(text);
    }
    
    protected String onTextRemoved() {
        String cut = this.text.getHighlighted();
        int cutLength = cut.length();
        int textLength = textLength();
        String s = "";
        if(cutLength<textLength) {
            if(this.text.getHighlightStart()>0) s+=this.buffer.substring(0,this.text.getHighlightStart());
            if(this.text.getHighlightEnd()<textLength)
                s+=this.buffer.substring(this.text.getHighlightStart()+cutLength);
        }
        this.text.setBlinkerPos(this.text.getHighlightStart());
        setText(s);
        resetHighlight();
        return cut;
    }
    
    @Override public void onTick() {
        this.cursorBlinkCounter++;
        if(this.cursorBlinkCounter==10) {
            this.text.flipBlinkerVisibility();
            setText(this.buffer);
            this.cursorBlinkCounter = 0;
        }
    }
    
    @Override public void playLeftClickSound() {}
    
    @Override public void playRightClickSound() {}
    
    public void resetHighlight() {
        this.text.setHighlightEnd(0);
        this.text.setHighlightStart(0);
    }
    
    @Override public BasicTypeableWidget setText(TextBuffer text) {
        this.text = text;
        String str = String.valueOf(text);
        this.buffer = str;
        int blinkerPos = this.text.getBlinkerPos();
        if(blinkerPos>str.length() || blinkerPos<0) blinkerPos = str.length();
        this.text.setBlinkerPos(blinkerPos);
        return this;
    }
}