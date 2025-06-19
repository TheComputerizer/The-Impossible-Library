package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.biome;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
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

@Setter
public class Biome1_18_2 extends BiomeAPI<Biome> {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    private static final String GET_TEMPERATURE = DEV ? "getTemperature" : (FORGE ? "m_47505_" : "method_21740");
    
    protected RegistryAccess access;
    
    public Biome1_18_2(Object biome) {
        super(biome instanceof Holder<?> ? (Biome)((Holder<?>)biome).value() : (Biome)biome);
    }
    
    @Override public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitation()==RAIN && !this.wrapped.shouldSnow(world.unwrap(),pos.unwrap());
    }
    
    @Override public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitation()==SNOW && this.wrapped.shouldSnow(world.unwrap(),pos.unwrap());
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) {
            if(Objects.isNull(this.access)) this.access = RegistryAccess.builtinCopy();
            this.registryName = getRegistryName(this.access);
        }
        return this.registryName;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        if(Objects.isNull(this.registryName))
            this.registryName = getRegistryName(((LevelAccessor)world.unwrap()).registryAccess());
        return this.registryName;
    }
    
    private ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Biome> registry = access.registry(BIOME_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        LevelAccessor access = world.unwrap();
        Registry<Biome> registry = access.registryAccess().registry(BIOME_REGISTRY).orElse(null);
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
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) Methods.invoke(this.wrapped,"setRegistryName",registryName.unwrap());
    }
}