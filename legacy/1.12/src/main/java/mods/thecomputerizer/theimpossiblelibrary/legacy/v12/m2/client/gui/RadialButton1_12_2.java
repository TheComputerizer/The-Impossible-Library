package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.RadialButtonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.shapes.WidgetRadial;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraftforge.fml.client.config.GuiButtonExt;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

public class RadialButton1_12_2 extends RadialButtonAPI<GuiButtonExt> {

    public RadialButton1_12_2(WidgetRadial shape) {
        super(shape);
    }

    @Override
    public boolean click() {
        return false;
    }

    @Override
    public @Nonnull List<TextAPI<?>> getHoverLines() {
        return Collections.emptyList();
    }

    @Override
    public MinecraftAPI getMinecraft() {
        ScreenAPI<?> screen = getScreen();
        return Objects.nonNull(screen) ? screen.getMinecraft() : Minecraft1_12_2.getInstance();
    }

    @Override
    public @Nullable WidgetAPI<?> getParent() {
        return null;
    }
}
