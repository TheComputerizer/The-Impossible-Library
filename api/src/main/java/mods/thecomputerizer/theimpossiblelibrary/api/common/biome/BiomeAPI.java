package mods.thecomputerizer.theimpossiblelibrary.api.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.wrappers.AbstractWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;

import java.util.Objects;
import java.util.Set;
import java.util.StringJoiner;

public abstract class BiomeAPI<B> extends AbstractWrapped<B> implements RegistryEntryAPI<B> {

    protected BiomeAPI(B biome) {
        super(biome);
    }

    @IndirectCallers public boolean canRain() {
        return TILRef.getCommonHandles().canBiomeRain(this.wrapped);
    }
    
    @IndirectCallers public boolean canSnow() {
        return TILRef.getCommonHandles().canBiomeSnow(this.wrapped);
    }
    
    @IndirectCallers public String getName(WorldAPI<?> world) {
        ResourceLocationAPI<?> registryName = getRegistryName(world);
        if(Objects.isNull(registryName)) return null;
        String[] words = registryName.getPath().split("_");
        StringJoiner joiner = new StringJoiner(" ");
        for(String word : words) joiner.add(TextHelper.capitalize(word));
        return joiner.toString();
    }
    
    @IndirectCallers public abstract float getRainfall();
    @IndirectCallers public abstract ResourceLocationAPI<?> getRegistryName(WorldAPI<?> world);
    
    @IndirectCallers
    public Set<String> getTagNames(WorldAPI<?> world) {
        return TILRef.getCommonHandles().biomeTagNames(world,unwrap());
    }
    
    @IndirectCallers public abstract float getTemperatureAt(BlockPosAPI<?> pos);
}