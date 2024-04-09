package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.CropGrowPostEventWrapper;
import net.minecraftforge.event.world.BlockEvent.CropGrowEvent.Post;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.CROP_GROW_POST;

public class CropGrowPostEventLegacy extends CropGrowPostEventWrapper<Post> {

    @SubscribeEvent
    public static void onEvent(Post event) {
        CROP_GROW_POST.invoke(event);
    }
}