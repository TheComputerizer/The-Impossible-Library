package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextTranslationAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Minecraft1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.text.Text1_16_5;
import net.minecraft.client.gui.screen.Screen;
import org.joml.Vector2f;

import javax.annotation.Nonnull;
import java.util.Objects;

public class Screen1_16_5 extends ScreenAPI<Screen> {

    private final Screen screen;
    private MinecraftAPI mc;

    public Screen1_16_5(Screen screen) {
        this.screen = screen;
    }

     @Override
     public @Nonnull Vector2f getCenter() {
        return Objects.nonNull(this.screen) ?
                new Vector2f((float)this.screen.width/2f,(float)this.screen.height/2f) : VectorHelper.ZERO_2F;
     }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = Minecraft1_16_5.getInstance();
        return this.mc;
    }

    @Override
    public void init() {

    }

    @Override
    public Screen make(String locale, Object ... args) {
        TextTranslationAPI<?> text = TextHelper.getTranslated(locale,args);
        return new ScreenWrapper1_16_5(this,Objects.nonNull(text) ? ((Text1_16_5)text).getComponent() : null);
    }

    @Override
    public WidgetAPI<Screen> set(WidgetAPI<Screen> widget) {
        return null;
    }
}
