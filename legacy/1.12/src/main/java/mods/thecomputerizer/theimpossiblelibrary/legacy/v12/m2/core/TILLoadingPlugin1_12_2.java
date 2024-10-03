package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;

import static java.lang.Integer.MIN_VALUE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;
import static net.minecraft.launchwrapper.Launch.classLoader;

@MCVersion("1.12.2") @Name(NAME+" Core") @SortingIndex(MIN_VALUE+1)
public class TILLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

    static {
        new TILCore1_12_2();
    }
    
    public static Collection<CoreEntryPoint> getTransformers(String name) {
        return INSTANCE.coreTransforms.getOrDefault(name,Collections.emptySet());
    }
    
    static TILLoadingPlugin1_12_2 INSTANCE;
    
    final Map<String,Collection<CoreEntryPoint>> coreTransforms;

    public TILLoadingPlugin1_12_2() {
        this.coreTransforms = new HashMap<>();
        INSTANCE = this;
    }

    @Override public String[] getASMTransformerClass() {
        TILRef.logInfo("Coremod shenanigans");
        CoreAPI core = CoreAPI.getInstance();
        core.loadCoreModInfo(classLoader);
        core.instantiateCoreMods();
        for(CoreEntryPoint entryPoint : core.getCoreInstances()) {
            for(String className : entryPoint.classTargets()) {
                this.coreTransforms.putIfAbsent(className,new HashSet<>());
                this.coreTransforms.get(className).add(entryPoint);
            }
        }
        return new String[]{"mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.asm.TILClassTransform1_12_2"};
    }

    @Override public String getModContainerClass() {
        return null;
    }

    @Override public @Nullable String getSetupClass() {
        return null;
    }

    @Override public void injectData(Map<String,Object> data) {
        TILRef.logInfo("Beginning injection with coremod data: {}", data);
        CoreAPI.getInstance().writeModContainers(classLoader);
    }

    @Override public String getAccessTransformerClass() {
        return null;
    }
}
