package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;
import static net.minecraft.world.biome.Biome.RainType.RAIN;
import static net.minecraft.world.biome.Biome.RainType.SNOW;

public abstract class Biome1_16_5 extends BiomeAPI<Biome> {

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
        return registries.registryOrThrow(BIOME_REGISTRY).getResourceKey(this.biome).orElse(null);
    }

    @Override
    public float getRainfall() {
        return this.biome.getDownfall();
    }

    @Override
    public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        getTypes(tags,getRegistryKey(((IWorld)world.getWorld()).registryAccess()));
        return tags;
    }

    @Override
    public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.biome.getTemperature((BlockPos)pos.getPos());
    }

    protected abstract void getTypes(Set<String> typeSet, @Nullable RegistryKey<Biome> biomeKey);

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(this.biome.getRegistryName());
    }
}
