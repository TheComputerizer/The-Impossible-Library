package mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.neoforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;
import static net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventNeoForge extends RegisterBlockEntitiesEventWrapper<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(BLOCK_ENTITY_TYPE)) REGISTER_BLOCK_ENTITIES.invoke(event);
    }
    
    @Override public void setEvent(RegisterEvent event) {
        super.setEvent(event);
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        this.event.register(BLOCK_ENTITY_TYPE,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}