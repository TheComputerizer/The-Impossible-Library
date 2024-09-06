package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.block.material.MaterialColor;

public class MaterialColor1_16_5 extends MaterialColorAPI<MaterialColor> {
    
    public MaterialColor1_16_5(MaterialColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.wrapped.col;
    }
    
    @Override public int getID() {
        return this.wrapped.id;
    }
}