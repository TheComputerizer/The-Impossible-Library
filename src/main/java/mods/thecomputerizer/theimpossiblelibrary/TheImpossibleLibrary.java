package mods.thecomputerizer.theimpossiblelibrary;

import mods.thecomputerizer.theimpossiblelibrary.util.file.DataUtil;
import net.fabricmc.api.ModInitializer;

public class TheImpossibleLibrary implements ModInitializer {

    @Override
    public void onInitialize() {
        DataUtil.initGlobal();
    }
}
