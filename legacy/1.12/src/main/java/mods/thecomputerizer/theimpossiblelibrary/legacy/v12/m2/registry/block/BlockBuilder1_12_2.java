package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;

import javax.annotation.Nullable;

public class BlockBuilder1_12_2 extends BlockBuilderAPI {
    
    public BlockBuilder1_12_2(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_12_2 build() {
        Block block = new TILBasicBlock1_12_2(Material.WOOD, MapColor.BLACK); //TODO Material & MopColor APIs
        block.setRegistryName(((ResourceLocation1_12_2)this.registryName).getInstance());
        return new Block1_12_2(block);
    }
}
