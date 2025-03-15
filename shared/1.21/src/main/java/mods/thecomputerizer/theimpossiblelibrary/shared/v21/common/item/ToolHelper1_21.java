package mods.thecomputerizer.theimpossiblelibrary.shared.v21.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import net.minecraft.world.item.Tier;

import static net.minecraft.world.item.Tiers.*;

public class ToolHelper1_21 implements ToolHelperAPI {
    
    @Override public ToolTier1_21 getTier(String name) {
        Tier tier = WOOD;
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
        return new ToolTier1_21(tier);
    }
}