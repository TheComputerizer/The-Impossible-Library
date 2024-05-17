package mods.thecomputerizer.theimpossiblelibrary.api.text;

@SuppressWarnings("unused")
public interface TextAPI<S> {

    /**
     * Returns the applied string after stylization and transformations have been added
     */
    String getApplied();

    TextHelperAPI<S> getHelper();

    /**
     * Returns the original input string
     */
    String getOriginal();

    TextAPI<S> setStyle(S style);
    TextAPI<S> withStyle(S style);
}
