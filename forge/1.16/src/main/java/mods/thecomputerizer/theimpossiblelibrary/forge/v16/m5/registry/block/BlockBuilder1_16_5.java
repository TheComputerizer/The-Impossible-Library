package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockProperty1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.Material1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.MaterialColor1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.block.AbstractBlock.Properties;
import net.minecraft.block.Block;
import net.minecraft.state.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class BlockBuilder1_16_5 extends BlockBuilderAPI {
    
    public BlockBuilder1_16_5(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_16_5 build() {
        Properties properties = Properties.of(((Material1_16_5)this.material).getMaterial(),
                ((MaterialColor1_16_5)this.materialColor).getMaterialColor());
        Collection<Property<?>> stateProperties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet())
            stateProperties.add(((BlockProperty1_16_5<?>)property).getProperty());
        TILBasicBlock1_16_5.stateProperties = stateProperties;
        Block block = Objects.nonNull(this.blockEntityCreator) ?
                new TILBlockEntityProvider1_16_5(properties,defaultStateBuilder(),this.useFunc,this.blockEntityCreator) :
                new TILBasicBlock1_16_5(properties,defaultStateBuilder(),this.useFunc);
        TILBasicBlock1_16_5.stateProperties = Collections.emptyList();
        block.setRegistryName(((ResourceLocation1_16_5)this.registryName).getInstance());
        return new Block1_16_5(block);
    }
}
