package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.biome;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Holder;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.Biome.ClimateSettings;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.core.registries.BuiltInRegistries.REGISTRY;
import static net.minecraft.core.registries.Registries.BIOME;
import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@Setter public class Biome1_21 extends BiomeAPI<Biome> {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    static final String CLIMATE_SETTINGS = NAMED_ENV ? "climateSettings" : "field_26393";
    private static final String GET_TEMPERATURE = NAMED_ENV ? "getTemperature" : (SRG_ENV ? "m_47505_" : "method_21740");

    protected RegistryAccess access;
    
    public Biome1_21(Object biome) {
        super((Biome)biome);
    }
    
    @Override public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitationAt(pos.unwrap())==RAIN;
    }
    
    @Override public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitationAt(pos.unwrap())==SNOW;
    }
    
    @Override public float getRainfall() {
        if(FORGE_OR_NEOFORGE) return this.wrapped.getModifiedClimateSettings().downfall();
        ClimateSettings climate = Fields.getDirect(this.wrapped,CLIMATE_SETTINGS);
        return climate.downfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) {
            if(Objects.isNull(this.access)) {
                if(CoreAPI.isClient()) {
                    ClientLevel level = Minecraft.getInstance().level;
                    this.access = Objects.nonNull(level) ? level.registryAccess() :
                            RegistryAccess.fromRegistryOfRegistries(REGISTRY);
                } else this.access = RegistryAccess.fromRegistryOfRegistries(REGISTRY);
            }
            this.registryName = getRegistryName(this.access);
        }
        return this.registryName;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        if(Objects.isNull(this.registryName))
            this.registryName = getRegistryName(((LevelAccessor)world.unwrap()).registryAccess());
        return this.registryName;
    }
    
    protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Biome> registry = access.registry(BIOME).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        LevelAccessor access = world.unwrap();
        Registry<Biome> registry = access.registryAccess().registry(BIOME).orElse(null);
        if(Objects.isNull(registry)) return Collections.emptySet();
        ResourceKey<Biome> key = registry.getResourceKey(this.wrapped).orElse(null);
        if(Objects.isNull(key)) return Collections.emptySet();
        return getTagNames(registry.getHolder(key).orElse(null));
    }
    
    protected Set<String> getTagNames(Holder<Biome> holder) {
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
        setLocalRegistryName(registryName); //There is no built-in registryName field for forge in 1.19.+
    }
}