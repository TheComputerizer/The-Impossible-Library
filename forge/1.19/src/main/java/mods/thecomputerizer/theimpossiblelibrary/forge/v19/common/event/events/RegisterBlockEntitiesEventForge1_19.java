package mods.thecomputerizer.theimpossiblelibrary.forge.v19.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlockEntitiesEventForge;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;

public class RegisterBlockEntitiesEventForge1_19 extends RegisterBlockEntitiesEventForge<BlockEntityType<?>> {
    
    @SubscribeEvent
    public static void onEvent(Register<BlockEntityType<?>> event) {
        REGISTER_BLOCK_ENTITIES.invoke(event);
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}