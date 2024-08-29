package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.IConfigurable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TILModConfigForge1_16_5 implements IConfigurable {
    
    private final Map<String,Object> infoMap;
    
    public TILModConfigForge1_16_5(MultiVersionModInfo info) {
        this.infoMap = new HashMap<>();
        this.infoMap.put("description",info.getDescription());
        this.infoMap.put("displayName",info.getName());
        this.infoMap.put("modId",info.getModID());
        this.infoMap.put("version",info.getVersion());
        this.infoMap.put("logoFile","logo.png");
    }
    
    @SuppressWarnings("unchecked") @Override
    public <T> Optional<T> getConfigElement(String ... key) {
        if(Objects.isNull(key) || key.length==0) return Optional.empty();
        T value = (T)this.infoMap.get(key[0]);
        return Objects.nonNull(value) ? Optional.of(value) : Optional.empty();
    }
    
    @Override public List<? extends IConfigurable> getConfigList(String... key) {
        return Collections.emptyList();
    }
}