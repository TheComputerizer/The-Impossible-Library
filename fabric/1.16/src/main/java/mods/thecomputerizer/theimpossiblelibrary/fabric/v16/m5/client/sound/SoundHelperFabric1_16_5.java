package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound.SoundHelper1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.resources.sounds.SimpleSoundInstance;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.SoundSource;

import java.util.Objects;

@SuppressWarnings("ConstantValue")
public class SoundHelperFabric1_16_5 extends SoundHelper1_16_5<SoundInstance> {
    
    @Override public float getCategoryVolume(String name) {
        if(name.equals("record")) name = "records";
        SoundSource category = SoundSource.valueOf(name.toUpperCase());
        return Objects.nonNull(category) ? Minecraft.getInstance().options.getSoundSourceVolume(category) : 0f;
    }
    
    @Override public void play(SoundEventAPI<?> event) {
        Minecraft.getInstance().getSoundManager().play(SimpleSoundInstance.forUI(event.unwrap(),1f));
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundSource category = SoundSource.valueOf(name.toUpperCase());
        if(Objects.nonNull(category)) Minecraft.getInstance().options.setSoundCategoryVolume(category,volume);
    }
}
