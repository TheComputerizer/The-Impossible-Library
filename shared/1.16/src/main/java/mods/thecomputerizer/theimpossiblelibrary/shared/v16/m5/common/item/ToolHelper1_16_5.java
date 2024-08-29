package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import net.minecraft.item.ItemTier;

import static net.minecraft.item.ItemTier.*;

public class ToolHelper1_16_5 implements ToolHelperAPI {
    
    @Override public ToolTier1_16_5 getTier(String name) {
        ItemTier tier = WOOD;
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
            case "NETHERITE": {
                tier = NETHERITE;
                break;
            }
        }
        return new ToolTier1_16_5(tier);
    }
}
