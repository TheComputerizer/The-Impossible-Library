package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.resource.ResourceLocation1_16_5;
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

public abstract class Biome1_16_5<B> extends BiomeAPI<B> {

    public Biome1_16_5(B biome) {
        super(biome);
    }

    @Override
    public boolean canRain() {
        return ((Biome)this.biome).getPrecipitation()==RAIN;
    }

    @Override
    public boolean canSnow() {
        return ((Biome)this.biome).getPrecipitation()==SNOW;
    }

    @SuppressWarnings("unchecked")
    public <K> @Nullable K getRegistryKey(DynamicRegistries registries) {
        return (K)registries.registryOrThrow(BIOME_REGISTRY).getResourceKey((Biome)this.biome).orElse(null);
    }

    @Override
    public float getRainfall() {
        return ((Biome)this.biome).getDownfall();
    }

    @Override
    public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        getTypes(tags,getRegistryKey(((IWorld)world.getWorld()).registryAccess()));
        return tags;
    }

    @Override
    public float getTemperatureAt(BlockPosAPI<?> pos) {
        return ((Biome)this.biome).getTemperature((BlockPos)pos.getPos());
    }

    protected abstract void getTypes(Set<String> typeSet, @Nullable Object biomeKey);

    @Override
    public ResourceLocationAPI<?> getRegistryName() {
        return new ResourceLocation1_16_5(((Biome)this.biome).getRegistryName());
    }
}
