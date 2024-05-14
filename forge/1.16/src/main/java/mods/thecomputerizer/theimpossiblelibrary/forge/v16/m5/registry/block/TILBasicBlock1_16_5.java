package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.registry.block;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.Events1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;
import java.util.Objects;
import java.util.function.Function;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.util.ActionResultType.PASS;

@MethodsReturnNonnullByDefault
@ParametersAreNonnullByDefault
public class TILBasicBlock1_16_5 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    protected final Function<TILItemUseContext,ActionResult> useFunc;
    
    public TILBasicBlock1_16_5(Properties properties,
            @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> stateTransformer,
            @Nullable Function<TILItemUseContext,ActionResult> useFunc) {
        super(properties);
        if(Objects.nonNull(stateTransformer))
            registerDefaultState(((BlockState1_16_5)stateTransformer.apply(
                    new BlockState1_16_5(this.stateDefinition.any()))).getState());
        this.useFunc = useFunc;
    }
    
    @Override
    protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @SuppressWarnings("deprecation") @Override
    public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockRayTraceResult hit) {
        if(Objects.isNull(this.useFunc)) return super.use(state,world,pos,player,hand,hit);
        TILItemUseContext ctx = new TILItemUseContext(
                WrapperHelper.wrapPlayer(player), new World1_16_5(world), new BlockPos1_16_5(pos),
                new BlockState1_16_5(state),Events1_16_5.getHand(hand),null);
        return this.useFunc.apply(ctx)==SUCCESS ? ActionResultType.sidedSuccess(world.isClientSide) : PASS;
    }
}
