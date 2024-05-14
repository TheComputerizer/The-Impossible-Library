package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block.BlockState1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.event.Events1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map.Entry;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;

@ParametersAreNonnullByDefault
@MethodsReturnNonnullByDefault
public class TILBasicBlock1_12_2 extends Block {
    
    public static IProperty<?>[] iProperties;
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_12_2(BlockProperties properties) {
        super((Material)properties.getMaterial().getMaterial(),(MapColor)properties.getMaterialColor().getMaterialColor());
        if(properties.hasStateTransformer())
            setDefaultState(((BlockState1_12_2)properties.getDefaultState(
                    new BlockState1_12_2(this.blockState.getBaseState()))).getState());
        this.properties = properties;
        setResistance(10f);
        setHardness(2f);
        for(Entry<String,Integer> tool : properties.getEffectiveTools().entrySet())
            setHarvestLevel(tool.getKey(),tool.getValue());
        ResourceLocation1_12_2 name = (ResourceLocation1_12_2)properties.getRegistryName();
        setRegistryName(name.getInstance());
        setTranslationKey(name.getNamespace()+"."+name.getPath());
    }
    
    @Override
    protected BlockStateContainer createBlockState() {
        IProperty<?>[] propertiesArray = Objects.nonNull(iProperties) ? iProperties : new IProperty[]{};
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
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(new TILItemUseContext(
                    WrapperHelper.wrapPlayer(player),new World1_12_2(world),new BlockPos1_12_2(pos),new BlockState1_12_2(state),
                    Events1_12_2.getHand(hand),Events1_12_2.getFacing(facing)))==SUCCESS;
        }
        return super.onBlockActivated(world,pos,state,player,hand,facing,hitX,hitY,hitZ);
    }
}
