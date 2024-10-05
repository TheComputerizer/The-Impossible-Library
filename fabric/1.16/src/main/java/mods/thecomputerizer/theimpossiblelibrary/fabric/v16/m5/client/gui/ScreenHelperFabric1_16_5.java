package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

import static net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK;

public class ScreenHelperFabric1_16_5 implements ScreenHelperAPI {
    
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/widgets.png");
    
    private final TextureWrapper normalButton;
    private final TextureWrapper hoveredButton;
    private final TextureWrapper disabledButton;
    
    public ScreenHelperFabric1_16_5() {
        this.normalButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
        this.hoveredButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(43d/128d,53d/128d);
        this.disabledButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
    }
    
    @Override public TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled) {
        return disabled ? this.disabledButton : (hover ? this.hoveredButton : this.normalButton);
    }
    
    @Override public void open(@Nullable ScreenAPI screen) {
        Minecraft mc = Minecraft.getInstance();
        if(Objects.isNull(screen)) mc.setScreen(null);
        else mc.setScreen(new ScreenWrapperFabric1_16_5(screen));
    }
    
    @Override public void playVanillaClickSound() {
        SoundHelper.play(WrapperHelper.wrapSoundEvent(UI_BUTTON_CLICK));
    }
}