package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import org.joml.Vector2f;

import javax.annotation.Nonnull;

public abstract class RadialButtonAPI<B> extends ButtonAPI<B> {

    protected RadialButtonAPI(B button, WidgetShape shape) {
        super(button,shape);
    }


    @Override
    public @Nonnull Vector2f getCenter() {
        return getShape().getCenter();
    }

    @Override
    public boolean isHovering(int x, int y) {
        return false;
    }

    @Override
    public B make(int id, int x, int y, int width, int height, String locale) {
        return null;
    }

    @Override
    public void playClickSound() {

    }

    @Override
    public void setLocale(String locale) {

    }
}
