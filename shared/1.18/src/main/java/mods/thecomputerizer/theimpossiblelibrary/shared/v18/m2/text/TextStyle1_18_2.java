package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Style;

import static net.minecraft.network.chat.Style.EMPTY;

public class TextStyle1_18_2 implements TextStyleAPI<Style> {

    private static final Style AQUA = EMPTY.withColor(ChatFormatting.AQUA);
    private static final Style BLACK = EMPTY.withColor(ChatFormatting.BLACK);
    private static final Style BLUE = EMPTY.withColor(ChatFormatting.BLUE);
    private static final Style BOLD = EMPTY.withBold(true);
    private static final Style DARK_AQUA = EMPTY.withColor(ChatFormatting.DARK_AQUA);
    private static final Style DARK_BLUE = EMPTY.withColor(ChatFormatting.DARK_BLUE);
    private static final Style DARK_GRAY = EMPTY.withColor(ChatFormatting.DARK_GRAY);
    private static final Style DARK_GREEN = EMPTY.withColor(ChatFormatting.DARK_GREEN);
    private static final Style DARK_PURPLE = EMPTY.withColor(ChatFormatting.DARK_PURPLE);
    private static final Style DARK_RED = EMPTY.withColor(ChatFormatting.DARK_RED);
    private static final Style GOLD = EMPTY.withColor(ChatFormatting.GOLD);
    private static final Style GRAY = EMPTY.withColor(ChatFormatting.GRAY);
    private static final Style GREEN = EMPTY.withColor(ChatFormatting.GREEN);
    private static final Style ITALICS = EMPTY.withItalic(true);
    private static final Style LIGHT_PURPLE = EMPTY.withColor(ChatFormatting.LIGHT_PURPLE);
    private static final Style OBFUSCATED = EMPTY.applyFormat(ChatFormatting.OBFUSCATED);
    private static final Style RED = EMPTY.withColor(ChatFormatting.RED);
    private static final Style STRIKETHROUGH = EMPTY.applyFormat(ChatFormatting.STRIKETHROUGH);
    private static final Style UNDERLINE = EMPTY.applyFormat(ChatFormatting.UNDERLINE);
    private static final Style WHITE = EMPTY.withColor(ChatFormatting.WHITE);
    private static final Style YELLOW = EMPTY.withColor(ChatFormatting.YELLOW);

    @Override public Style aqua() {
        return AQUA;
    }

    @Override public Style black() {
        return BLACK;
    }

    @Override public Style blue() {
        return BLUE;
    }

    @Override public Style bold() {
        return BOLD;
    }

    @Override public Style darkAqua() {
        return DARK_AQUA;
    }

    @Override public Style darkBlue() {
        return DARK_BLUE;
    }

    @Override public Style darkGray() {
        return DARK_GRAY;
    }

    @Override public Style darkGreen() {
        return DARK_GREEN;
    }

    @Override public Style darkPurple() {
        return DARK_PURPLE;
    }

    @Override public Style darkRed() {
        return DARK_RED;
    }

    @Override public Style gold() {
        return GOLD;
    }

    @Override public Style gray() {
        return GRAY;
    }

    @Override public Style green() {
        return GREEN;
    }

    @Override public Style italics() {
        return ITALICS;
    }

    @Override public Style lightPurple() {
        return LIGHT_PURPLE;
    }

    @Override public Style obfuscated() {
        return OBFUSCATED;
    }

    @Override public Style red() {
        return RED;
    }

    @Override public Style strikethrough() {
        return STRIKETHROUGH;
    }

    @Override public Style underline() {
        return UNDERLINE;
    }

    @Override public Style white() {
        return WHITE;
    }

    @Override public Style yellow() {
        return YELLOW;
    }
}