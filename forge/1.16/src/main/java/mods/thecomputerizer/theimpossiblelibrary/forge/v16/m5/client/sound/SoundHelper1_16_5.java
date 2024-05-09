package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;

import java.util.Objects;

@SuppressWarnings({"ConstantValue","UnreachableCode"})
public class SoundHelper1_16_5 implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new Sound1_16_5(sound);
    }
    
    @Override public float getCategoryVolume(String name) {
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        return Objects.nonNull(category) ? Minecraft.getInstance().options.getSoundSourceVolume(category) : 0f;
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        if(Objects.nonNull(category)) Minecraft.getInstance().options.setSoundCategoryVolume(category,volume);
    }
}