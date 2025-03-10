package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.event.level.BlockEvent.CropGrowEvent.Post;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public class CropGrowPostEventNeoForge extends CropGrowPostEventWrapper<Post> {
    
    @SubscribeEvent
    public static void onEvent(Post event) {
        CROP_GROW_POST.invoke(event);
    }
    
    @Override public void setEvent(Post event) {
        super.setEvent(event);
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