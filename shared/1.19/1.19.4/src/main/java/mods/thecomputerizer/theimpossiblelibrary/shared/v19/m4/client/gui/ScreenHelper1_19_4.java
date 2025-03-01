package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.client.gui.ScreenHelper1_19;
import net.minecraft.client.Minecraft;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK;

public class ScreenHelper1_19_4 extends ScreenHelper1_19 {
    
    @Override public void open(@Nullable ScreenAPI screen) {
        Minecraft mc = Minecraft.getInstance();
        if(Objects.isNull(screen)) mc.setScreen(null);
        else mc.setScreen(new ScreenWrapper1_19_4(screen));
    }
    
    @Override public void playVanillaClickSound() {
        SoundHelper.play(WrapperHelper.wrapSoundEvent(UI_BUTTON_CLICK.value()));
    }
}