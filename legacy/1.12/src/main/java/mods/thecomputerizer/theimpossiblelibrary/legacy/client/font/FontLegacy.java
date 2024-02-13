package mods.thecomputerizer.theimpossiblelibrary.legacy.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;

public class FontLegacy implements FontAPI {

    @Override
    public int getCharWidth(char c) {
        return 0;
    }

    @Override
    public int getFontHeight() {
        return 0;
    }

    @Override
    public int getStringWidth(String str) {
        return 0;
    }
}
