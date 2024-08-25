package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterEntitiesEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Entity1_16_5;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;

public class RegisterEntitiesEvent1_16_5 extends RegisterEntitiesEventForge {
    
    @SubscribeEvent
    public static void onEvent(Register<EntityType<?>> event) {
        REGISTER_ENTITIES.invoke(event);
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.getRegistry().register(((Entity1_16_5)entry).getValue());
    }
}
