package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.biome.Biome1_19;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;
import java.util.Set;

import static net.minecraft.core.registries.BuiltInRegistries.REGISTRY;
import static net.minecraft.core.registries.Registries.BIOME;

public class Biome1_19_4 extends Biome1_19 {

    public Biome1_19_4(Object biome) {
        super(biome);
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        return getRegistryName(RegistryAccess.fromRegistryOfRegistries(REGISTRY));
    }
    
    @Override protected ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Biome> registry = access.registry(BIOME).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public Set<String> getTagNames(WorldAPI<?> world) {
        LevelAccessor access = world.unwrap();
        return getTagNames(access.registryAccess().registry(BIOME).orElse(null));
    }
}