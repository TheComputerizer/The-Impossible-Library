package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.Block1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockProperty1_16_5;
import net.minecraft.block.Block;
import net.minecraft.state.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BlockBuilder1_16_5 extends BlockBuilderAPI {
    
    public BlockBuilder1_16_5(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_16_5 build() {
        BlockProperties properties = buildProperties();
        Collection<Property<?>> stateProperties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet())
            stateProperties.add(((BlockProperty1_16_5<?>)property).getProperty());
        TILBasicBlock1_16_5.stateProperties = stateProperties;
        Block block = properties.isBlockEntity() ? new TILBlockEntityProvider1_16_5(properties) :
                new TILBasicBlock1_16_5(properties);
        TILBasicBlock1_16_5.stateProperties = Collections.emptyList();
        return new Block1_16_5(block);
    }
}
