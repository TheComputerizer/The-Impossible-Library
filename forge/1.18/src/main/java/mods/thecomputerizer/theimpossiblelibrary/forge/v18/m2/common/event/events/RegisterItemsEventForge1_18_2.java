package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterItemsEventForge;
import net.minecraft.world.item.Item;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;

public class RegisterItemsEventForge1_18_2 extends RegisterItemsEventForge<Item> {
    
    @SubscribeEvent
    public static void onEvent(Register<Item> event) {
        REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}