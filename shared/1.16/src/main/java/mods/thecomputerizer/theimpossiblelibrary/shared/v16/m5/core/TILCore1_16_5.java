package mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;

import java.util.Set;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16_5;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public abstract class TILCore1_16_5 extends CoreAPI {
    
    protected TILCore1_16_5(ModLoader loader,boolean client) {
        super(V16_5,loader,client ? DEDICATED_CLIENT : DEDICATED_SERVER);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCore1_16_5.class);
    }
}