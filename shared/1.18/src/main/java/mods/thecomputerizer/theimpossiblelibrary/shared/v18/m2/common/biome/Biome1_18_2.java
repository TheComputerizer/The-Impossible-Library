package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.minecraft.core.Registry.BIOME_REGISTRY;
import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class Biome1_18_2 extends BiomeAPI<Biome> {
    
    private static final String GET_TEMPERATURE = DEV ? "getTemperature" : (CoreAPI.isForge() ? "m_47505_" : "method_21740");

    public Biome1_18_2(Object biome) {
        super((Biome)biome);
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
        Registry<Biome> registry = getRegistry(world.unwrap());
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Registry<Biome> registry = getRegistry(world.unwrap());
        Holder<Biome> holder = registry.getOrCreateHolder(registry.getResourceKey(this.wrapped).orElseThrow());
        return holder.tags().map(key -> key.location().toString()).collect(Collectors.toSet());
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        try {
            return Methods.invokeDirect(this.wrapped,GET_TEMPERATURE,pos.getWrapped());
        } catch(Throwable t) {
            TILRef.logError("Failed to get temperature for biome {} at {}",this.wrapped,pos.getWrapped(),t);
            return this.wrapped.getBaseTemperature();
        }
    }
}