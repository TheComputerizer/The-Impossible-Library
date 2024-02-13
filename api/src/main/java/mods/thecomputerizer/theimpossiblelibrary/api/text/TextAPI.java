package mods.thecomputerizer.theimpossiblelibrary.api.text;

public interface TextAPI<STYLE> {

    void applyStyle(STYLE style);

    /**
     * Returns the applied string after stylization and transformations have been added
     */
    String getApplied();

    /**
     * Returns the original input string
     */
    String getOriginal();
}
