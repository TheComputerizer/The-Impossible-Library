package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetBox;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetShape;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.GuiTextField;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nullable;

public class ScreenHelperLegacy implements ScreenHelperAPI {

    @Override
    public <B> ButtonAPI<?> getButton(@Nullable B button, WidgetShape shape) {
        return new ButtonLegacy((GuiButtonExt)button,shape);
    }

    @Override
    public RadialButtonAPI<?> getRadialButton(WidgetRadial shape) {
        return new RadialButtonLegacy(shape);
    }

    @Override
    public <S> ScreenAPI<?> getScreen(S screen) {
        return new ScreenLegacy((GuiScreen)screen);
    }

    @Override
    public WidgetScrollableAPI<WidgetAPI<?>> getScrollable(WidgetBox scrollBar) {
        return new WidgetScrollableLegacy<>(scrollBar);
    }

    @Override
    public <T> TextWidgetAPI<?> getTextWidget(T text, WidgetShape shape) {
        return new TextWidgetLegacy((GuiTextField)text,shape);
    }

    @Override
    public WidgetCollectionAPI<WidgetAPI<?>> getWidgetCollection() {
        return new WidgetCollectionLegacy<>();
    }
}