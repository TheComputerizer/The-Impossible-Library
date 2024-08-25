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

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.MODID;

public class TILFileConfig1_16_5 implements IConfigurable {
    
    private final Map<String,Object> infoMap;
    private final Map<String,List<IConfigurable>> childConfigs;
    private final Map<String,List<IConfigurable>> dependencies;
    
    public TILFileConfig1_16_5(Collection<MultiVersionModInfo> infos) {
        this.infoMap = new HashMap<>();
        this.infoMap.put("modLoader","multiversionprovider");
        this.infoMap.put("loaderVersion","[0.4.0,)");
        this.infoMap.put("license","NYI");
        this.childConfigs = new HashMap<>();
        this.dependencies = new HashMap<>();
        this.childConfigs.put("mods",new ArrayList<>());
        for(MultiVersionModInfo info : infos) {
            this.childConfigs.get("mods").add(new TILModConfig1_16_5(info));
            if(!info.getModID().equals(MODID)) {
                String modid = info.getModID();
                this.dependencies.put(modid,new ArrayList<>());
                this.dependencies.get(modid).add(new TILDependencyConfig1_16_5(
                        MODID,"[0.4.0,)","AFTER","BOTH",true));
            }
        }
    }
    
    @SuppressWarnings("unchecked") @Override
    public <T> Optional<T> getConfigElement(String ... keys) {
        if(Objects.isNull(keys) || keys.length==0) return Optional.empty();
        String key = keys[0];
        T value = (T)this.infoMap.get(key);
        return Objects.nonNull(value) ? Optional.of(value) : Optional.empty();
    }
    
    @Override public List<? extends IConfigurable> getConfigList(String ... keys) {
        if(Objects.isNull(keys) || keys.length==0) return Collections.emptyList();
        String key = keys[0];
        return key.equals("dependencies") ? this.dependencies.getOrDefault(keys[1],Collections.emptyList()) :
                this.childConfigs.getOrDefault(key,Collections.emptyList());
    }
}