package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound.SoundHelper1_16_5;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;

import java.util.Objects;

@SuppressWarnings("ConstantValue")
public class SoundHelperForge1_16_5 extends SoundHelper1_16_5<ISound> {
    
    @Override public float getCategoryVolume(String name) {
        if(name.equals("record")) name = "records";
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        return Objects.nonNull(category) ? Minecraft.getInstance().options.getSoundSourceVolume(category) : 0f;
    }
    
    @Override public void play(SoundEventAPI<?> event) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(event.unwrap(),1f));
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        if(Objects.nonNull(category)) Minecraft.getInstance().options.setSoundCategoryVolume(category,volume);
    }
}
