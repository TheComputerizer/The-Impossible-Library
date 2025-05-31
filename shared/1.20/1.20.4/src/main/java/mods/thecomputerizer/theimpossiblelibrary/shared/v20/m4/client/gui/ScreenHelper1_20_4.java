package mods.thecomputerizer.theimpossiblelibrary.shared.v20.m4.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.ScreenHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.resources.ResourceLocation;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

import static net.minecraft.sounds.SoundEvents.UI_BUTTON_CLICK;

public class ScreenHelper1_20_4 implements ScreenHelperAPI {
    
    private static final ResourceLocation BUTTON = new ResourceLocation("textures/gui/sprites/widget/button.png");
    private static final ResourceLocation BUTTON_DISABLED = new ResourceLocation("textures/gui/sprites/widget/button_disabled.png");
    private static final ResourceLocation BUTTON_HOVERED = new ResourceLocation("textures/gui/sprites/widget/button_highlighted.png");
    
    private final TextureWrapper normalButton;
    private final TextureWrapper hoveredButton;
    private final TextureWrapper disabledButton;
    
    public ScreenHelper1_20_4() {
        this.normalButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON))
                .setU(0d,1d).setV(0d,1d);
        this.hoveredButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON_HOVERED))
                .setU(0d,1d).setV(0d,1d);
        this.disabledButton = new TextureWrapper()
                .setTexture(WrapperHelper.wrapResourceLocation(BUTTON_DISABLED))
                .setU(0d,1d).setV(0d,1d);
    }
    
    @Override public TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled) {
        return disabled ? this.disabledButton : (hover ? this.hoveredButton : this.normalButton);
    }
    
    @Override public void open(@Nullable ScreenAPI screen) {
        Minecraft mc = Minecraft.getInstance();
        if(Objects.isNull(screen)) mc.setScreen(null);
        else mc.setScreen(new ScreenWrapper1_20_4(screen));
    }
    
    @Override public void playVanillaClickSound() {
        SoundHelper.play(WrapperHelper.wrapSoundEvent(UI_BUTTON_CLICK.value()));
    }
}