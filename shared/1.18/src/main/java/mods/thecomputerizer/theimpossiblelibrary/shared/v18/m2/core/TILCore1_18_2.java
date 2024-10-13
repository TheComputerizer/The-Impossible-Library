package mods.thecomputerizer.theimpossiblelibrary.shared.v18.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;

import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V18_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public abstract class TILCore1_18_2 extends CoreAPI {
    
    protected TILCore1_18_2(ModLoader loader,boolean client) {
        super(V18_2,loader,client ? DEDICATED_CLIENT : DEDICATED_SERVER);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCore1_18_2.class);
    }
}