package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.WidgetScrollableAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;

public class WidgetScrollableForge<W extends WidgetAPI<?>> extends WidgetScrollableAPI<W> {

    public WidgetScrollableForge(WidgetBox scrollBar) {
        super(scrollBar);
    }

    @Override
    public W set(W widget) {
        return null;
    }
}
