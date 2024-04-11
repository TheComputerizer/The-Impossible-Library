package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockNotifyNeighborEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import net.minecraft.util.EnumFacing;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.BLOCK_NOTIFY_NEIGHBOR;
import static mods.thecomputerizer.theimpossiblelibrary.api.block.Facing.UP;

public class BlockNotifyNeighborEvent1_12_2 extends BlockNotifyNeighborEventWrapper<NeighborNotifyEvent> {

    @SubscribeEvent
    public static void onEvent(NeighborNotifyEvent event) {
        BLOCK_NOTIFY_NEIGHBOR.invoke(event);
    }

    @Override
    protected EventFieldWrapper<NeighborNotifyEvent,Boolean> wrapForceRedstoneUpdateField() {
        return wrapGenericGetter(NeighborNotifyEvent::getForceRedstoneUpdate,false);
    }

    @Override
    protected EnumSet<Facing> wrapSidesField() {
        if(Objects.isNull(this.event)) return EnumSet.of(UP);
        List<Facing> list = new ArrayList<>();
        for(EnumFacing facing : this.event.getNotifiedSides()) list.add(Events1_12_2.getFacing(facing));
        return EnumSet.copyOf(list);
    }
}