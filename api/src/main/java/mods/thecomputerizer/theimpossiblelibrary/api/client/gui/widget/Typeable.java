package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;

public interface Typeable {
    
    boolean canBackspace();
    
    default boolean canCopy() {
        return true;
    }
    
    default boolean canCut() {
        return true;
    }
    
    default boolean canPaste(@Nullable String text) {
        return StringUtils.isNotEmpty(text);
    }
    
    boolean canType(char c);
    
    default boolean canSelectAll() {
        return true;
    }
    boolean onBackspace();
    boolean onKeyPressed(int keycode);
    boolean onCharTyped(char c);
    @Nullable String onCopy();
    @Nullable String onCut();
    boolean onPaste(@Nullable String text);
    boolean onSelectAll();
}