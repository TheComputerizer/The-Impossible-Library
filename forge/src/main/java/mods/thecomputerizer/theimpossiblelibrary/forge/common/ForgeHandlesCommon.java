package mods.thecomputerizer.theimpossiblelibrary.forge.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import net.minecraft.util.RegistryKey;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Set;
import java.util.stream.Collectors;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;

public class ForgeHandlesCommon implements SharedHandlesCommon {
    
    @Override public Set<String> biomeTagNames(WorldAPI<?> world, Object biome) {
        RegistryKey<Biome> key = getBiomeKey(((IWorld)world.getWrapped()).registryAccess(),(Biome)biome);
        return BiomeDictionary.getTypes(key).stream().map(Type::getName).collect(Collectors.toSet());
    }
    
    public @Nullable RegistryKey<Biome> getBiomeKey(DynamicRegistries registries, Biome biome) {
        return getRegistryKey(registries,BIOME_REGISTRY,biome);
    }
    
    public <R> @Nullable RegistryKey<R> getRegistryKey(DynamicRegistries registries, RegistryKey<Registry<R>> type, R value) {
        return registries.registryOrThrow(type).getResourceKey(value).orElse(null);
    }
}