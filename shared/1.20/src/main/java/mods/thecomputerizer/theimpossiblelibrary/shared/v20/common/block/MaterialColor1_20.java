package mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MapColor;

public class MaterialColor1_20 extends MaterialColorAPI<MapColor> {
    
    public MaterialColor1_20(MapColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.wrapped.col;
    }
    
    @Override public int getID() {
        return this.wrapped.id;
    }
}