package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.WidgetScrollableAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;

public class WidgetScrollable1_12_2<W extends WidgetAPI<?>> extends WidgetScrollableAPI<W> {

    public WidgetScrollable1_12_2(WidgetBox scrollBar) {
        super(scrollBar);
    }

    @Override
    public W set(W widget) {
        return null;
    }
}
