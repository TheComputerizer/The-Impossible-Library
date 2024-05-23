package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.entity.Entity1_12_2;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityEntry;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_ENTITIES;

public class RegisterEntitiesEvent1_12_2 extends RegisterEntitiesEventWrapper<Register<EntityEntry>> {
    
    @SubscribeEvent
    public static void onEvent(Register<EntityEntry> event) {
        REGISTER_ENTITIES.invoke(event);
    }
    
    @Override
    public void cancel() {
        this.event.setCanceled(true);
    }
    
    @Override public void setEvent(Register<EntityEntry> event) {
        super.setEvent(event);
        setCanceled(event.isCanceled());
    }
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.getRegistry().register(((Entity1_12_2)entry).getValue());
    }
}
