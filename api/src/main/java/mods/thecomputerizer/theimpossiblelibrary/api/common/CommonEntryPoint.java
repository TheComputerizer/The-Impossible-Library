package mods.thecomputerizer.theimpossiblelibrary.api.common;

import mods.thecomputerizer.theimpossiblelibrary.api.ReferenceAPI;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;
import java.util.function.Function;

/**
 * Common entrypoint API. This covers events for multiple loaders and versions, so some
 */
public abstract class CommonEntryPoint<TYPES extends CommonAPI> {

    public static CommonEntryPoint<?> INSTANCE;

    private final TYPES types;
    private final Map<String,Function<TYPES,?>> getterShorcuts;
    private final Map<String,Consumer<TYPES>> setterShorcuts;

    protected CommonEntryPoint(TYPES types) {
        this.types = types;
        this.getterShorcuts = new HashMap<>();
        this.setterShorcuts = new HashMap<>();
        if(!ReferenceAPI.CLIENT) INSTANCE = this;
        registerTypeShortcuts();
    }

    public TYPES getTypes() {
        return this.types;
    }

    public void putGetterShortcut(String key, Function<TYPES,?> getter) {
        this.getterShorcuts.put(key,getter);
    }

    public void putSetterShortcut(String key, Consumer<TYPES> setter) {
        this.setterShorcuts.put(key,setter);
    }

    public void registerEventListener(Object listener) {

    }

    protected void registerTypeShortcuts() {

    }
}