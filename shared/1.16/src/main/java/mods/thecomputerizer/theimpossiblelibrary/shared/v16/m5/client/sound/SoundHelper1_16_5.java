package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.client.sound;

import mods.thecomputerizer.theimpossiblelibrary.api.client.sound.SoundHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.sound.SoundEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.client.GameSettings;
import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.SimpleSound;
import net.minecraft.util.SoundCategory;

import java.util.Objects;

public class SoundHelper1_16_5 implements SoundHelperAPI {
    
    @SuppressWarnings("ConstantValue")
    @Override public float getCategoryVolume(String name) {
        String categoryName = (name.equals("record") ? "records" : name).toUpperCase();
        SoundCategory category = SoundCategory.valueOf(categoryName);
        if(Objects.nonNull(category)) {
            GameSettings options = Minecraft.getInstance().options;
            if(Objects.nonNull(options)) return options.getSoundSourceVolume(category);
            else TILRef.logError("Failed to get source volume for {} (null options)", categoryName);
        } else TILRef.logError("Failed to get source volume for {} (nonexistent category)",categoryName);
        return 0f;
    }
    
    @Override public void play(SoundEventAPI<?> event) {
        Minecraft.getInstance().getSoundManager().play(SimpleSound.forUI(event.unwrap(), 1f));
    }
    
    @SuppressWarnings("ConstantValue")
    @Override public void setCategoryVolume(String name, float volume) {
        String categoryName = (name.equals("record") ? "records" : name).toUpperCase();
        SoundCategory category = SoundCategory.valueOf(categoryName);
        if(Objects.nonNull(category)) {
            GameSettings options = Minecraft.getInstance().options;
            if(Objects.nonNull(options)) options.setSoundCategoryVolume(category,volume);
            else TILRef.logError("Failed to set source volume for {} to {} (null options)",categoryName,volume);
        } else TILRef.logError("Failed to set source volume for {} to {} (nonexistent category)",categoryName,volume);
    }
}