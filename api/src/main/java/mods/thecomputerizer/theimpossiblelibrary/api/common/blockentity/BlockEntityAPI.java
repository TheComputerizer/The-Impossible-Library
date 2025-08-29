package mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockBuilderAPI.BlockEntityCreator;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.util.GenericUtils;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import org.jetbrains.annotations.Nullable;

import java.util.Objects;

@Getter
public abstract class BlockEntityAPI<E,T> extends AbstractWrapped<T> implements RegistryEntryAPI<T> {
    
    protected final E entity;
    protected ResourceLocationAPI<?> registryName;
    @Setter protected BlockEntityCreator creator;

    protected BlockEntityAPI(@Nullable Object entity, Object type) {
        super(type);
        this.entity = GenericUtils.cast(entity);
        if(Objects.nonNull(this.entity)) {
            this.creator = (world,pos,state) -> this;
        }
    }
    
    @IndirectCallers
    public BlockEntityAPI<?,?> createFromReference(WorldAPI<?> world, BlockPosAPI<?> pos, BlockStateAPI<?> state) {
        return Objects.nonNull(this.creator) ? this.creator.create(world,pos,state) : null;
    }
    
    @Override public boolean equals(Object other) {
        if(super.equals(other) && other instanceof BlockEntityAPI<?,?>) {
            Object entity = getEntity();
            Object otherEntity = ((BlockEntityAPI<?,?>)other).getEntity();
            return Objects.isNull(otherEntity) ? Objects.isNull(entity) : entity.equals(otherEntity);
        }
        return false;
    }
    
    public abstract BlockPosAPI<?> getPos();
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName) && Objects.nonNull(this.wrapped)) {
            RegistryAPI<?> registry = getRegistry();
            if(Objects.nonNull(registry)) this.registryName = registry.getKey(unwrap());
        }
        return this.registryName;
    }
    
    public BlockStateAPI<?> getState() {
        WorldAPI<?> world = getWorld();
        return Objects.nonNull(world) ? world.getStateAt(getPos()) : null;
    }

    public abstract WorldAPI<?> getWorld();
    @IndirectCallers public abstract CompoundTagAPI<?> readTagFrom();
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
    
    @IndirectCallers public abstract void writeTagTo(CompoundTagAPI<?> tag);
}