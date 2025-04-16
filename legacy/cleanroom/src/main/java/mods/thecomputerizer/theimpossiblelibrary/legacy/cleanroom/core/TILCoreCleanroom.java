package mods.thecomputerizer.theimpossiblelibrary.legacy.cleanroom.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.legacy.core.TILCoreLegacy;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.V12_2;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader.LEGACY;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_CLIENT;
import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.Side.DEDICATED_SERVER;

/**
 * May not be needed as a separate core class
 */
public abstract class TILCoreCleanroom extends CoreAPI implements TILCoreLegacy {
    
    public static final Reference LEGACY_REF = TILRef.instance(FMLLaunchHandler.side()::isClient, "");
    
    public TILCoreCleanroom() {
        super(V12_2,LEGACY,LEGACY_REF.isClient() ? DEDICATED_CLIENT : DEDICATED_SERVER);
    }
}
