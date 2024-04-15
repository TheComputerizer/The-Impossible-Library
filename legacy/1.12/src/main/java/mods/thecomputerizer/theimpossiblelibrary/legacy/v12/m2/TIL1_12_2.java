package mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.client.Client1_12_2;
import mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.common.Common1_12_2;

import javax.annotation.Nullable;

import static mods.thecomputerizer.theimpossiblelibrary.legacy.v12.m2.core.TILCore1_12_2.LEGACY_REF;

/**
 * The init method needs to be called before the API can be utilized
 */
public class TIL1_12_2 {

    private static boolean isInitialized = false;

    public static void init(@Nullable CoreAPI core) {
        if(!isInitialized) TILRef.setAPI(LEGACY_REF.isClient() ? new Client1_12_2(core) : new Common1_12_2(core));
        isInitialized = true;
    }
}
