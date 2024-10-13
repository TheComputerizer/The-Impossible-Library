package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import net.minecraft.MethodsReturnNonnullByDefault;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
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

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.world.InteractionResult.PASS;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
public class TILBasicBlock1_18_2 extends Block {
    
    public static Collection<Property<?>> stateProperties = Collections.emptyList();
    
    public static TILBasicBlock1_18_2 basicFrom(BlockProperties properties) {
        Material material = properties.getMaterial().unwrap();
        MaterialColor color = properties.getMaterialColor().unwrap();
        return new TILBasicBlock1_18_2(Properties.of(material,color),properties);
    }
    
    protected final BlockProperties properties;
    
    public TILBasicBlock1_18_2(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties);
        if(properties.hasStateTransformer())
            registerDefaultState(properties.getDefaultState(WrapperHelper.wrapState(this.stateDefinition.any())).unwrap());
        this.properties = properties;
        setRegistryName((ResourceLocation)properties.getRegistryName().unwrap());
    }
    
    @Override protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @Override public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player,
            InteractionHand hand, BlockHitResult hit) {
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(TILItemUseContext.wrap(player,level,pos,state,hand,null))==SUCCESS ?
                    InteractionResult.sidedSuccess(level.isClientSide) : PASS;
        }
        return super.use(state,level,pos,player,hand,hit);
    }
}