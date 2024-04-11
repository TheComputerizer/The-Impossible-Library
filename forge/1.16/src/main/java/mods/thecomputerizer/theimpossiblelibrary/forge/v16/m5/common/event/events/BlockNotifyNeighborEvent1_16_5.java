package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockNotifyNeighborEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import net.minecraft.util.Direction;
import net.minecraftforge.event.world.BlockEvent.NeighborNotifyEvent;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.block.Facing.UP;

public class BlockNotifyNeighborEvent1_16_5 extends BlockNotifyNeighborEventWrapper<NeighborNotifyEvent> {

    @Override
    protected EventFieldWrapper<NeighborNotifyEvent,Boolean> wrapForceRedstoneUpdateField() {
        return wrapGenericGetter(NeighborNotifyEvent::getForceRedstoneUpdate,false);
    }

    @Override
    protected EnumSet<Facing> wrapSidesField() {
        if(Objects.isNull(this.event)) return EnumSet.of(UP);
        List<Facing> list = new ArrayList<>();
        for(Direction facing : this.event.getNotifiedSides()) list.add(Events1_16_5.getFacing(facing));
        return EnumSet.copyOf(list);
    }
}