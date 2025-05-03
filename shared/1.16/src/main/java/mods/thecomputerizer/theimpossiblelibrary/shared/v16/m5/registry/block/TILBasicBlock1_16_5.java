package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.StateDefinition.Builder;
import net.minecraft.world.level.block.state.properties.Property;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import net.minecraft.world.phys.BlockHitResult;

import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.world.InteractionResult.PASS;

@SuppressWarnings("deprecation")
public class TILBasicBlock1_16_5 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    public static TILBasicBlock1_16_5 basicFrom(BlockProperties properties) {
        Material material = properties.getMaterial().unwrap();
        MaterialColor color = properties.getMaterialColor().unwrap();
        return new TILBasicBlock1_16_5(Properties.of(material,color),properties);
    }
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_16_5(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties);
        if(properties.hasStateTransformer())
            registerDefaultState(properties.getDefaultState(WrapperHelper.wrapState(this.stateDefinition.any())).unwrap());
        this.properties = properties;
    }
    
    @Override protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @Override public InteractionResult use(BlockState state, Level world, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(TILItemUseContext.wrap(player,world,pos,state,hand,null))==SUCCESS ?
                    InteractionResult.sidedSuccess(world.isClientSide) : PASS;
        }
        return super.use(state,world,pos,player,hand,hit);
    }
}