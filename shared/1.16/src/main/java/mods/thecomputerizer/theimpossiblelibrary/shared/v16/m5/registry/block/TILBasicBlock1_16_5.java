package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.registry.block;

import mcp.MethodsReturnNonnullByDefault;
import mods.thecomputerizer.theimpossiblelibrary.api.common.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block.BlockState1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.world.BlockPos1_16_5;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.state.Property;
import net.minecraft.state.StateContainer.Builder;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.World;

import javax.annotation.ParametersAreNonnullByDefault;
import java.util.Collection;
import java.util.Collections;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult.SUCCESS;
import static net.minecraft.util.ActionResultType.PASS;

@SuppressWarnings("deprecation")
@MethodsReturnNonnullByDefault @ParametersAreNonnullByDefault
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
        setRegistryName((ResourceLocation)properties.getRegistryName().unwrap());
    }
    
    @Override protected void createBlockStateDefinition(Builder<Block,BlockState> builder) {
        for(Property<?> property : stateProperties) builder.add(property);
    }
    
    @Override public ActionResultType use(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand,
            BlockRayTraceResult hit) {
        if(this.properties.hasUseResult()) {
            return this.properties.getUseResult(TILItemUseContext.wrap(player,world,pos,state,hand,null))==SUCCESS ?
                    ActionResultType.sidedSuccess(world.isClientSide) : PASS;
        }
        return super.use(state,world,pos,player,hand,hit);
    }
}