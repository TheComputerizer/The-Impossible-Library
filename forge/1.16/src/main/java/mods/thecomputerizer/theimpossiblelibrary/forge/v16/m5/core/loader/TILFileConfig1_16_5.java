package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionModInfo;
import net.minecraftforge.forgespi.language.IConfigurable;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

public class TILFileConfig1_16_5 implements IConfigurable {
    
    private final Map<String,Object> infoMap;
    private final Map<String,List<TILModConfig1_16_5>> childConfigs;
    
    public TILFileConfig1_16_5(Collection<MultiVersionModInfo> infos) {
        this.infoMap = new HashMap<>();
        this.infoMap.put("modLoader","javafml");
        this.infoMap.put("loaderVersion","[36.2,)");
        this.infoMap.put("license","NYI");
        this.childConfigs = new HashMap<>();
        this.childConfigs.put("mods",new ArrayList<>());
        for(MultiVersionModInfo info : infos)
            this.childConfigs.get("mods").add(new TILModConfig1_16_5(info));
    }
    
    @SuppressWarnings("unchecked") @Override
    public <T> Optional<T> getConfigElement(String ... key) {
        if(Objects.isNull(key) || key.length==0) return Optional.empty();
        T value = (T)this.infoMap.get(key[0]);
        return Objects.nonNull(value) ? Optional.of(value) : Optional.empty();
    }
    
    @Override public List<? extends IConfigurable> getConfigList(String ... key) {
        if(Objects.isNull(key) || key.length==0) return Collections.emptyList();
        return this.childConfigs.getOrDefault(key[0],Collections.emptyList());
    }
}