package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;

import javax.annotation.Nullable;

public class ScreenHelperForge implements ScreenHelperAPI {

    @Override
    public <B> ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape) {
        return new ButtonForge((Button)button,shape);
    }

    @Override
    public RadialButtonAPI<?> getRadialButton(WidgetRadial shape) {
        return new RadialButtonForge(shape);
    }

    @Override
    public <S> ScreenAPI<?> getScreen(S screen) {
        return new ScreenForge((Screen)screen);
    }

    @Override
    public WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar) {
        return new WidgetScrollableForge<>(scrollBar);
    }

    @Override
    public <T> TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape) {
        return new TextWidgetForge((TextFieldWidget)text,shape);
    }

    @Override
    public WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection() {
        return new WidgetCollectionForge<>();
    }
}