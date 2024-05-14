package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import net.minecraft.item.Item.ToolMaterial;

import static net.minecraft.item.Item.ToolMaterial.*;

public class ToolHelper1_12_2 implements ToolHelperAPI {
    
    @Override public ToolTier1_12_2 getTier(String name) {
        ToolMaterial tier = WOOD;
        switch(name.toUpperCase()) {
            case "STONE": {
                tier = STONE;
                break;
            }
            case "IRON": {
                tier = IRON;
                break;
            }
            case "DIAMOND": {
                tier = DIAMOND;
                break;
            }
            case "GOLD": {
                tier = GOLD;
                break;
            }
        }
        return new ToolTier1_12_2(tier);
    }
}
