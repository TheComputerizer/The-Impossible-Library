package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ArrayHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Block1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockProperty1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.Material1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.MaterialColor1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;

import javax.annotation.Nullable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class BlockBuilder1_12_2 extends BlockBuilderAPI {
    
    public BlockBuilder1_12_2(@Nullable BlockBuilderAPI parent) {
        super(parent);
    }
    
    @Override public Block1_12_2 build() {
        Material material = ((Material1_12_2)this.material).getMaterial();
        MapColor materialColor = ((MaterialColor1_12_2)this.materialColor).getMaterialColor();
        List<IProperty<?>> properties = new ArrayList<>();
        for(BlockPropertyAPI<?,?> property : this.defaultProperties.keySet())
            properties.add(((BlockProperty1_12_2<?>)property).getProperty());
        TILBasicBlock1_12_2.properties = ArrayHelper.fromIterable(properties,IProperty.class); //Kind of a gross implementation
        Block block = Objects.nonNull(this.blockEntityCreator) ?
                new TILBlockEntityProvider1_12_2(material,materialColor,defaultStateBuilder(),this.blockEntityCreator) :
                new TILBasicBlock1_12_2(material,materialColor,defaultStateBuilder());
        TILBasicBlock1_12_2.properties = null;
        block.setRegistryName(((ResourceLocation1_12_2)this.registryName).getInstance());
        return new Block1_12_2(block);
    }
}
