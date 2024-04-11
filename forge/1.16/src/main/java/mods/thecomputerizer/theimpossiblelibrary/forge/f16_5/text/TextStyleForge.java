package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

public class TextStyleForge implements TextStyleAPI<Style> {

    private static final Style AQUA = Style.EMPTY.withColor(TextFormatting.AQUA);
    private static final Style BLACK = Style.EMPTY.withColor(TextFormatting.BLACK);
    private static final Style BLUE = Style.EMPTY.withColor(TextFormatting.BLUE);
    private static final Style BOLD = Style.EMPTY.withBold(true);
    private static final Style DARK_AQUA = Style.EMPTY.withColor(TextFormatting.DARK_AQUA);
    private static final Style DARK_BLUE = Style.EMPTY.withColor(TextFormatting.DARK_BLUE);
    private static final Style DARK_GRAY = Style.EMPTY.withColor(TextFormatting.DARK_GRAY);
    private static final Style DARK_GREEN = Style.EMPTY.withColor(TextFormatting.DARK_GREEN);
    private static final Style DARK_PURPLE = Style.EMPTY.withColor(TextFormatting.DARK_PURPLE);
    private static final Style DARK_RED = Style.EMPTY.withColor(TextFormatting.DARK_RED);
    private static final Style GOLD = Style.EMPTY.withColor(TextFormatting.GOLD);
    private static final Style GRAY = Style.EMPTY.withColor(TextFormatting.GRAY);
    private static final Style GREEN = Style.EMPTY.withColor(TextFormatting.GREEN);
    private static final Style ITALICS = Style.EMPTY.withItalic(true);
    private static final Style LIGHT_PURPLE = Style.EMPTY.withColor(TextFormatting.LIGHT_PURPLE);
    private static final Style OBFUSCATED = Style.EMPTY.setObfuscated(true);
    private static final Style RED = Style.EMPTY.withColor(TextFormatting.RED);
    private static final Style STRIKETHROUGH = Style.EMPTY.setStrikethrough(true);
    private static final Style UNDERLINE = Style.EMPTY.setUnderlined(true);
    private static final Style WHITE = Style.EMPTY.withColor(TextFormatting.WHITE);
    private static final Style YELLOW = Style.EMPTY.withColor(TextFormatting.YELLOW);

    @Override
    public Style aqua() {
        return AQUA;
    }

    @Override
    public Style black() {
        return BLACK;
    }

    @Override
    public Style blue() {
        return BLUE;
    }

    @Override
    public Style bold() {
        return BOLD;
    }

    @Override
    public Style darkAqua() {
        return DARK_AQUA;
    }

    @Override
    public Style darkBlue() {
        return DARK_BLUE;
    }

    @Override
    public Style darkGray() {
        return DARK_GRAY;
    }

    @Override
    public Style darkGreen() {
        return DARK_GREEN;
    }

    @Override
    public Style darkPurple() {
        return DARK_PURPLE;
    }

    @Override
    public Style darkRed() {
        return DARK_RED;
    }

    @Override
    public Style gold() {
        return GOLD;
    }

    @Override
    public Style gray() {
        return GRAY;
    }

    @Override
    public Style green() {
        return GREEN;
    }

    @Override
    public Style italics() {
        return ITALICS;
    }

    @Override
    public Style lightPurple() {
        return LIGHT_PURPLE;
    }

    @Override
    public Style obfuscated() {
        return OBFUSCATED;
    }

    @Override
    public Style red() {
        return RED;
    }

    @Override
    public Style strikethrough() {
        return STRIKETHROUGH;
    }

    @Override
    public Style underline() {
        return UNDERLINE;
    }

    @Override
    public Style white() {
        return WHITE;
    }

    @Override
    public Style yellow() {
        return YELLOW;
    }
}