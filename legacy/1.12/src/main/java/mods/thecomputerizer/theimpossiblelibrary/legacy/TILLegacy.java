package mods.thecomputerizer.theimpossiblelibrary.legacy;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.ClientLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.CommonLegacy;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TILLegacy {

    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient,"");
    private static boolean isInitialized = false;

    public static void init() {
        if(!isInitialized) TILRef.setAPI(TILLegacy.LEGACY_REF.isClient() ? new ClientLegacy() : new CommonLegacy());
        isInitialized = true;
    }
}
