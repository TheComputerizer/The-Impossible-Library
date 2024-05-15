package mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.tag.CompoundTagAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("unused") @Getter
public abstract class BlockEntityAPI<E,T> implements RegistryEntryAPI<T> {
    
    protected final E entity;
    protected final T type;
    
    @Setter protected Function<T,BlockEntityAPI<?,?>> creator;

    protected BlockEntityAPI(E entity, T type) {
        this.entity = entity;
        this.type = type;
        if(Objects.nonNull(this.entity)) this.creator = t -> this;
    }
    
    public BlockEntityAPI<?,?> createFromReference() {
        return Objects.nonNull(this.creator) ? this.creator.apply(this.type) : null;
    }
    
    public abstract BlockPosAPI<?> getPos();
    
    public BlockStateAPI<?> getState() {
        WorldAPI<?> world = getWorld();
        return Objects.nonNull(world) ? world.getStateAt(getPos()) : null;
    }

    @Override
    public T getValue() {
        return this.type;
    }

    @SuppressWarnings("unchecked")
    public Class<? extends T> getValueClass() {
        return (Class<? extends T>)this.type.getClass();
    }

    public abstract WorldAPI<?> getWorld();
    public abstract CompoundTagAPI<?> readTagFrom();
    public abstract void setRegistryName(ResourceLocationAPI<?> registryName); //1.12.2 doesnt have proper tile entity types
    public abstract void writeTagTo(CompoundTagAPI<?> tag);
}