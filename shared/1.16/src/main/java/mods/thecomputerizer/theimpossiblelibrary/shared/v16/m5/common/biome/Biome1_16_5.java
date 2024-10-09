package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.util.registry.DynamicRegistries;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.IWorld;
import net.minecraft.world.biome.Biome;

import java.util.Objects;

import static net.minecraft.util.registry.Registry.BIOME_REGISTRY;

public class Biome1_16_5 extends BiomeAPI<Biome> {

    public Biome1_16_5(Object biome) {
        super((Biome)biome);
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        DynamicRegistries registries = ((IWorld)world.unwrap()).registryAccess();
        Registry<Biome> registry = registries.registry(BIOME_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }
}