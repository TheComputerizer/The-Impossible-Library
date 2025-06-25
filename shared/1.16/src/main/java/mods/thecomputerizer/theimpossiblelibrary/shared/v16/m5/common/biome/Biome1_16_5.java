package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.biome.Biome;

import java.util.Objects;

import static net.minecraft.core.Registry.BIOME_REGISTRY;
import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

@Setter
public class Biome1_16_5 extends BiomeAPI<Biome> {
    
    protected RegistryAccess access;
    
    public Biome1_16_5(Object biome) {
        super((Biome)biome);
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName() {
        if(Objects.isNull(this.registryName)) {
            if(Objects.isNull(this.access))
                this.access = (RegistryAccess)TILRef.getCommonHandles().builtInRegistryAccess();
            this.registryName = getRegistryName(this.access);
        }
        return this.registryName;
    }
    
    @Override public ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world) {
        if(Objects.isNull(this.registryName))
            this.registryName = getRegistryName(((LevelAccessor)world.unwrap()).registryAccess());
        return this.registryName;
    }
    
    private ResourceLocationAPI<?> getRegistryName(RegistryAccess access) {
        Registry<Biome> registry = access.registry(BIOME_REGISTRY).orElse(null);
        return WrapperHelper.wrapResourceLocation(Objects.nonNull(registry) ? registry.getKey(this.wrapped) : null);
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }
    
    @Override public void setRegistryName(ResourceLocationAPI<?> registryName) {
        setLocalRegistryName(registryName);
        if(FORGE) Methods.invoke(this.wrapped,"setRegistryName",(Object)registryName.unwrap());
    }
}