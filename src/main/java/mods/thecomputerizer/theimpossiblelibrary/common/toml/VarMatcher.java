package mods.thecomputerizer.theimpossiblelibrary.common.toml;

import mods.thecomputerizer.theimpossiblelibrary.util.file.LogUtil;
import org.apache.logging.log4j.Level;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;

@SuppressWarnings("BooleanMethodIsAlwaysInverted")
public class VarMatcher {

    private final Map<String, Function<Object, Boolean>> conditions;

    public VarMatcher() {
        this.conditions = new HashMap<>();
    }

    public void addCondition(String name, Function<Object, Boolean> matcher) {
        this.conditions.put(name, matcher);
    }

    public boolean matches(String name, Object val) {
        if(this.conditions.isEmpty() || Objects.isNull(val) || !this.conditions.containsKey(name)) return true;
        try {
            return this.conditions.containsKey(name) && this.conditions.get(name).apply(val);
        } catch (Exception e) {
            LogUtil.logInternal(Level.ERROR,"Error in variable whitelist matcher",e);
        }
        return false;
    }
}
