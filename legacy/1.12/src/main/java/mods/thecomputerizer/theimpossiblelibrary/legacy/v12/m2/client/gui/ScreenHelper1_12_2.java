package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.*;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.sound.SoundEvent1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.client.Minecraft;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Objects;

public class ScreenHelper1_12_2 implements ScreenHelperAPI {
    
    private static final ResourceLocation BUTTON_TEXTURE = new ResourceLocation("textures/gui/widgets.png");
    
    private final TextureWrapper normalButton;
    private final TextureWrapper hoveredButton;
    private final TextureWrapper disabledButton;
    
    public ScreenHelper1_12_2() {
        this.normalButton = new TextureWrapper().setTexture(new ResourceLocation1_12_2(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
        this.hoveredButton = new TextureWrapper().setTexture(new ResourceLocation1_12_2(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(43d/128d,53d/128d);
        this.disabledButton = new TextureWrapper().setTexture(new ResourceLocation1_12_2(BUTTON_TEXTURE))
                .setU(0d,25d/32d).setV(33d/128d,43d/128d);
    }
    
    @Override public TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled) {
        return disabled ? this.disabledButton : (hover ? this.hoveredButton : this.normalButton);
    }
    
    @Override public void open(@Nullable ScreenAPI screen) {
        Minecraft mc = Minecraft.getMinecraft();
        if(Objects.isNull(screen)) mc.setIngameFocus();
        else mc.displayGuiScreen(new ScreenWrapper1_12_2(screen));
    }
    
    @Override public void playVanillaClickSound() {
        SoundHelper.play(new SoundEvent1_12_2(SoundEvents.UI_BUTTON_CLICK));
    }
}