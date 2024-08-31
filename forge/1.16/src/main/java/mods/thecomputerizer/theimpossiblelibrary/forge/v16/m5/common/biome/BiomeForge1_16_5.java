package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import net.minecraft.util.RegistryKey;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

public class BiomeForge1_16_5 extends Biome1_16_5<Biome> {
    
    public BiomeForge1_16_5(Biome biome) {
        super(biome);
    }
    
    @SuppressWarnings("unchecked")
    @Override protected void getTypes(Set<String> typeSet, @Nullable Object biomeKey) {
        Set<Type> dictTypes = Objects.nonNull(biomeKey) ?
                BiomeDictionary.getTypes((RegistryKey<Biome>)biomeKey) : Collections.emptySet();
        for(Type type : dictTypes) typeSet.add(type.getName());
    }
}
