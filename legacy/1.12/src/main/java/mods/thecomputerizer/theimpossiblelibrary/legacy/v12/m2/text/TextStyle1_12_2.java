package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.text;

import mods.thecomputerizer.theimpossiblelibrary.api.text.TextStyleAPI;
import net.minecraft.util.text.Style;
import net.minecraft.util.text.TextFormatting;

import java.util.Objects;

public class TextStyle1_12_2 implements TextStyleAPI<Style> {

    private static final Style AQUA = new Style().setColor(TextFormatting.AQUA);
    private static final Style BLACK = new Style().setColor(TextFormatting.BLACK);
    private static final Style BLUE = new Style().setColor(TextFormatting.BLUE);
    private static final Style BOLD = new Style().setBold(true);
    private static final Style DARK_AQUA = new Style().setColor(TextFormatting.DARK_AQUA);
    private static final Style DARK_BLUE = new Style().setColor(TextFormatting.DARK_BLUE);
    private static final Style DARK_GRAY = new Style().setColor(TextFormatting.DARK_GRAY);
    private static final Style DARK_GREEN = new Style().setColor(TextFormatting.DARK_GREEN);
    private static final Style DARK_PURPLE = new Style().setColor(TextFormatting.DARK_PURPLE);
    private static final Style DARK_RED = new Style().setColor(TextFormatting.DARK_RED);
    private static final Style GOLD = new Style().setColor(TextFormatting.GOLD);
    private static final Style GRAY = new Style().setColor(TextFormatting.GRAY);
    private static final Style GREEN = new Style().setColor(TextFormatting.GREEN);
    private static final Style ITALICS = new Style().setItalic(true);
    private static final Style LIGHT_PURPLE = new Style().setColor(TextFormatting.LIGHT_PURPLE);
    private static final Style OBFUSCATED = new Style().setObfuscated(true);
    private static final Style RED = new Style().setColor(TextFormatting.RED);
    private static final Style STRIKETHROUGH = new Style().setStrikethrough(true);
    private static final Style UNDERLINE = new Style().setUnderlined(true);
    private static final Style WHITE = new Style().setColor(TextFormatting.WHITE);
    private static final Style YELLOW = new Style().setColor(TextFormatting.YELLOW);

    public Style append(Style style, Style append) {
        if(append.getBold()) style.setBold(true);
        if(append.getItalic()) style.setItalic(true);
        if(append.getObfuscated()) style.setObfuscated(true);
        if(append.getStrikethrough()) style.setStrikethrough(true);
        if(append.getUnderlined()) style.setUnderlined(true);
        TextFormatting color = append.getColor();
        if(Objects.nonNull(color)) style.setColor(color);
        return style;
    }

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

    @SuppressWarnings("unused")
    public enum StyleType { BOLD, COLOR, ITALICS, OBFUSCATED, STRIKETHROUGH, UNDERLINE }
}