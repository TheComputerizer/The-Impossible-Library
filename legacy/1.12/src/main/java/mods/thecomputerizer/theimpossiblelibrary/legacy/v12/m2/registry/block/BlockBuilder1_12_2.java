package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockProperty1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;

public class BlockBuilder1_12_2 extends BlockBuilderAPI {
    
    public BlockBuilder1_12_2(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_12_2 build() {
        BlockProperties properties = buildProperties();
        List<IProperty<?>> blockProperties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet())
            blockProperties.add(((BlockProperty1_12_2<?>)property).getProperty());
        TILBasicBlock1_12_2.iProperties = ArrayHelper.fromIterable(blockProperties,IProperty.class); //Kind of a gross implementation
        Block block = properties.isBlockEntity() ? new TILBlockEntityProvider1_12_2(properties) :
                new TILBasicBlock1_12_2(properties);
        TILBasicBlock1_12_2.iProperties = null;
        return new Block1_12_2(block);
    }
}
