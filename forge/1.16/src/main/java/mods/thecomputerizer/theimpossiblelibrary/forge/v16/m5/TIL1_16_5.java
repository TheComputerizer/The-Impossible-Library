package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.TILCore1_16_5;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TIL1_16_5 {

    private static boolean isInitialized = false;

    public static void init() {
        if(!isInitialized) TILRef.setAPI(TILCore1_16_5.FORGE_REF.isClient() ? new Client1_16_5() : new Common1_16_5());
        isInitialized = true;
    }
}
