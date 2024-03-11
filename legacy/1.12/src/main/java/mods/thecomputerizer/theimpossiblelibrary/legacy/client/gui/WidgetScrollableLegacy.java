package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.WidgetScrollableAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;

public class WidgetScrollableLegacy<W extends WidgetAPI<?>> extends WidgetScrollableAPI<W> {

    public WidgetScrollableLegacy(WidgetBox scrollBar) {
        super(scrollBar);
    }

    @Override
    public W set(W widget) {
        return null;
    }
}
