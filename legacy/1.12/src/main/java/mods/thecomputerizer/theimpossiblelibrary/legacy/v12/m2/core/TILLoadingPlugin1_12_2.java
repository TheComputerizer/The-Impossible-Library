package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import net.minecraftforge.fml.relauncher.IFMLLoadingPlugin;

import javax.annotation.Nullable;
import java.util.Map;

import static net.minecraft.launchwrapper.Launch.classLoader;
import static net.minecraft.launchwrapper.Launch.minecraftHome;

public class TILLoadingPlugin1_12_2 implements IFMLLoadingPlugin {

    static {
        new TILCore1_12_2(minecraftHome).loadCoreModInfo(classLoader::addURL);
    }

    public TILLoadingPlugin1_12_2() {
        CoreAPI.INSTANCE.instantiateCoreMods();
    }

    @Override
    public String[] getASMTransformerClass() {
        return new String[0];
    }

    @Override
    public String getModContainerClass() {
        return "";
    }

    @Override
    public @Nullable String getSetupClass() {
        return "";
    }

    @Override
    public void injectData(Map<String,Object> data) {

    }

    @Override
    public String getAccessTransformerClass() {
        return "";
    }
}
