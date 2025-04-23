package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterItemsEventForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;
import static net.minecraft.core.Registry.ITEM_REGISTRY;

public class RegisterItemsEventForge1_19 extends RegisterItemsEventForge<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(ITEM_REGISTRY)) REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.register(ITEM_REGISTRY,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}