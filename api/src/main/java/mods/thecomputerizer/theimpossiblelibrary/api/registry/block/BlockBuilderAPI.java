package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryBuilder;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Objects;
import java.util.function.BiFunction;
import java.util.function.Function;

@SuppressWarnings("unused")
public abstract class BlockBuilderAPI extends RegistryEntryBuilder<BlockAPI<?>> {
    
    protected BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> blockEntityCreator;
    protected Map<BlockPropertyAPI<?,?>,Comparable<?>> defaultProperties;
    protected MaterialAPI<?> material;
    protected MaterialColorAPI<?> materialColor;
    
    protected BlockBuilderAPI(@Nullable BlockBuilderAPI parent) {
        this.defaultProperties = new HashMap<>();
        if(Objects.nonNull(parent)) {
            this.blockEntityCreator = parent.blockEntityCreator;
            this.defaultProperties.putAll(parent.defaultProperties);
            this.material = parent.material;
            this.materialColor = parent.materialColor;
        } else {
            this.blockEntityCreator = null;
            this.material = MaterialHelper.getByName("AIR");
            this.materialColor = MaterialHelper.getColorByName("GRASS");
        }
    }
    
    public <V extends Comparable<V>> BlockBuilderAPI addDefaultProperty(BlockPropertyAPI<?,V> property, V value) {
        this.defaultProperties.put(property,value);
        return this;
    }
    
    @SuppressWarnings("unchecked") public <V extends Comparable<V>> BlockStateAPI<?> buildDefaultProperty(
            BlockStateAPI<?> state, BlockPropertyAPI<?,V> property, Comparable<?> value) {
        return state.withProperty(property,(V)value);
    }
    
    public @Nullable Function<BlockStateAPI<?>,BlockStateAPI<?>> defaultStateBuilder() {
        if(this.defaultProperties.isEmpty()) return null;
        return state -> {
            BlockStateAPI<?> next = state;
            for(Entry<BlockPropertyAPI<?,?>,Comparable<?>> entry : this.defaultProperties.entrySet())
                next = buildDefaultProperty(next,entry.getKey(),entry.getValue());
            return next;
        };
    }
    
    public BlockBuilderAPI setBlockEntityCreator(BiFunction<WorldAPI<?>,BlockStateAPI<?>,BlockEntityAPI<?,?>> creator) {
        this.blockEntityCreator = creator;
        return this;
    }
    
    public BlockBuilderAPI setMaterial(MaterialAPI<?> material) {
        this.material = material;
        return this;
    }
    
    public BlockBuilderAPI setMaterialColor(MaterialColorAPI<?> color) {
        this.materialColor = color;
        return this;
    }
    
    @Override
    public BlockBuilderAPI setRegistryName(ResourceLocationAPI<?> name) {
        this.registryName = name;
        return this;
    }
}