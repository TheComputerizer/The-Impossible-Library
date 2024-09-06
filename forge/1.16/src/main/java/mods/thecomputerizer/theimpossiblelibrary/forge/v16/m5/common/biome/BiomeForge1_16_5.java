package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.util.BasicWrapped;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;

import javax.annotation.Nullable;
import java.util.Collections;
import java.util.Objects;
import java.util.Set;

import static net.minecraft.world.biome.Biome.RainType.RAIN;
import static net.minecraft.world.biome.Biome.RainType.SNOW;

public class BiomeForge1_16_5 extends Biome1_16_5<Biome> {
    
    public BiomeForge1_16_5(Biome biome) {
        super(biome);
    }
    
    @Override public boolean canRain() {
        return this.wrapped.getPrecipitation()==RAIN;
    }
    
    @Override public boolean canSnow() {
        return this.wrapped.getPrecipitation()==SNOW;
    }
    
    @Override public float getRainfall() {
        return this.wrapped.getDownfall();
    }
    
    @Override public float getTemperatureAt(BlockPosAPI<?> pos) {
        return this.wrapped.getTemperature(pos.unwrap());
    }
    
    @Override protected void getTypes(Set<String> typeSet, @Nullable Object biomeKey) {
        Set<Type> dictTypes = Objects.nonNull(biomeKey) ?
                BiomeDictionary.getTypes(BasicWrapped.cast(biomeKey)) : Collections.emptySet();
        for(Type type : dictTypes) typeSet.add(type.getName());
    }
}