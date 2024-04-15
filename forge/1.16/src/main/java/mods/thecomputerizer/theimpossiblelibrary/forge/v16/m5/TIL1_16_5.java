package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.client.Client1_16_5;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.Common1_16_5;

import javax.annotation.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.core.TILCore1_16_5.FORGE_REF;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TIL1_16_5 {

    private static boolean isInitialized = false;

    public static void init(@Nullable CoreAPI core) {
        if(!isInitialized) TILRef.setAPI(FORGE_REF.isClient() ? new Client1_16_5(core) : new Common1_16_5(core));
        isInitialized = true;
    }
}
