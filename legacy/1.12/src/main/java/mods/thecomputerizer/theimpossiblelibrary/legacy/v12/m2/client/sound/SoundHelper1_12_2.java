package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.util.SoundCategory;

import java.util.Objects;

@SuppressWarnings({"ConstantValue","UnreachableCode"})
public class SoundHelper1_12_2 implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new Sound1_12_2(sound);
    }
    
    
    @Override public float getCategoryVolume(String name) {
        SoundCategory category = SoundCategory.getByName(name);
        return Objects.nonNull(category) ? Minecraft.getMinecraft().gameSettings.getSoundLevel(category) : 0f;
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundCategory category = SoundCategory.getByName(name);
        if(Objects.nonNull(category)) Minecraft.getMinecraft().gameSettings.setSoundLevel(category,volume);
    }
}