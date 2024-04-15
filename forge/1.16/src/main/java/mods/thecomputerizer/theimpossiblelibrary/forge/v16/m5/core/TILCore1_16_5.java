package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.loader.MultiLoaderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import net.minecraftforge.fml.loading.FMLLoader;

import java.io.File;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V16;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.FORGE;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

public class TILCore1_16_5 extends CoreAPI {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private final MultiLoader1_16_5 loader;

    public TILCore1_16_5(File root) {
        super(V16,FORGE,FORGE_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
        this.loader = new MultiLoader1_16_5(this,root);
    }

    @Override
    public MultiLoaderAPI getLoader() {
        return this.loader;
    }

    @Override
    public void initAPI() {
        TILRef.setAPI(this.side.isClient() ? new Client1_16_5() : new Common1_16_5());
    }
}
