package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.RegisterBlockEntitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.blockentity.BlockEntity1_16_5;
import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.event.RegistryEvent.Register;

public class RegisterBlockEntitiesEvent1_16_5 extends RegisterBlockEntitiesEventWrapper<Register<TileEntityType<?>>> {
    
    @Override public void register(BlockEntityAPI<?,?> entry) {
        this.event.getRegistry().register(((BlockEntity1_16_5)entry).getValue());
    }
}