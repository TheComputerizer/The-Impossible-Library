package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.DiscBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.item.ItemProperties;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.sounds.SoundEvents.EXPERIENCE_ORB_PICKUP;

public class DiscBuilder1_20 extends DiscBuilderAPI implements ItemBuilderHelpers1_20 {
    
    public DiscBuilder1_20(@Nullable ItemBuilderAPI parent) {
        super(parent);
    }
    
    @Override public ItemAPI<?> build() {
        return defaultBuild(buildProperties(),this::makeItem);
    }
    
    @SuppressWarnings("unchecked")
    @Override protected <S> S defaultSound() {
        return (S)EXPERIENCE_ORB_PICKUP;
    }
    
    protected Object[] makeArgs(ItemProperties properties) {
        return new Object[]{getSound(),properties,this.lengthInSeconds};
    }
}