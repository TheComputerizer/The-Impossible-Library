package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MaterialColor;

public class MaterialColor1_18_2 extends MaterialColorAPI<MaterialColor> {
    
    public MaterialColor1_18_2(MaterialColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.wrapped.col;
    }
    
    @Override public int getID() {
        return this.wrapped.id;
    }
}