package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.MaterialColor1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;

import javax.annotation.Nullable;
import java.util.Objects;

public class BlockBuilder1_16_5 extends BlockBuilderAPI {
    
    public BlockBuilder1_16_5(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_16_5 build() {
        Properties properties = Properties.of(((Material1_16_5)this.material).getMaterial(),
                ((MaterialColor1_16_5)this.materialColor).getMaterialColor());
        Block block = Objects.nonNull(this.blockEntityCreator) ?
                new TILBlockEntityProvider1_16_5(properties,defaultStateBuilder(),this.blockEntityCreator) :
                new TILBasicBlock1_16_5(properties,defaultStateBuilder());
        block.setRegistryName(((ResourceLocation1_16_5)this.registryName).getInstance());
        return new Block1_16_5(block);
    }
}
