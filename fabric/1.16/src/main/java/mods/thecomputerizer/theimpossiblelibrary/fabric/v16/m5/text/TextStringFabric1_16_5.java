package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStringAPI;
import net.minecraft.network.chat.MutableComponent;
import net.minecraft.network.chat.Style;
import net.minecraft.network.chat.TextComponent;

import java.util.Objects;

public class TextStringFabric1_16_5 extends TextFabric1_16_5 implements TextStringAPI<Style> {
    
    private final TextHelperFabric1_16_5 helper;
    private final MutableComponent component;
    private final String original;
    
    public TextStringFabric1_16_5(TextHelperFabric1_16_5 helper, String original) {
        this.helper = helper;
        if(Objects.isNull(original)) original = "";
        this.original = original;
        this.component = new TextComponent(original);
    }
    
    @Override public String getApplied() {
        return this.component.getString();
    }
    
    @Override public TextHelperFabric1_16_5 getHelper() {
        return this.helper;
    }
    
    @Override public String getOriginal() {
        return this.original;
    }
    
    @Override public MutableComponent getComponent() {
        return this.component;
    }
    
    @Override public TextStringFabric1_16_5 setStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
    
    @Override public TextStringFabric1_16_5 withStyle(Style style) {
        this.component.withStyle(style);
        return this;
    }
}
