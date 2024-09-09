package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.GameStagesAPI;
import net.darkhax.gamestages.GameStageHelper;
import net.darkhax.gamestages.data.IStageData;

import java.util.Collection;
import java.util.Collections;
import java.util.Objects;

public class GameStagesForge1_16_5 extends GameStagesAPI {

    @Override public Collection<String> getStages(PlayerAPI<?,?> player) {
        IStageData data = GameStageHelper.getPlayerData(player.unwrap());
        return Objects.nonNull(data) ? data.getStages() : Collections.emptyList();
    }
}