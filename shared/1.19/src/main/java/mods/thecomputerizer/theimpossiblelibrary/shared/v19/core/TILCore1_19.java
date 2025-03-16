package mods.thecomputerizer.theimpossiblelibrary.shared.v19.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.common.TILCommonEntryPoint1_19;

import java.net.URL;
import java.net.URLClassLoader;
import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V19_4;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public abstract class TILCore1_19 extends CoreAPI {
    
    protected TILCore1_19(boolean two, ModLoader loader, boolean client) {
        super(two ? V19_2 : V19_4,loader,client ? DEDICATED_CLIENT : DEDICATED_SERVER);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCore1_19.class);
    }
    
    @Override public boolean addURLToClassLoader(ClassLoader loader, URL url) {
        if(loader instanceof URLClassLoader) return ClassHelper.loadURL((URLClassLoader)loader, url);
        TILRef.logError("Directly adding a URL is not supported in this version! Not adding {}", url);
        return false;
    }
    
    @Override public CommonEntryPoint getCommonVersionHandler() {
        return TILCommonEntryPoint1_19.getInstance();
    }
}