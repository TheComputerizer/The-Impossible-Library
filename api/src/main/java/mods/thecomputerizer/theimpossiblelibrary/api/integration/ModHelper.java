package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import javax.annotation.Nullable;
import java.util.Objects;

public class ModHelper {
    
    @IndirectCallers
    public static @Nullable BetterWeatherAPI betterWeather() {
        return (BetterWeatherAPI)getModAPI(BetterWeatherAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable BloodmoonAPI bloodmoon() {
        return (BloodmoonAPI)getModAPI(BloodmoonAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable ChampionsAPI champions() {
        return (ChampionsAPI)getModAPI(ChampionsAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable DynamicSurroundingsAPI dynamicSurroundings() {
        return (DynamicSurroundingsAPI)getModAPI(DynamicSurroundingsAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable EnhancedCelestialsAPI enhancedCelestials() {
        return (EnhancedCelestialsAPI)getModAPI(EnhancedCelestialsAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable GameStagesAPI gameStages() {
        return (GameStagesAPI)getModAPI(GameStagesAPI.MODID);
    }

    public static @Nullable ModAPI getModAPI(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) ? api.getMod(modid) : null;
    }

    public static @Nullable ModHelperAPI getModHelperAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getModHelper);
    }

    public static @Nullable String getModName(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) ? api.getModName(modid) : null;
    }
    
    @IndirectCallers
    public static @Nullable InfernalMobsAPI infernalMobs() {
        return (InfernalMobsAPI)getModAPI(InfernalMobsAPI.MODID);
    }
    
    @IndirectCallers
    public static boolean isModLoaded(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) && api.isModLoaded(modid);
    }
    
    @IndirectCallers
    public static @Nullable NyxAPI nyx() {
        return (NyxAPI)getModAPI(NyxAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable SereneSeasonsAPI sereneSeasons() {
        return (SereneSeasonsAPI)getModAPI(SereneSeasonsAPI.MODID);
    }
    
    @IndirectCallers
    public static @Nullable Weather2API weather2() {
        return (Weather2API)getModAPI(Weather2API.MODID);
    }
}