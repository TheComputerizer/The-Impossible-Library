package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.block.material.MapColor;

public class MaterialColor1_12_2 extends MaterialColorAPI<MapColor> {
    
    public MaterialColor1_12_2(MapColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.materialColor.colorValue;
    }
    
    @Override public int getID() {
        return this.materialColor.colorIndex;
    }
}
