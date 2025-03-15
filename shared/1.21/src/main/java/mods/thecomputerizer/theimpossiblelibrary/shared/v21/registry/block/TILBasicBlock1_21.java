package mods.thecomputerizer.theimpossiblelibrary.shared.v21.registry.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.phys.BlockHitResult;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.world.ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;

@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault @Getter
public class TILBasicBlock1_21 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    public static TILBasicBlock1_21 basicFrom(BlockProperties properties) {
        return new TILBasicBlock1_21(Properties.of(),properties);
    }
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_21(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties);
        if(properties.hasStateTransformer())
            registerDefaultState(properties.getDefaultState(WrapperHelper.wrapState(this.stateDefinition.any())).unwrap());
        this.properties = properties;
    }
    
    @Override protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @Override public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
            Player player, InteractionHand hand, BlockHitResult hit) {
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(TILItemUseContext.wrap(player,level,pos,state,hand,null))==SUCCESS ?
                    ItemInteractionResult.sidedSuccess(level.isClientSide) : PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }
        return super.useItemOn(stack,state,level,pos,player,hand,hit);
    }
}