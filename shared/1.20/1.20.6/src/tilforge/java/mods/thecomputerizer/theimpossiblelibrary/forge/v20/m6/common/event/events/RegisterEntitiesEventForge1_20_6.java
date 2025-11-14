package mods.thecomputerizer.theimpossiblelibrary.forge.v20.m6.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterEntitiesEventForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;
import static net.minecraft.core.registries.Registries.ENTITY_TYPE;

public class RegisterEntitiesEventForge1_20_6 extends RegisterEntitiesEventForge<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(ENTITY_TYPE)) REGISTER_ENTITIES.invoke(event);
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.register(ENTITY_TYPE,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}