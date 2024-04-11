package mods.thecomputerizer.theimpossiblelibrary.api.text;

public interface TextStyleAPI<S> {

    default TextAPI<S> apply(TextAPI<S> text, S style) {
        text.setStyle(style);
        return text;
    }

    default TextAPI<S> applyAqua(TextAPI<S> text) {
        return apply(text,aqua());
    }

    default TextAPI<S> applyBlack(TextAPI<S> text) {
        return apply(text,black());
    }

    default TextAPI<S> applyBlue(TextAPI<S> text) {
        return apply(text,blue());
    }

    default TextAPI<S> applyBold(TextAPI<S> text) {
        return apply(text,bold());
    }

    default TextAPI<S> applyDarkAqua(TextAPI<S> text) {
        return apply(text,darkAqua());
    }

    default TextAPI<S> applyDarkBlue(TextAPI<S> text) {
        return apply(text,darkBlue());
    }

    default TextAPI<S> applyDarkGray(TextAPI<S> text) {
        return apply(text,darkGray());
    }

    default TextAPI<S> applyDarkGreen(TextAPI<S> text) {
        return apply(text,darkGreen());
    }

    default TextAPI<S> applyDarkPurple(TextAPI<S> text) {
        return apply(text,darkPurple());
    }

    default TextAPI<S> applyDarkRed(TextAPI<S> text) {
        return apply(text,darkRed());
    }

    default TextAPI<S> applyGold(TextAPI<S> text) {
        return apply(text,gold());
    }

    default TextAPI<S> applyGray(TextAPI<S> text) {
        return apply(text,gray());
    }

    default TextAPI<S> applyGreen(TextAPI<S> text) {
        return apply(text,green());
    }

    default TextAPI<S> applyItalics(TextAPI<S> text) {
        return apply(text,italics());
    }

    default TextAPI<S> applyLightPurple(TextAPI<S> text) {
        return apply(text,lightPurple());
    }

    default TextAPI<S> applyObfuscated(TextAPI<S> text) {
        return apply(text,obfuscated());
    }

    default TextAPI<S> applyRed(TextAPI<S> text) {
        return apply(text,red());
    }

    default TextAPI<S> applyStrikethrough(TextAPI<S> text) {
        return apply(text,strikethrough());
    }

    default TextAPI<S> applyUnderline(TextAPI<S> text) {
        return apply(text,underline());
    }

    default TextAPI<S> applyWhite(TextAPI<S> text) {
        return apply(text,white());
    }

    default TextAPI<S> applyYellow(TextAPI<S> text) {
        return apply(text,yellow());
    }

    S aqua();
    S black();
    S blue();
    S bold();
    S darkAqua();
    S darkBlue();
    S darkGray();
    S darkGreen();
    S darkPurple();
    S darkRed();
    S gold();
    S gray();
    S green();
    S italics();
    S lightPurple();
    S obfuscated();
    S red();
    S strikethrough();
    S underline();
    S white();
    S yellow();
}