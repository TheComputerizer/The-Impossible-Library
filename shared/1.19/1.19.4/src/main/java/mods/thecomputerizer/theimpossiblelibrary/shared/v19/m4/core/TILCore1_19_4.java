package mods.thecomputerizer.theimpossiblelibrary.shared.v19.m4.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.shared.v19.core.TILCore1_19;

import java.util.Set;

public abstract class TILCore1_19_4 extends TILCore1_19 {
    
    protected TILCore1_19_4(ModLoader loader, boolean client) {
        super(false,loader,client);
    }
    
    @Override public void addSources(Set<String> sources) {
        super.addSources(sources);
        ClassHelper.addSource(sources,TILCore1_19_4.class);
    }
}