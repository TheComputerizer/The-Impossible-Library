package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.WidgetCollectionAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;

import javax.annotation.Nullable;

public class WidgetCollectionLegacy<W extends WidgetAPI<?>> extends WidgetCollectionAPI<W> {

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }

    @Override
    public @Nullable ScreenAPI<?> getScreen() {
        return null;
    }

    @Override
    public W set(W widget) {
        return null;
    }
}
