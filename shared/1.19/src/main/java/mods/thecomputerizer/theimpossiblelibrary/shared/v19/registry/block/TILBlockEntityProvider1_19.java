package mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockProperties;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.blockentity.TILTickableBlockEntity1_19;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.Material;
import net.minecraft.world.level.material.MaterialColor;
import org.jetbrains.annotations.Nullable;

import javax.annotation.ParametersAreNonnullByDefault;

@ParametersAreNonnullByDefault
public class TILBlockEntityProvider1_19 extends TILBasicBlock1_19 implements EntityBlock {
    
    public static TILBlockEntityProvider1_19 tileFrom(BlockProperties properties) {
        Material material = properties.getMaterial().unwrap();
        MaterialColor color = properties.getMaterialColor().unwrap();
        return new TILBlockEntityProvider1_19(Properties.of(material,color),properties);
    }
    
    public TILBlockEntityProvider1_19(Properties vanillaProperties, BlockProperties properties) {
        super(vanillaProperties,properties);
    }
    
    @Override public @Nullable <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level l, BlockState s,
            BlockEntityType<T> type) {
        return (level,pos,state,entity) -> {
            if(entity instanceof TILTickableBlockEntity1_19) ((TILTickableBlockEntity1_19)entity).tick();
        };
    }
    
    @Override public @Nullable BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return (BlockEntity)this.properties.createBlockEntity(null,WrapperHelper.wrapPosition(pos),
                                                              WrapperHelper.wrapState(state)).getEntity();
    }
}