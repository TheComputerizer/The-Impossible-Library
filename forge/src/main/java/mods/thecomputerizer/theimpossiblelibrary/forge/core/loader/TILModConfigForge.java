package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import net.minecraftforge.forgespi.language.IConfigurable;

import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;

import static org.burningwave.core.assembler.StaticComponentContainer.Methods;

public class TILModConfigForge implements IConfigurable {
    
    static {
        ClassHelper.checkBurningWaveInit();
    }
    
    private final Map<String,Object> infoMap;
    
    public TILModConfigForge(Object info) {
        this.infoMap = new HashMap<>();
        this.infoMap.put("description",genericMethod(info,"getDescription"));
        this.infoMap.put("displayName",genericMethod(info,"getName"));
        this.infoMap.put("modId",genericMethod(info,"getModID"));
        this.infoMap.put("version",genericMethod(info,"getVersion"));
        this.infoMap.put("logoFile","logo.png");
    }
    
    String genericMethod(Object generic, String name) {
        return Methods.invokeDirect(generic,name);
    }
    
    @SuppressWarnings("unchecked") @Override public <T> Optional<T> getConfigElement(String ... key) {
        if(Objects.isNull(key) || key.length==0) return Optional.empty();
        T value = (T)this.infoMap.get(key[0]);
        return Objects.nonNull(value) ? Optional.of(value) : Optional.empty();
    }
    
    @Override public List<? extends IConfigurable> getConfigList(String... key) {
        return Collections.emptyList();
    }
}