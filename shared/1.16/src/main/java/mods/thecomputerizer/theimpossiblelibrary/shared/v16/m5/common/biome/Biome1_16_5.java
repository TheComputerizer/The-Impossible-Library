package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;
import static net.minecraft.world.biome.Biome.RainType.RAIN;
import static net.minecraft.world.biome.Biome.RainType.SNOW;

public class Biome1_16_5 extends BiomeAPI<Biome> {

    public Biome1_16_5(Object biome) {
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
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }

    public @Nullable RegistryKey<Biome> getRegistryKey(DynamicRegistries registries) {
        return registries.registryOrThrow(BIOME_REGISTRY).getResourceKey(unwrap()).orElse(null);
    }

    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        if(CoreAPI.getInstance().getModLoader().isForge()) {
            RegistryKey<Biome> key = getRegistryKey(((IWorld)world.getWrapped()).registryAccess());
            return BiomeDictionary.getTypes(key).stream().map(Type::getName).collect(Collectors.toSet());
        }
        return Collections.singleton(this.wrapped.getBiomeCategory().getName());
    }
}