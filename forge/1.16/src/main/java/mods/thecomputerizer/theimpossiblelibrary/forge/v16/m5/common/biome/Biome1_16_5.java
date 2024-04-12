package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.resource.ResourceLocation1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.BlockPos1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.world.World1_16_5;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.*;

import static net.minecraft.world.biome.Biome.RainType.RAIN;
import static net.minecraft.world.biome.Biome.RainType.SNOW;

public class Biome1_16_5 extends BiomeAPI<Biome> {

    public Biome1_16_5(Biome biome) {
        super(biome);
    }

    @Override
    public boolean canRain() {
        return this.biome.getPrecipitation()==RAIN;
    }

    @Override
    public boolean canSnow() {
        return this.biome.getPrecipitation()==SNOW;
    }

    public @Nullable RegistryKey<Biome> getRegistryKey(DynamicRegistries registries) {
        return registries.registryOrThrow(Registry.BIOME_REGISTRY).getResourceKey(this.biome).orElse(null);
    }

    @Override
    public float getRainfall() {
        return this.biome.getDownfall();
    }

    @Override
    public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        for(Type type : getTypes(((World1_16_5)world).getWorld().registryAccess())) tags.add(type.getName());
        return tags;
    }

    @Override
    public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.biome.getTemperature(((BlockPos1_16_5)pos).getPos());
    }

    private Set<Type> getTypes(DynamicRegistries registries) {
        RegistryKey<Biome> biomeKey = getRegistryKey(registries);
        return Objects.nonNull(biomeKey) ? BiomeDictionary.getTypes(biomeKey) : Collections.emptySet();
    }

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.biome.getRegistryName());
    }
}
