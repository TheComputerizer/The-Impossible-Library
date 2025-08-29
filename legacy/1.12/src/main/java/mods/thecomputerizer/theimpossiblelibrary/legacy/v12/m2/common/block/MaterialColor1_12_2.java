package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.block.material.MapColor;

public class MaterialColor1_12_2 extends MaterialColorAPI<MapColor> {
    
    public MaterialColor1_12_2(Object color) {
        super(color);
    }
    
    @Override public int getColor() {
        return getIfNotNullOrDefault(w -> w.colorValue,0);
    }
    
    @Override public int getID() {
        return getIfNotNullOrDefault(w -> w.colorIndex,0);
    }
}