package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.biome.Biome1_19;
import net.minecraft.client.Minecraft;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILDev.DEV;
import static net.minecraft.core.registries.BuiltInRegistries.REGISTRY;
import static net.minecraft.core.registries.Registries.BIOME;
import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;
import static org.burningwave.core.assembler.StaticComponentContainer.Fields;

public class Biome1_19_4 extends Biome1_19 {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    static final String CLIMATE_SETTINGS = DEV ? "climateSettings" : SRG_ENV ? "f_47437_" : "field_26393";
    static final String DOWNFALL = DEV ? "downfall" : "f_47683_";

    public Biome1_19_4(Object biome) {
        super(biome);
    }
    
    @Override public boolean canRain(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitationAt(pos.unwrap())==RAIN;
    }
    
    @Override public boolean canSnow(WorldAPI<?> world, BlockPosAPI<?> pos) {
        return this.wrapped.getPrecipitationAt(pos.unwrap())==SNOW;
    }
    
    @Override public float getRainfall() {
        return Fields.getDirect(Fields.getDirect(this.wrapped,CLIMATE_SETTINGS),DOWNFALL);
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
    
    @Override protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
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
}