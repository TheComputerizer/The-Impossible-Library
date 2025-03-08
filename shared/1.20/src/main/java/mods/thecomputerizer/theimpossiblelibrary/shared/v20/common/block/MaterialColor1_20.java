package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MaterialColor;

public class MaterialColor1_20 extends MaterialColorAPI<MaterialColor> {
    
    public MaterialColor1_20(MaterialColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.wrapped.col;
    }
    
    @Override public int getID() {
        return this.wrapped.id;
    }
}