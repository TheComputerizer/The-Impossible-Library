package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterItemsEventForge;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item.Item1_16_5;
import net.minecraft.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;

public class RegisterItemsEventForge1_16_5 extends RegisterItemsEventForge {
    
    @SubscribeEvent
    public static void onEvent(Register<Item> event) {
        REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.getRegistry().register(((Item1_16_5)entry).getValue());
    }
}
