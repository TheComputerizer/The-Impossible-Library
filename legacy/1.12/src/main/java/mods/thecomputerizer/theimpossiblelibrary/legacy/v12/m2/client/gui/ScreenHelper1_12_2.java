package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nullable;

public class ScreenHelper1_12_2 implements ScreenHelperAPI {

    @Override
    public <B> ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape) {
        return new Button1_12_2((GuiButtonExt)button,shape);
    }

    @Override
    public RadialButtonAPI<?> getRadialButton(WidgetRadial shape) {
        return new RadialButton1_12_2(shape);
    }

    @Override
    public <S> ScreenAPI<?> getScreen(S screen) {
        return new Screen1_12_2((GuiScreen)screen);
    }

    @Override
    public WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar) {
        return new WidgetScrollable1_12_2<>(scrollBar);
    }

    @Override
    public <T> TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape) {
        return new TextWidget1_12_2((GuiTextField)text,shape);
    }

    @Override
    public WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection() {
        return new WidgetCollection1_12_2<>();
    }
}