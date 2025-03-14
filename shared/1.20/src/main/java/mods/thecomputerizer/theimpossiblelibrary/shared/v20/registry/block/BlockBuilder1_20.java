package mods.thecomputerizer.theimpossiblelibrary.shared.v20.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.world.level.block.state.properties.Property;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BlockBuilder1_20 extends BlockBuilderAPI {
    
    public BlockBuilder1_20(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockAPI<?> build() {
        BlockProperties properties = buildProperties();
        Collection<Property<?>> stateProperties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet()) stateProperties.add(property.unwrap());
        TILBasicBlock1_20.stateProperties = stateProperties;
        BlockAPI<?> block = WrapperHelper.wrapBlock(properties.isBlockEntity() ?
                        TILBlockEntityProvider1_20.tileFrom(properties) : TILBasicBlock1_20.basicFrom(properties));
        block.setRegistryName(properties.getRegistryName());
        TILBasicBlock1_20.stateProperties = Collections.emptyList();
        return block;
    }
}