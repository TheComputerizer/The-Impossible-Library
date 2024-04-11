package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.widget.TextFieldWidget;
import net.minecraft.client.gui.widget.button.Button;

import javax.annotation.Nullable;

public class ScreenHelper1_16_5 implements ScreenHelperAPI {

    @Override
    public <B> ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape) {
        return new Button1_16_5((Button)button,shape);
    }

    @Override
    public RadialButtonAPI<?> getRadialButton(WidgetRadial shape) {
        return new RadialButton1_16_5(shape);
    }

    @Override
    public <S> ScreenAPI<?> getScreen(S screen) {
        return new Screen1_16_5((Screen)screen);
    }

    @Override
    public WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar) {
        return new WidgetScrollable1_16_5<>(scrollBar);
    }

    @Override
    public <T> TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape) {
        return new TextWidget1_16_5((TextFieldWidget)text,shape);
    }

    @Override
    public WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection() {
        return new WidgetCollection1_16_5<>();
    }
}