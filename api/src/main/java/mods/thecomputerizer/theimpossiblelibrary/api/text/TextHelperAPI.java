package mods.thecomputerizer.theimpossiblelibrary.api.text;

public interface TextHelperAPI<S> {

    TextStringAPI<?> getLiteral(String text);
    TextStyleAPI<S> getStyle();
    TextTranslationAPI<?> getTranslated(String key, Object ... args);
}