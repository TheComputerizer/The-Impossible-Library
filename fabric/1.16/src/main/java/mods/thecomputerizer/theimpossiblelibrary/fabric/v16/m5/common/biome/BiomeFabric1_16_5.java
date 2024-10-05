package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

import static net.minecraft.world.level.biome.Biome.Precipitation.RAIN;
import static net.minecraft.world.level.biome.Biome.Precipitation.SNOW;

public class BiomeFabric1_16_5 extends Biome1_16_5<Biome> {
    
    public BiomeFabric1_16_5(Object biome) {
        super((Biome)biome);
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
        typeSet.add(this.wrapped.getBiomeCategory().getName());
    }
}