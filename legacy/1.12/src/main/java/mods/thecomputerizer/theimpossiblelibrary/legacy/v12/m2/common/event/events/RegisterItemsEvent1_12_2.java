package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item.Item1_12_2;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;

public class RegisterItemsEvent1_12_2 extends RegisterItemsEventWrapper<Register<Item>> {
    
    @SubscribeEvent
    public static void onEvent(Register<Item> event) {
        REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.getRegistry().register(((Item1_12_2)entry).getValue());
    }
}
