package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity.BlockEntity1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.event.RegistryEvent.Register;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.REGISTER_BLOCK_ENTITIES;

public class RegisterBlockEntitiesEvent1_12_2 extends RegisterBlockEntitiesEventWrapper<Register<Block>> {
    
    @SubscribeEvent
    public static void onEvent(Register<Block> event) {
        REGISTER_BLOCK_ENTITIES.invoke(event);
    }
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        Class<? extends TileEntity> clazz = ((BlockEntity1_12_2)entry).getType();
        GameRegistry.registerTileEntity(clazz,((ResourceLocation1_12_2)entry.getRegistryName()).getInstance());
    }
}