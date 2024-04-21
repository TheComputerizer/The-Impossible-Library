package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import net.minecraft.launchwrapper.Launch;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.MCVersion;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.Name;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin.SortingIndex;

import javax.annotation.Nullable;
import java.net.URLClassLoader;
import java.util.Map;

@MCVersion("1.12.2")
@Name(TILRef.NAME+" Core")
@SortingIndex(Integer.MIN_VALUE+1) // Allow the injection sorter to be run first
public class TILLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

    static {
        new TILCore1_12_2();
    }

    public TILLoadingPlugin1_12_2() {
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return null;
    }

    @Override
    public @Nullable String getSetupClass() {
        return null;
    }

    @Override
    public void injectData(Map<String,Object> data) {
        TILRef.logInfo("Beginnig core injection with `{}`",data);
        URLClassLoader loader = Launch.classLoader;
        CoreAPI core = CoreAPI.INSTANCE;
        core.loadCoreModInfo(loader);
        core.instantiateCoreMods();
        core.writeModContainers(loader);
    }

    @Override
    public String getAccessTransformerClass() {
        return null;
    }
}
