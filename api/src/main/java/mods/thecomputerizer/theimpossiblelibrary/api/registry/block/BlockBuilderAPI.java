package mods.thecomputerizer.theimpossiblelibrary.api.registry.block;

import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockPropertyAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.blockentity.BlockEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.TILItemUseContext;
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
    protected Map<String,Integer> effectiveTools;
    protected MaterialAPI<?> material;
    protected MaterialColorAPI<?> materialColor;
    protected Function<TILItemUseContext,ActionResult> useFunc;
    
    protected BlockBuilderAPI(@Nullable BlockBuilderAPI parent) {
        this.defaultProperties = new HashMap<>();
        this.effectiveTools = new Object2IntOpenHashMap<>();
        if(Objects.nonNull(parent)) {
            this.blockEntityCreator = parent.blockEntityCreator;
            this.defaultProperties.putAll(parent.defaultProperties);
            this.effectiveTools.putAll(parent.effectiveTools);
            this.material = parent.material;
            this.materialColor = parent.materialColor;
            this.useFunc = parent.useFunc;
        } else {
            this.blockEntityCreator = null;
            this.material = MaterialHelper.getByName("AIR");
            this.materialColor = MaterialHelper.getColorByName("GRASS");
            this.useFunc = null;
        }
    }
    
    public <V extends Comparable<V>> BlockBuilderAPI addDefaultProperty(BlockPropertyAPI<?,V> property, V value) {
        this.defaultProperties.put(property,value);
        return this;
    }
    
    public BlockBuilderAPI addEffectiveTool(String tool, int tier) {
        this.effectiveTools.put(tool,tier);
        return this;
    }
    
    @SuppressWarnings("unchecked") public <V extends Comparable<V>> BlockStateAPI<?> buildDefaultProperty(
            BlockStateAPI<?> state, BlockPropertyAPI<?,V> property, Comparable<?> value) {
        return state.withProperty(property,(V)value);
    }
    
    public BlockProperties buildProperties() {
        return new BlockProperties(this.material,this.materialColor,this.effectiveTools,this.registryName,
                                   defaultStateBuilder(),this.useFunc,this.blockEntityCreator);
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
    
    public BlockBuilderAPI setUseFunc(Function<TILItemUseContext,ActionResult> func) {
        this.useFunc = func;
        return this;
    }
}