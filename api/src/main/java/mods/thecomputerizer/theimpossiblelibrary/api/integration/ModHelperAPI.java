package mods.thecomputerizer.theimpossiblelibrary.api.integration;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side;
import org.apache.commons.lang3.StringUtils;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class ModHelperAPI {

    protected final GameVersion version;
    protected final ModLoader loader;
    protected final Side side;
    @Getter protected final Map<String,ModAPI> supportedMods;

    protected ModHelperAPI(GameVersion version, ModLoader loader, Side side) {
        this.version = version;
        this.loader = loader;
        this.side = side;
        this.supportedMods = addSupportedMods(new HashMap<>());
    }

    protected void addMod(Map<String,ModAPI> map, @Nullable ModAPI mod) {
        if(Objects.nonNull(mod) && mod.isCompatible(this.loader,this.side,this.version)) {
            String id = mod.getID();
            if(StringUtils.isNotBlank(id)) map.put(id,mod);
        }
    }

    protected abstract Map<String,ModAPI> addSupportedMods(Map<String,ModAPI> map);

    public @Nullable ModAPI getMod(String modid) {
        return isModLoaded(modid) ? this.supportedMods.get(modid) : null;
    }

    public String getModName(String modid) {
        ModAPI mod = getMod(modid);
        return Objects.nonNull(mod) ? mod.getName() : modid;
    }

    public abstract boolean isModLoaded(String modid);
}