package mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockNotifyNeighborEventWrapper;
import net.minecraft.util.Direction;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_NOTIFY_NEIGHBOR;

public class BlockNotifyNeighborEventForge extends BlockNotifyNeighborEventWrapper<NeighborNotifyEvent> {
    
    @SubscribeEvent
    public static void onEvent(NeighborNotifyEvent event) {
        BLOCK_NOTIFY_NEIGHBOR.invoke(event);
    }
    
    @Override public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(NeighborNotifyEvent event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override protected EventFieldWrapper<NeighborNotifyEvent,Boolean> wrapForceRedstoneUpdateField() {
        return wrapGenericGetter(NeighborNotifyEvent::getForceRedstoneUpdate,false);
    }

    @Override protected EnumSet<Facing> wrapSidesField() {
        if(Objects.isNull(this.event)) return EnumSet.of(UP);
        List<Facing> list = new ArrayList<>();
        for(Direction facing : this.event.getNotifiedSides()) list.add(EventHelper.getFacing(facing));
        return EnumSet.copyOf(list);
    }
}