package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPostEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public class CropGrowPostEvent1_12_2 extends CropGrowPostEventWrapper<Post> {

    @SubscribeEvent
    public static void onEvent(Post event) {
        CROP_GROW_POST.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Post event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
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
        return wrapWorldGetter(Post::getWorld);
    }
}