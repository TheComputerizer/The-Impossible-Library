package mods.thecomputerizer.theimpossiblelibrary.forge.core.loader;

import mods.thecomputerizer.theimpossiblelibrary.api.core.ClassHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.forgespi.language.ModFileScanData;

import java.util.HashMap;
import java.util.Map;

public class TILBetterModScan extends ModFileScanData {
    
    private final Map<String,byte[]> writtenClasses;
    
    public TILBetterModScan() {
        super();
        this.writtenClasses = new HashMap<>();
    }
    
    public void addWrittenClass(String classpath, byte[] bytecode) {
        this.writtenClasses.put(classpath,bytecode);
    }
    
    public void defineClass(String classpath, ClassLoader loader) {
        if(this.writtenClasses.containsKey(classpath)){
            ClassHelper.defineClass(loader,classpath,this.writtenClasses.get(classpath));
            return;
        }
        TILRef.logFatal("Unknown class definition for {}!",classpath);
    }
}
