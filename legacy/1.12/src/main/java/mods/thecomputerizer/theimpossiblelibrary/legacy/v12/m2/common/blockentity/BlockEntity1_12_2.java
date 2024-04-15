package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.blockentity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.resource.ResourceLocation1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.BlockPos1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.world.World1_12_2;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ResourceLocation;

import java.util.Objects;

public class BlockEntity1_12_2 extends BlockEntityAPI<TileEntity,Class<? extends TileEntity>> {

    public BlockEntity1_12_2(TileEntity tile) {
        super(tile,tile.getClass());
    }

    @Override
    public RegistryAPI<?> getRegistry() {
        return RegistryHelper.getBlockEntityRegistry();
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        ResourceLocation name = TileEntity.getKey(this.type);
        return Objects.nonNull(name) ? new ResourceLocation1_12_2(name) : null;
    }

    @Override
    public BlockPosAPI<?> getPos() {
        return new BlockPos1_12_2(this.entity.getPos());
    }

    @Override
    public WorldAPI<?> getWorld() {
        return new World1_12_2(this.entity.getWorld());
    }
}
