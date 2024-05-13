package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumBlockRenderType;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TILBasicBlock1_12_2 extends Block {
    
    public static IProperty<?>[] properties;
    private Map<Integer,IBlockState> metaMap; //There really isn't a good multiversion way of handling this
    
    public TILBasicBlock1_12_2(Material material, MapColor color,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer) {
        super(material,color);
        if(Objects.nonNull(stateTransformer))
            setDefaultState(((BlockState1_12_2)stateTransformer.apply(
                    new BlockState1_12_2(this.blockState.getBaseState()))).getState());
        this.metaMap = new Int2ObjectOpenHashMap<>();
        IBlockState defaultState = getDefaultState();
        this.metaMap.put(0,defaultState);
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        IProperty<?>[] propertiesArray = Objects.nonNull(properties) ? properties : new IProperty[]{};
        TILDev.logInfo("Initializing block with {} states",propertiesArray.length);
        
        return new BlockStateContainer(this,propertiesArray);
    }
    
    @SuppressWarnings("deprecation") @Override
    public EnumBlockRenderType getRenderType(IBlockState state) {
        return EnumBlockRenderType.MODEL;
    }
    
    @SuppressWarnings("deprecation")
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }
    
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
}
