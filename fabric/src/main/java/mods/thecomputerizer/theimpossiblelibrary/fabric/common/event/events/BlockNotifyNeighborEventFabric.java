package mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.BlockNotifyNeighborEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.CommonFabricEvent;
import net.fabricmc.fabric.api.event.Event;

import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.UP;

public class BlockNotifyNeighborEventFabric extends BlockNotifyNeighborEventWrapper<Object[]> implements CommonFabricEvent {
    
    @Override public Event<?> getEventInstance() {
        return null;
    }
    
    @Override protected EventFieldWrapper<Object[],Boolean> wrapForceRedstoneUpdateField() {
        return wrapGenericGetter(wrapArrayGetter(0),false);
    }

    @Override protected EnumSet<Facing> wrapSidesField() {
        if(Objects.isNull(this.event)) return EnumSet.of(UP);
        List<Facing> list = new ArrayList<>();
        //for(Direction facing : this.event.getNotifiedSides()) list.add(EventHelper.getFacing(facing));
        return EnumSet.copyOf(list);
    }
}