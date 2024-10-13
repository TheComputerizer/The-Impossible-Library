package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterEntitiesEventForge;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;

public class RegisterEntitiesEventForge1_18_2 extends RegisterEntitiesEventForge<EntityType<?>> {
    
    @SubscribeEvent
    public static void onEvent(Register<EntityType<?>> event) {
        REGISTER_ENTITIES.invoke(event);
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}