package mods.thecomputerizer.theimpossiblelibrary.api.text;

import org.apache.commons.lang3.StringUtils;

@SuppressWarnings("unused")
public interface TextAPI<S> {

    /**
     * Returns the applied string after stylization and transformations have been added
     */
    String getApplied();
    String getAppliedNoReset();

    TextHelperAPI<S> getHelper();

    /**
     * Returns the original input string
     */
    String getOriginal();
    
    default boolean isAppliedBlank() {
        return StringUtils.isBlank(getApplied());
    }
    
    default boolean isAppliedEmpty() {
        return StringUtils.isEmpty(getApplied());
    }
    
    default boolean isOriginalBlank() {
        return StringUtils.isBlank(getOriginal());
    }
    
    default boolean isOriginalEmpty() {
        return StringUtils.isEmpty(getOriginal());
    }

    TextAPI<S> setStyle(S style);
    TextAPI<S> withStyle(S style);
}
