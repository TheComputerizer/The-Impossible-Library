package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.entity.Entity1_16_5;
import net.minecraft.entity.EntityType;
import net.minecraftforge.event.RegistryEvent.Register;

public class RegisterEntitiesEvent1_16_5 extends RegisterEntitiesEventWrapper<Register<EntityType<?>>> {
    
    @Override public void register(EntityAPI<?,?> entry) {
        this.event.getRegistry().register(((Entity1_16_5)entry).getValue());
    }
}
