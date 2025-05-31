package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.world.level.block.state.properties.Property;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;

public class BlockBuilder1_19 extends BlockBuilderAPI {
    
    public BlockBuilder1_19(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public BlockAPI<?> build() {
        BlockProperties properties = buildProperties();
        Collection<Property<?>> stateProperties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet()) stateProperties.add(property.unwrap());
        TILBasicBlock1_19.stateProperties = stateProperties;
        BlockAPI<?> block = WrapperHelper.wrapBlock(properties.isBlockEntity() ?
                        TILBlockEntityProvider1_19.tileFrom(properties) : TILBasicBlock1_19.basicFrom(properties));
        block.setRegistryName(properties.getRegistryName());
        TILBasicBlock1_19.stateProperties = Collections.emptyList();
        return block;
    }
}