package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import java.util.Objects;

@SuppressWarnings({"ConstantValue","UnreachableCode"})
public class SoundHelper1_16_5 implements SoundHelperAPI<ISound> {

    @Override
    public SoundAPI<ISound> getAPI(ISound sound) {
        return new Sound1_16_5(sound);
    }
    
    @Override public float getCategoryVolume(String name) {
        if(name.equals("record")) name = "records";
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        return Objects.nonNull(category) ? Minecraft.getInstance().options.getSoundSourceVolume(category) : 0f;
    }
    
    @Override public void play(SoundEventAPI<?> event) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI((SoundEvent)event.getSound(),1f));
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundCategory category = SoundCategory.valueOf(name.toUpperCase());
        if(Objects.nonNull(category)) Minecraft.getInstance().options.setSoundCategoryVolume(category,volume);
    }
}