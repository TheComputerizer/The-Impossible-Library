package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.registry.block;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.block.Block;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumBlockRenderType;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Map.Entry;
import java.util.Objects;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.util.EnumBlockRenderType.MODEL;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILBasicBlock1_12_2 extends Block {
    
    public static IProperty<?>[] iProperties;
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_12_2(BlockProperties properties) {
        super(properties.getMaterial().unwrap(),properties.getMaterialColor().unwrap());
        if(properties.hasStateTransformer())
            setDefaultState(properties.getDefaultState(WrapperHelper.wrapState(this.blockState.getBaseState())).unwrap());
        this.properties = properties;
        setResistance(10f);
        setHardness(2f);
        for(Entry<String,Integer> tool : properties.getEffectiveTools().entrySet())
            setHarvestLevel(tool.getKey(),tool.getValue());
        ResourceLocation name = properties.getRegistryName().unwrap();
        setRegistryName(name);
        setTranslationKey(name.getNamespace()+"."+name.getPath());
    }
    
    @Override protected BlockStateContainer createBlockState() {
        IProperty<?>[] propertiesArray = Objects.nonNull(iProperties) ? iProperties : new IProperty[]{};
        TILDev.logInfo("Initializing block with {} states",propertiesArray.length);
        return new BlockStateContainer(this,propertiesArray);
    }
    
    @Override public EnumBlockRenderType getRenderType(IBlockState state) {
        return MODEL;
    }
    
    public IBlockState getStateFromMeta(int meta) {
        return this.getDefaultState();
    }
    
    public int getMetaFromState(IBlockState state) {
        return 0;
    }
    
    @Override public boolean onBlockActivated(World world, BlockPos pos, IBlockState state, EntityPlayer player,
            EnumHand hand, EnumFacing facing, float hitX, float hitY, float hitZ) {
        if(this.properties.hasUseResult())
            return this.properties.getUseResult(TILItemUseContext.wrap(player,world,pos,state,hand,facing))==SUCCESS;
        return super.onBlockActivated(world,pos,state,player,hand,facing,hitX,hitY,hitZ);
    }
}
