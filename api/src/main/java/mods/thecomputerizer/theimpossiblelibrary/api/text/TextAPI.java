package mods.thecomputerizer.theimpossiblelibrary.api.text;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

public interface TextAPI<S> {

    /**
     * Returns the applied string after stylization and transformations have been added
     */
    String getApplied();
    String getAppliedNoReset();
    <C> C getAsComponent();

    TextHelperAPI<S> getHelper();

    /**
     * Returns the original input string
     */
    String getOriginal();
    
    default boolean isAppliedBlank() {
        return TextHelper.isBlank(getApplied());
    }
    
    @IndirectCallers
    default boolean isAppliedEmpty() {
        return TextHelper.isEmpty(getApplied());
    }
    
    @IndirectCallers
    default boolean isOriginalBlank() {
        return TextHelper.isBlank(getOriginal());
    }
    
    default boolean isOriginalEmpty() {
        return TextHelper.isEmpty(getOriginal());
    }

    TextAPI<S> setStyle(S style);
    TextAPI<S> withStyle(S style);
}
