package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiVersionLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.TILClientEntry1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.TILCommonEntry1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.File;
import java.util.Collection;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_16_5 extends CoreAPI {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiVersionLoader1_16_5 loader;

    public TILCore1_16_5(Collection<File> mods) {
        super(V16,FORGE,FORGE_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiVersionLoader1_16_5(this,mods);
    }

    @Override
    public MultiVersionLoaderAPI getLoader() {
        return this.loader;
    }

    @Override
    public CommonEntryPoint getClientVersionHandler() {
        return this.side.isClient() ? new TILClientEntry1_16_5() : null;
    }

    @Override
    public CommonEntryPoint getCommonVersionHandler() {
        return new TILCommonEntry1_16_5();
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new Client1_16_5() : new Common1_16_5());
    }
}
