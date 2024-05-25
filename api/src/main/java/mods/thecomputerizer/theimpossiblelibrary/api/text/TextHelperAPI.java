package mods.thecomputerizer.theimpossiblelibrary.api.text;

public interface TextHelperAPI<S> {

    TextStringAPI<S> getLiteral(String text);
    TextStyleAPI<S> getStyle();
    TextTranslationAPI<S> getTranslated(String key, Object ... args);
}