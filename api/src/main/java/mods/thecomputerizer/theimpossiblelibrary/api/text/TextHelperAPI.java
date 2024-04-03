package mods.thecomputerizer.theimpossiblelibrary.api.text;

public interface TextHelperAPI<STYLE> {

    TextStringAPI<STYLE> getLiteral(String text);

    TextTranslationAPI<STYLE> getTranslated(String key, Object ... args);
}