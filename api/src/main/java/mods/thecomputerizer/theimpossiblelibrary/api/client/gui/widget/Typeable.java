package mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget;

import javax.annotation.Nullable;

public interface Typeable {
    
    boolean canType();
    boolean onBackspace();
    boolean onCharTyped(char c);
    @Nullable String onCopy();
    @Nullable String onCut();
    boolean onPaste(@Nullable String text);
    boolean onSelectAll();
}