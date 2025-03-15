package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.block.MaterialColorAPI;
import net.minecraft.world.level.material.MapColor;

public class MaterialColor1_21 extends MaterialColorAPI<MapColor> {
    
    public MaterialColor1_21(MapColor color) {
        super(color);
    }
    
    @Override public int getColor() {
        return this.wrapped.col;
    }
    
    @Override public int getID() {
        return this.wrapped.id;
    }
}