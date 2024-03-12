package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.MinecraftAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.widget.WidgetAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.MinecraftForgeTIL;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.text.TextForge;
import net.minecraft.client.gui.screen.Screen;

import java.util.Objects;

public class ScreenForge extends ScreenAPI<Screen> {

    private final Screen screen;
    private MinecraftAPI mc;

    public ScreenForge(Screen screen) {
        this.screen = screen;
    }

    @Override
    public MinecraftAPI getMinecraft() {
        if(Objects.isNull(this.mc)) this.mc = MinecraftForgeTIL.getInstance();
        return this.mc;
    }

    @Override
    public WidgetAPI<Screen> set(WidgetAPI<Screen> widget) {
        return null;
    }

    @Override
    public void init() {

    }

    @Override
    public Screen make(String locale, Object ... args) {
        return new ScreenWrapperForge(this,((TextForge)this.mc.getTranslatedText(locale,args)).get());
    }
}
