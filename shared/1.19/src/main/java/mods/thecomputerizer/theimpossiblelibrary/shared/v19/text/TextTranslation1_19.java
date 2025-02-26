package mods.thecomputerizer.theimpossiblelibrary.shared.v19.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.contents.TranslatableContents;

import java.util.Objects;

public class TextTranslation1_19 extends Text1_19 implements TextTranslationAPI<Style> {
    
    private final TextHelper1_19 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextTranslation1_19(TextHelper1_19 helper, String original, Object ... args) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        Object[] contentArgs = Objects.nonNull(args) ? args : new Object[]{};
        this.component = MutableComponent.create(new TranslatableContents(original,contentArgs));
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelper1_19 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public MutableComponent getComponent() {
        return this.component;
    }
    
    @Override public TextTranslation1_19 setStyle(Style style) {
        this.component.setStyle(style);
        return this;
    }
    
    @Override public TextTranslation1_19 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}