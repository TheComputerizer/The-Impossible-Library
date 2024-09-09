package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.ISound;
import net.minecraft.client.audio.PositionedSoundRecord;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;

import java.util.Objects;

import static net.minecraft.client.audio.ISound.AttenuationType.NONE;
import static net.minecraft.util.SoundCategory.MASTER;

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
    
    @Override public void play(SoundEventAPI<?> event) {
        PositionedSoundRecord sound = new PositionedSoundRecord(((SoundEvent)event.getWrapped()).getSoundName(),MASTER,
                1f,1f,false,0,NONE,0f,0f,0f);
        Minecraft.getMinecraft().getSoundHandler().playSound(sound);
    }
    
    @Override public void setCategoryVolume(String name, float volume) {
        SoundCategory category = SoundCategory.getByName(name);
        if(Objects.nonNull(category)) Minecraft.getMinecraft().gameSettings.setSoundLevel(category,volume);
    }
}