package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.util.ActionResultType.PASS;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILBasicBlock1_16_5 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_16_5(BlockProperties properties) {
        super(Properties.of((Material)properties.getMaterial().getMaterial(),
                            (MaterialColor)properties.getMaterialColor().getMaterialColor()));
        if(properties.hasStateTransformer())
            registerDefaultState((BlockState)properties.getDefaultState(
                    new BlockState1_16_5(this.stateDefinition.any())).getState());
        this.properties = properties;
        setRegistryName(((ResourceLocation1_16_5)properties.getRegistryName()).getInstance());
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @SuppressWarnings("deprecation") @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockRayTraceResult hit) {
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(new TILItemUseContext(
                    WrapperHelper.wrapPlayer(player), new World1_16_5(world), new BlockPos1_16_5(pos),
                    new BlockState1_16_5(state),Events1_16_5.getHand(hand),null))==SUCCESS ?
                    ActionResultType.sidedSuccess(world.isClientSide) : PASS;
        }
        return super.use(state,world,pos,player,hand,hit);
    }
}
