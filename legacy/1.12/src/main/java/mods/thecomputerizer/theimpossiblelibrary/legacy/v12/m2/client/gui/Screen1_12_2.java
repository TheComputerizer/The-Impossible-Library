package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Minecraft1_12_2;
import net.minecraft.client.gui.GuiScreen;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Screen1_12_2 extends ScreenAPI<GuiScreen> {

    private final GuiScreen screen;
    private MinecraftAPI mc;

    public Screen1_12_2(GuiScreen screen) {
        this.screen = screen;
    }

    @Override
    public @Nonnull Vector2f getCenter() {
        return Objects.nonNull(this.screen) ?
                new Vector2f((float)this.screen.width/2f,(float)this.screen.height/2f) : VectorHelper.ZERO_2F;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = Minecraft1_12_2.getInstance();
        return this.mc;
    }

    @Override
    public void init() {

    }

    @Override
    public GuiScreen make(String locale, Object ... args) {
        return new ScreenWrapper1_12_2(this);
    }

    @Override
    public WidgetAPI<GuiScreen> set(WidgetAPI<GuiScreen> widget) {
        return null;
    }
}
