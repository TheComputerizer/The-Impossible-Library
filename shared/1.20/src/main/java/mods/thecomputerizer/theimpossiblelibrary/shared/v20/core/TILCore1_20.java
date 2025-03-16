package mods.thecomputerizer.theimpossiblelibrary.shared.v20.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public abstract class TILCore1_20 extends CoreAPI {
    
    protected TILCore1_20(GameVersion version, ModLoader loader, boolean client) {
        super(version,loader,client ? DEDICATED_CLIENT : DEDICATED_SERVER);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCore1_20.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader, url);
        TILRef.logError("Directly adding a URL is not supported in this version! Not adding {}", url);
        return false;
    }
}