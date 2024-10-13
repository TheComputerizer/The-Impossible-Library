package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.common.item;

import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ToolHelperAPI;
import net.minecraft.world.item.Tier;

import static net.minecraft.world.item.Tiers.*;

public class ToolHelper1_18_2 implements ToolHelperAPI {
    
    @Override public ToolTier1_18_2 getTier(String name) {
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
        return new ToolTier1_18_2(tier);
    }
}