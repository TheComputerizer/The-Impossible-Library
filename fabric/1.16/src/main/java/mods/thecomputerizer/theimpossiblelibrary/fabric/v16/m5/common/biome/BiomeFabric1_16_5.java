package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class BiomeFabric1_16_5 extends Biome1_16_5 {
    
    public BiomeFabric1_16_5(Biome biome) {
        super(biome);
    }
    
    @Override protected void getTypes(Set<String> typeSet, @Nullable RegistryKey<Biome> biomeKey) {
        Set<Type> dictTypes = Objects.nonNull(biomeKey) ? BiomeDictionary.getTypes(biomeKey) : Collections.emptySet();
        for(Type type : dictTypes) typeSet.add(type.getName());
    }
}
