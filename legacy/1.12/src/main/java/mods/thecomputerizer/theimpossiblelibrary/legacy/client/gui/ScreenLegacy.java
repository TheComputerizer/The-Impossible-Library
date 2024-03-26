package mods.thecomputerizer.theimpossiblelibrary.legacy.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.geometry.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.MinecraftLegacy;
import net.minecraft.client.gui.GuiScreen;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ScreenLegacy extends ScreenAPI<GuiScreen> {

    private final GuiScreen screen;
    private MinecraftAPI mc;

    public ScreenLegacy(GuiScreen screen) {
        this.screen = screen;
    }

    @Override
    public @Nonnull Vector2f getCenter() {
        return Objects.nonNull(this.screen) ?
                new Vector2f((float)this.screen.width/2f,(float)this.screen.height/2f) : VectorHelper.ZERO_2F;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = MinecraftLegacy.getInstance();
        return this.mc;
    }

    @Override
    public void init() {

    }

    @Override
    public GuiScreen make(String locale, Object ... args) {
        return new ScreenWrapperLegacy(this);
    }

    @Override
    public WidgetAPI<GuiScreen> set(WidgetAPI<GuiScreen> widget) {
        return null;
    }
}
