package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.lang.invoke.MethodHandle;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.core.Registry.BIOME_REGISTRY;
import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;

public class Biome1_18_2 extends BiomeAPI<Biome> {
    
    private final MethodHandle tempHandle;

    public Biome1_18_2(Object biome) {
        super((Biome)biome);
        this.tempHandle = Objects.nonNull(biome) ?
                ReflectionHelper.findMethodHandle(Biome.class,"getTemperature",BlockPos.class) : null;
    }
    
    @Override public boolean canRain() {
        return this.wrapped.getPrecipitation()==RAIN;
    }
    
    @Override public boolean canSnow() {
        return this.wrapped.getPrecipitation()==SNOW;
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    Registry<Biome> getRegistry(LevelAccessor world) {
        return world.registryAccess().registryOrThrow(BIOME_REGISTRY);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        Registry<Biome> registry = getRegistry((LevelAccessor)world);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Registry<Biome> registry = getRegistry((LevelAccessor)world);
        Holder<Biome> holder = registry.getOrCreateHolder(registry.getResourceKey(this.wrapped).orElseThrow());
        return holder.tags().map(key -> key.location().toString()).collect(Collectors.toSet());
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        if(Objects.isNull(this.tempHandle)) return this.wrapped.getBaseTemperature();
        try {
            return (float)this.tempHandle.invoke(this.wrapped,pos.unwrap());
        } catch(Throwable t) {
            TILRef.logError("Failed to get get temperature for biome {} at {}",this.wrapped,pos.getWrapped(),t);
            return this.wrapped.getBaseTemperature();
        }
    }
}