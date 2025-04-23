package mods.thecomputerizer.theimpossiblelibrary.forge.v20.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.CropGrowPostEventForge;
import net.minecraftforge.event.level.BlockEvent.CropGrowEvent.Post;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public class CropGrowPostEventForge1_20 extends CropGrowPostEventForge<Post> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        CROP_GROW_POST.invoke(event);
    }
    
    @Override protected EventFieldWrapper<Post,BlockStateAPI<?>> wrapOriginalStateField() {
        return wrapStateGetter(Post::getOriginalState);
    }

    @Override protected EventFieldWrapper<Post,BlockPosAPI<?>> wrapPosField() {
        return wrapPosGetter(Post::getPos);
    }

    @Override protected EventFieldWrapper<Post,BlockStateAPI<?>> wrapStateField() {
        return wrapStateGetter(Post::getState);
    }

    @Override protected EventFieldWrapper<Post,WorldAPI<?>> wrapWorldField() {
        return wrapWorldGetter(Post::getLevel);
    }
}