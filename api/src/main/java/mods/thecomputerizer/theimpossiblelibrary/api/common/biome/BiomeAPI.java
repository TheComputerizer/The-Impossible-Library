package mods.thecomputerizer.theimpossiblelibrary.api.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;

import java.util.Objects;
import java.util.Set;

public abstract class BiomeAPI<B> extends AbstractWrapped<B> implements RegistryEntryAPI<B> {
    
    protected ResourceLocationAPI<?> registryName;
    
    protected BiomeAPI(Object biome) {
        super(biome);
    }

    @IndirectCallers public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return TILRef.getCommonHandles().canBiomeRain(this.wrapped,world,pos);
    }
    
    @IndirectCallers public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return TILRef.getCommonHandles().canBiomeSnow(this.wrapped,world,pos);
    }
    
    @IndirectCallers public abstract float getRainfall();
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName) && Objects.nonNull(this.wrapped)) {
            RegistryAPI<?> registry = getRegistry();
            if(Objects.nonNull(registry)) this.registryName = registry.getKey(unwrap());
        }
        return this.registryName;
    }
    
    @IndirectCallers public abstract ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world);
    
    @IndirectCallers
    public Set<String> getTagNames(WorldAPI<?> world) {
        return TILRef.getCommonHandles().biomeTagNames(world,unwrap());
    }
    
    @IndirectCallers public abstract float getTemperatureAt(BlockPosAPI<?> pos);
    
    protected void setLocalRegistryName(ResourceLocationAPI<?> registryName) {
        this.registryName = registryName;
    }
}