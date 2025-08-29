package mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MaterialColor;

public class MaterialColor1_19 extends MaterialColorAPI<MaterialColor> {
    
    public MaterialColor1_19(Object color) {
        super(color);
    }
    
    @Override public int getColor() {
        return getIfNotNullOrDefault(w -> w.col,0);
    }
    
    @Override public int getID() {
        return getIfNotNullOrDefault(w -> w.id,0);
    }
}