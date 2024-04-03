package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.geometry.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.MinecraftForgeTIL;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextForge;
import net.minecraft.client.gui.screen.Screen;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

public class ScreenForge extends ScreenAPI<Screen> {

    private final Screen screen;
    private MinecraftAPI mc;

    public ScreenForge(Screen screen) {
        this.screen = screen;
    }

     @Override
     public @Nonnull Vector2f getCenter() {
        return Objects.nonNull(this.screen) ?
                new Vector2f((float)this.screen.width/2f,(float)this.screen.height/2f) : VectorHelper.ZERO_2F;
     }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = MinecraftForgeTIL.getInstance();
        return this.mc;
    }

    @Override
    public void init() {

    }

    @Override
    public Screen make(String locale, Object ... args) {
        TextTranslationAPI<?> text = TextHelper.getTranslated(locale,args);
        return new ScreenWrapperForge(this,Objects.nonNull(text) ? ((TextForge)text).get() : null);
    }

    @Override
    public WidgetAPI<Screen> set(WidgetAPI<Screen> widget) {
        return null;
    }
}
