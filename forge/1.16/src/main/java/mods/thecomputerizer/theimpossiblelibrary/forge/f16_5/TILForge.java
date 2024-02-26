package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.client.ClientForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.CommonForge;
import net.minecraftforge.fml.loading.FMLLoader;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TILForge {

    public static final Reference FORGE_REF = TILRef.instance(FMLLoader.getDist()::isClient,"");
    private static boolean isInitialized = false;

    public static void init() {
        if(!isInitialized) TILRef.setAPI(TILForge.FORGE_REF.isClient() ? new ClientForge() : new CommonForge());
        isInitialized = true;
    }
}
