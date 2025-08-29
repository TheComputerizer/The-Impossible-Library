package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MapColor;

public class MaterialColor1_20 extends MaterialColorAPI<MapColor> {
    
    public MaterialColor1_20(Object color) {
        super(color);
    }
    
    @Override public int getColor() {
        return getIfNotNullOrDefault(w -> w.col,0);
    }
    
    @Override public int getID() {
        return getIfNotNullOrDefault(w -> w.id,0);
    }
}