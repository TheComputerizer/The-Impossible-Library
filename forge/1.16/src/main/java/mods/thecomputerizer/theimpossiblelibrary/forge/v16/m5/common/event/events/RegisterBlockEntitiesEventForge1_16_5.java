package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.RegisterBlockEntitiesEventForge;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.eventbus.api.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;

public class RegisterBlockEntitiesEventForge1_16_5 extends RegisterBlockEntitiesEventForge<TileEntityType<?>> {
    
    @SubscribeEvent
    public static void onEvent(Register<TileEntityType<?>> event) {
        REGISTER_BLOCK_ENTITIES.invoke(event);
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        this.event.getRegistry().register(entry.unwrap());
    }
}