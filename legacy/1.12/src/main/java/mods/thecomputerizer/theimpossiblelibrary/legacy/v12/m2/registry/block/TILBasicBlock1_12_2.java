package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.BlockPos1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TILBasicBlock1_12_2 extends Block {
    
    public static IProperty<?>[] properties;
    
    protected final Function<TILItemUseContext,ActionResult> useFunc;
    private Map<Integer,IBlockState> metaMap; //There really isn't a good multiversion way of handling this
    
    public TILBasicBlock1_12_2(Material material, MapColor color,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer,
            @Nullable Function<TILItemUseContext,ActionResult> useFunc) {
        super(material,color);
        if(Objects.nonNull(stateTransformer))
            setDefaultState(((BlockState1_12_2)stateTransformer.apply(
                    new BlockState1_12_2(this.blockState.getBaseState()))).getState());
        this.useFunc = useFunc;
        this.metaMap = new Int2ObjectOpenHashMap<>();
        IBlockState defaultState = getDefaultState();
        this.metaMap.put(0,defaultState);
        setResistance(10f);
        setHardness(2f);
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
    
    @Override
    public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(Objects.isNull(this.useFunc)) return super.onBlockActivated(world,pos,state,player,hand,facing,hitX,hitY,hitZ);
        TILItemUseContext ctx = new TILItemUseContext(
                WrapperHelper.wrapPlayer(player),new World1_12_2(world),new BlockPos1_12_2(pos),new BlockState1_12_2(state),
                Events1_12_2.getHand(hand),Events1_12_2.getFacing(facing));
        return this.useFunc.apply(ctx)==SUCCESS;
    }
}
