package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.sound.SoundEvent1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvents;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScreenHelper1_16_5 implements ScreenHelperAPI {
    
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/widgets.png");
    
    private final TextureWrapper normalButton;
    private final TextureWrapper hoveredButton;
    private final TextureWrapper disabledButton;
    
    public ScreenHelper1_16_5() {
        this.normalButton = new TextureWrapper().setTexture(new ResourceLocation1_16_5(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
        this.hoveredButton = new TextureWrapper().setTexture(new ResourceLocation1_16_5(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(43d/128d,53d/128d);
        this.disabledButton = new TextureWrapper().setTexture(new ResourceLocation1_16_5(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
    }
    
    @Override public TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled) {
        return disabled ? this.disabledButton : (hover ? this.hoveredButton : this.normalButton);
    }
    
    @Override public void open(@Nullable ScreenAPI screen) {
        Minecraft mc = Minecraft.getInstance();
        if(Objects.isNull(screen)) mc.setScreen(null);
        else mc.setScreen(new ScreenWrapper1_16_5(screen));
    }
    
    @Override public void playVanillaClickSound() {
        SoundHelper.play(new SoundEvent1_16_5(SoundEvents.UI_BUTTON_CLICK));
    }
}