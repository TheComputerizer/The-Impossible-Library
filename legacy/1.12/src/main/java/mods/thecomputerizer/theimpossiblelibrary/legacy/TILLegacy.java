package mods.thecomputerizer.theimpossiblelibrary.legacy;

import mods.thecomputerizer.theimpossiblelibrary.api.Reference;
import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import net.minecraftforge.fml.relauncher.FMLLaunchHandler;

public class TILLegacy {

    public static final Reference LEGACY_REF = TILRef.init(FMLLaunchHandler.side()::isClient,"");
}
