package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterItemsEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemAPI;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ITEMS;
import static net.minecraft.core.registries.Registries.ITEM;

public class RegisterItemsEventNeoForge extends RegisterItemsEventWrapper<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(ITEM)) REGISTER_ITEMS.invoke(event);
    }
    
    @Override public void setEvent(RegisterEvent event) {
        super.setEvent(event);
    }
    
    @Override public void register(ItemAPI<?> entry) {
        this.event.register(ITEM,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}