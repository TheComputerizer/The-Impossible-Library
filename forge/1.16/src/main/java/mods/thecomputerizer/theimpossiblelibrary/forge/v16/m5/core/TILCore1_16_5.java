package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_16_5 extends CoreAPI {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiVersionLoader1_16_5 loader;

    public TILCore1_16_5() {
        super(V16,FORGE,FORGE_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_16_5(this);
    }

    @Override
    public CommonEntryPoint getClientVersionHandler() {
        return null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return null;
    }

    @Override
    public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new Client1_16_5() : new Common1_16_5());
    }

    @Override
    public void injectWrittenMod(Class<?> containerClass, String modid) {

    }
    
    @Override
    public void modConstructed(Package pkg, String modid, String name, String entryType) {
        TILRef.logInfo("Skipping extra entrypoint for `{}` in `{}`",modid,pkg);
    }

    @Override
    protected boolean modConstructed(String modid, Class<?> clazz) {
        return true;
    }
}
