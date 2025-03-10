package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;
import static net.minecraft.core.registries.Registries.ENTITY_TYPE;

public class RegisterEntitiesEventNeoForge extends RegisterEntitiesEventWrapper<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(ENTITY_TYPE)) REGISTER_ENTITIES.invoke(event);
    }
    
    @Override public void setEvent(RegisterEvent event) {
        super.setEvent(event);
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.register(ENTITY_TYPE,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}