package mods.thecomputerizer.theimpossiblelibrary.shared.v21.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Objects;

public class TextTranslation1_21 extends Text1_21 implements TextTranslationAPI<Style> {
    
    private final TextHelper1_21 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextTranslation1_21(TextHelper1_21 helper, String original, Object ... args) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        Object[] contentArgs = Objects.nonNull(args) ? args : new Object[]{};
        for(int i=0;i<contentArgs.length;i++) contentArgs[i] = String.valueOf(contentArgs[i]);
        this.component = MutableComponent.create(new TranslatableContents(original,original,contentArgs));
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelper1_21 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public MutableComponent getComponent() {
        return this.component;
    }
    
    @Override public TextTranslation1_21 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextTranslation1_21 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}