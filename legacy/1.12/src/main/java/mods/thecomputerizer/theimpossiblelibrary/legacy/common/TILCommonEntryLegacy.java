package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEntryPoint;
import mods.thecomputerizer.theimpossiblelibrary.legacy.TILLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.client.TILClientEntryLegacy;
import net.minecraftforge.fml.common.Mod;

@Mod(modid = TILRef.MODID,name = TILRef.NAME,version = TILRef.VERSION)
public class TILCommonEntryLegacy extends CommonEntryPoint {

    public TILCommonEntryLegacy() {
        TILLegacy.init();
        if(TILLegacy.LEGACY_REF.isClient()) new TILClientEntryLegacy();
    }
}
