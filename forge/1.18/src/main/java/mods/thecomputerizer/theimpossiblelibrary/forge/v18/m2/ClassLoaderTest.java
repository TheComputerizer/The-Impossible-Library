package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2;

import cpw.mods.cl.ModuleClassLoader;

import java.lang.module.Configuration;
import java.util.List;

public class ClassLoaderTest extends ModuleClassLoader {
    
    public ClassLoaderTest(
            String name, Configuration configuration, List<ModuleLayer> parentLayers) {
        super(name, configuration, parentLayers);
    }
}
