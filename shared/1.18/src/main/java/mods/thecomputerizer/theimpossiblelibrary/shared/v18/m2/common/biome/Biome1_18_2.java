package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.lang.invoke.MethodHandle;
import java.util.Objects;

import static net.minecraft.core.Registry.BIOME_REGISTRY;

public class Biome1_18_2 extends BiomeAPI<Biome> {
    
    private final MethodHandle tempHandle;

    public Biome1_18_2(Object biome) {
        super((Biome)biome);
        this.tempHandle = Objects.nonNull(biome) ?
                ReflectionHelper.findMethodHandle(Biome.class,"getTemperature",BlockPos.class) : null;
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        RegistryAccess registries = ((LevelAccessor)world.unwrap()).registryAccess();
        Registry<Biome> registry = registries.registry(BIOME_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        if(Objects.isNull(this.tempHandle)) return this.wrapped.getBaseTemperature();
        try {
            return (float)this.tempHandle.invoke(this.wrapped,pos.unwrap());
        } catch(Throwable t) {
            TILRef.logError("Failed to get get temperature for biome {} at {}",this.wrapped,pos.getWrapped(),t);
            return this.wrapped.getBaseTemperature();
        }
    }
}