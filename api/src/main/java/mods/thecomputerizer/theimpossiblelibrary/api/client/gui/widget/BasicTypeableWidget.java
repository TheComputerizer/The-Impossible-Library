package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.input.KeyStateCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorCache;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextBuffer.Alignment;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

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
    protected String buffer;
    private int cursorBlinkCounter;
    
    public BasicTypeableWidget(TextBuffer text, double x, double y, int charLimit) {
        super(text,x,y);
        this.charLimit = charLimit;
        if(!this.text.isLeftAligned()) setText(text.copyToBuilder().setAlignment(Alignment.LEFT).build());
        this.buffer = toString();
        this.text.setBlinkerPos(this.buffer.length());
    }
    
    @Override public boolean canBackspace() {
        return isNotEmpty() && (this.text.isHighlighting() || this.text.getBlinkerPos()>0);
    }
    
    @Override public boolean canCopy() {
        return isNotEmpty() && this.text.isHighlighting();
    }
    
    @Override public boolean canCut() {
        return isNotEmpty() && this.text.isHighlighting();
    }
    
    @Override public boolean canPaste(@Nullable String text) {
        return StringUtils.isNotEmpty(text) && (this.charLimit<=0 || this.charLimit>textLength());
    }
    
    @Override public boolean canType(char c) {
        return this.charLimit<=0 || this.charLimit>textLength();
    }
    
    @Override public boolean isActivelyTicking() {
        return true;
    }
    
    @Override public boolean onBackspace() {
        if(canBackspace()) {
            if(!this.text.isHighlighting()) {
                int textLength = textLength();
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
        if(KeyHelper.isArrow(keyCode)) {
            if(keyCode==KeyHelper.getKeyCode(LEFT) && this.text.getBlinkerPos()>0) {
                if(cache.isHoldingCtrl()) this.text.setBlinkerPos(0);
                else {
                    if(cache.isHoldingShift()) this.text.decrementHighlight();
                    this.text.decrementBlinkerPos();
                }
                return true;
            }
            else if(keyCode==KeyHelper.getKeyCode(RIGHT)) {
                int textLength = textLength();
                if(this.text.getBlinkerPos()<textLength) {
                    if(cache.isHoldingCtrl()) this.text.setBlinkerPos(textLength);
                    else {
                        if(cache.isHoldingShift()) this.text.incrementHighlight();
                        this.text.incrementBlinkerPos();
                    }
                }
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
        this.text.setHighlightStart(0);
        this.text.setHighlightEnd(textLength());
        return true;
    }
    
    protected void onTextAdded(String text) {
        int cutLength = 0;
        int pasteLength = text.length();
        int textLength = textLength();
        if(this.text.isHighlighting()) {
            String cut = this.text.getHighlighted();
            cutLength = cut.length();
            if(cutLength<textLength) {
                if(this.text.getHighlightStart()>0) text = this.buffer.substring(0,this.text.getHighlightStart())+text;
                if(this.text.getHighlightEnd()<textLength)
                    text+=this.buffer.substring(this.text.getHighlightStart()+cutLength);
            }
            resetHighlight();
        } else {
            if(this.text.getBlinkerPos()<textLength()) text+=this.buffer.substring(this.text.getBlinkerPos()-1);
            if(this.text.getBlinkerPos()>0) text = this.buffer.substring(0,this.text.getBlinkerPos())+text;
        }
        setText(text);
        this.text.setBlinkerPos(this.text.getBlinkerPos()+pasteLength-cutLength);
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
            TILDev.logInfo("Text length = {} | Buffer length = {} | Blinker Pos = {} | Blinker visible = {} | " +
                           "Highlight start = {} | Highlight end = {}",this.text.textLength(),
                           this.buffer.length(),this.text.getBlinkerPos(),this.text.isBlinkerVisible(),
                           this.text.getHighlightStart(),this.text.getHighlightEnd());
        }
    }
    
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