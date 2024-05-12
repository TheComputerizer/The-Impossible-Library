package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.item.Item1_16_5;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;

public class RegisterItemsEvent1_16_5 extends RegisterItemsEventWrapper<Register<Item>> {
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.getRegistry().register(((Item1_16_5)entry).getValue());
    }
}
