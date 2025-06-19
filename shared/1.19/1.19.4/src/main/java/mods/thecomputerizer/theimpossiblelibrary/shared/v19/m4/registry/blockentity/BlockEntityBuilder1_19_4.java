package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.registry.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.registry.blockentity.BlockEntityBuilderAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.registry.blockentity.BlockEntityBuilder1_19;
import net.minecraft.core.Registry;
import net.minecraft.world.level.block.entity.BlockEntityType;
import org.jetbrains.annotations.Nullable;

import static net.minecraft.core.registries.BuiltInRegistries.BLOCK_ENTITY_TYPE;

public class BlockEntityBuilder1_19_4 extends BlockEntityBuilder1_19 {
    
    public BlockEntityBuilder1_19_4(@Nullable BlockEntityBuilderAPI parent) {
        super(parent);
    }
    
    @Override protected Registry<BlockEntityType<?>> getRegistry() {
        return BLOCK_ENTITY_TYPE;
    }
}
