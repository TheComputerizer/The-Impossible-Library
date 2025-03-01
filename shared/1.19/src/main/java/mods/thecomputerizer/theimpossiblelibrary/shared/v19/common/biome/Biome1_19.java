package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.minecraft.core.Registry.BIOME_REGISTRY;
import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class Biome1_19 extends BiomeAPI<Biome> {
    
    private static final String GET_TEMPERATURE = DEV ? "getTemperature" : (CoreAPI.isForge() ? "m_47505_" : "method_21740");

    public Biome1_19(Object biome) {
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
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return getRegistryName(RegistryAccess.builtinCopy());
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        return getRegistryName(((LevelAccessor)world.unwrap()).registryAccess());
    }
    
    protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Biome> registry = access.registry(BIOME_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        LevelAccessor access = world.unwrap();
        return getTagNames(access.registryAccess().registry(BIOME_REGISTRY).orElse(null));
    }
    
    protected Set<String> getTagNames(Registry<Biome> registry) {
        if(Objects.isNull(registry)) return Collections.emptySet();
        ResourceKey<Biome> key = registry.getResourceKey(this.wrapped).orElse(null);
        if(Objects.isNull(key)) return Collections.emptySet();
        Holder<Biome> holder = registry.getHolder(key).orElse(null);
        if(Objects.isNull(holder)) return Collections.emptySet();
        return holder.tags().map(tagKey -> tagKey.location().toString()).collect(Collectors.toSet());
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