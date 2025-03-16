package mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.common;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ModHelperAPI;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.common.CommonFabric1_21;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v21.m1.integration.ModHelperFabric1_21_1;

import java.util.function.Supplier;

public class CommonFabric1_21_1 extends CommonFabric1_21 {
    
    @Override public Supplier<ModHelperAPI> initModHelper() {
        return () -> new ModHelperFabric1_21_1(CoreAPI.getInstance().getSide());
    }
}