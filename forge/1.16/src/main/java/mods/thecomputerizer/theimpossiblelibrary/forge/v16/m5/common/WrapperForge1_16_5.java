package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.biome.BiomeAPI;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.biome.BiomeForge1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.Wrapper1_16_5;
import net.minecraft.world.biome.Biome;

import javax.annotation.Nullable;
import java.util.Objects;

public class WrapperForge1_16_5 extends Wrapper1_16_5 {
    
    @SuppressWarnings("unchecked")
    @Override public @Nullable <B> BiomeAPI<B> wrapBiome(@Nullable B biome) {
        return Objects.nonNull(biome) ? (BiomeAPI<B>)new BiomeForge1_16_5((Biome)biome) : null;
    }
}
