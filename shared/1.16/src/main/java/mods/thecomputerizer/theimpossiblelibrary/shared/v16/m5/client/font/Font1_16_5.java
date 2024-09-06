package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.font;

import mods.thecomputerizer.theimpossiblelibrary.api.client.font.FontAPI;

public abstract class Font1_16_5 implements FontAPI {

    @Override public int getCharWidth(char c) {
        return getStringWidth(""+c);
    }
}
