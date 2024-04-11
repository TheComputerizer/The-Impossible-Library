package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TIL1_12_2 {

    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient,"");
    private static boolean isInitialized = false;

    public static void init() {
        if(!isInitialized) TILRef.setAPI(TIL1_12_2.LEGACY_REF.isClient() ? new Client1_12_2() : new Common1_12_2());
        isInitialized = true;
    }
}
