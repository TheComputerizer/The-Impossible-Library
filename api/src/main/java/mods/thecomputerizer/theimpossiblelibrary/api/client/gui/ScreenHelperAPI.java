package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;

import javax.annotation.Nullable;

public interface ScreenHelperAPI {

    <B> ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape);
    RadialButtonAPI<?> getRadialButton(WidgetRadial shape);
    <S> ScreenAPI<?> getScreen(S screen);
    WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar);
    <T> TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape);
    WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection();
}