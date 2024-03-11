package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;

public abstract class RadialButtonAPI<B> extends ButtonAPI<B> {

    protected RadialButtonAPI(WidgetRadial shape) {
        super(null,shape);
    }

    @Override
    public B make(int id, int x, int y, int width, int height, String locale, Object ... args) {
        return null;
    }

    @Override
    public void playClickSound() {

    }

    @Override
    public void setLocale(String locale) {

    }
}
