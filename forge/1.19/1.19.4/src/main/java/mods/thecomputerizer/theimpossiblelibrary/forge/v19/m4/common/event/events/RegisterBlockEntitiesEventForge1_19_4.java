package mods.thecomputerizer.theimpossiblelibrary.forge.v19.m4.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlockEntitiesEventForge;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.registries.RegisterEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;
import static net.minecraft.core.registries.Registries.BLOCK_ENTITY_TYPE;

public class RegisterBlockEntitiesEventForge1_19_4 extends RegisterBlockEntitiesEventForge<RegisterEvent> {
    
    @SubscribeEvent
    public static void onEvent(RegisterEvent event) {
        if(event.getRegistryKey().equals(BLOCK_ENTITY_TYPE)) REGISTER_BLOCK_ENTITIES.invoke(event);
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        this.event.register(BLOCK_ENTITY_TYPE,entry.getRegistryName().unwrap(),entry::unwrap);
    }
}