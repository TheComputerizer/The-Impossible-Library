package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.world;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.structure.StructureAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.WrapperHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.biome.Biome1_19;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.world.World1_19;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceKey;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.levelgen.structure.Structure;

import java.util.Map.Entry;
import java.util.Optional;

import static net.minecraft.core.registries.Registries.STRUCTURE;

public class World1_19_4 extends World1_19 {
    
    public World1_19_4(Object world) {
        super(world);
    }
    
    @Override public BiomeAPI<?> getBiomeAt(BlockPosAPI<?> pos) {
        BiomeAPI<Biome> biome = WrapperHelper.wrapBiome(this.wrapped.getBiome(pos.unwrap()).value());
        ((Biome1_19)biome).setAccess(this.wrapped.registryAccess());
        return biome;
    }
    
    @Override public StructureAPI<?> getStructureAt(BlockPosAPI<?> api) {
        if(this.wrapped instanceof ServerLevel) {
            StructureManager manager = ((ServerLevel)this.wrapped).structureManager();
            BlockPos pos = api.unwrap();
            RegistryAccess access = this.wrapped.registryAccess();
            Optional<Registry<Structure>> optional = access.registry(STRUCTURE);
            if(optional.isEmpty()) return null;
            for(Entry<ResourceKey<Structure>,Structure> entry : optional.get().entrySet()) {
                Structure structure = entry.getValue();
                if(manager.getStructureAt(pos,structure).isValid())
                    return WrapperHelper.wrapStructure(structure);
            }
        }
        return null;
    }
}
