package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import javax.annotation.Nullable;
import java.util.Objects;

public class ModHelper {

    public static @Nullable ModAPI getModAPI(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) ? api.getMod(modid) : null;
    }

    public static @Nullable ModHelperAPI getModHelperAPI() {
        return TILRef.getCommonSubAPI("ModHelperAPI",CommonAPI::getModHelper);
    }

    public static @Nullable String getModName(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) ? api.getModName(modid) : null;
    }

    public static boolean isModLoaded(String modid) {
        ModHelperAPI api = getModHelperAPI();
        return Objects.nonNull(api) && api.isModLoaded(modid);
    }
}
