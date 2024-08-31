package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.biome;

import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.biome.Biome1_16_5;
import net.minecraft.world.level.biome.Biome;
import org.jetbrains.annotations.Nullable;

import java.util.Set;

public class BiomeFabric1_16_5 extends Biome1_16_5<Biome> {
    
    public BiomeFabric1_16_5(Biome biome) {
        super(biome);
    }
    
    @Override protected void getTypes(Set<String> typeSet, @Nullable Object biomeKey) {
        typeSet.add(this.biome.getBiomeCategory().getName());
    }
}
