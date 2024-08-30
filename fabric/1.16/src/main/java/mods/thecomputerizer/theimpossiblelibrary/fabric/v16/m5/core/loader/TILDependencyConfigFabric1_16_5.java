package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.core.loader;

import net.minecraftforge.forgespi.language.IConfigurable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TILDependencyConfigFabric1_16_5 implements IConfigurable {
    
    private final Map<String,Object> infoMap;
    
    public TILDependencyConfigFabric1_16_5(String modid, String version, String order, String side, boolean mandatory) {
        this.infoMap = new HashMap<>();
        this.infoMap.put("mandatory",mandatory);
        this.infoMap.put("modId",modid);
        this.infoMap.put("ordering",order);
        this.infoMap.put("side",side);
        this.infoMap.put("versionRange",version);
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