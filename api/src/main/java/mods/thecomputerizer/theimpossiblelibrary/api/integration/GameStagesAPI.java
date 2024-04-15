package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;

import java.util.Arrays;
import java.util.Collection;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;

public abstract class GameStagesAPI implements ModAPI {

    public static final String MODID = "gamestages";
    public static final String NAME = "Game Stages";

    protected GameStagesAPI() {}

    @Override
    public String getID() {
        return MODID;
    }

    @Override
    public String getName() {
        return NAME;
    }

    public abstract Collection<String> getStages(PlayerAPI<?,?> player);

    public boolean hasStage(PlayerAPI<?,?> player, String stage) {
        return getStages(player).contains(stage);
    }

    public boolean hasAllStages(PlayerAPI<?,?> player, String ... stages) {
        return hasAllStages(player,Arrays.asList(stages));
    }

    public boolean hasAllStages(PlayerAPI<?,?> player, Iterable<String> stages) {
        Collection<String> playerStages = getStages(player);
        for(String stage : stages)
            if(!playerStages.contains(stage)) return false;
        return true;
    }

    public boolean hasAnyStage(PlayerAPI<?,?> player, String ... stages) {
        return hasAnyStage(player,Arrays.asList(stages));
    }

    public boolean hasAnyStage(PlayerAPI<?,?> player, Iterable<String> stages) {
        Collection<String> playerStages = getStages(player);
        for(String stage : stages)
            if(playerStages.contains(stage)) return true;
        return false;
    }

    @Override
    public boolean isCompatible(ModLoader loader, Side side, GameVersion version) {
        switch(version) {
            case V12: return loader==LEGACY;
            case V16:
            case V18:
            case V19: return loader==FORGE;
            default: return false;
        }
    }

    public boolean missingStage(PlayerAPI<?,?> player, String stage) {
        return !hasStage(player,stage);
    }

    public boolean missingAllStages(PlayerAPI<?,?> player, String ... stages) {
        return missingAllStages(player,Arrays.asList(stages));
    }

    public boolean missingAllStages(PlayerAPI<?,?> player, Iterable<String> stages) {
        return !hasAnyStage(player,stages);
    }

    public boolean missingAnyStage(PlayerAPI<?,?> player, String ... stages) {
        return missingAnyStage(player,Arrays.asList(stages));
    }

    public boolean missingAnyStage(PlayerAPI<?,?> player, Iterable<String> stages) {
        return !hasAllStages(player,stages);
    }
}