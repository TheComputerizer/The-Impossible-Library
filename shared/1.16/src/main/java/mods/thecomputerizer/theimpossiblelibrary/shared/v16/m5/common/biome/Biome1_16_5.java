package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.world.IWorld;

import javax.annotation.Nullable;
import java.util.HashSet;
import java.util.Set;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;

public abstract class Biome1_16_5<B> extends BiomeAPI<B> {

    public Biome1_16_5(B biome) {
        super(biome);
    }

    @SuppressWarnings("unchecked")
    public <K> @Nullable K getRegistryKey(DynamicRegistries registries) {
        return (K)registries.registryOrThrow(BIOME_REGISTRY).getResourceKey(unwrap()).orElse(null);
    }

    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        Set<String> tags = new HashSet<>();
        getTypes(tags,getRegistryKey(((IWorld)world.getWrapped()).registryAccess()));
        return tags;
    }

    protected abstract void getTypes(Set<String> typeSet, @Nullable Object biomeKey);
}