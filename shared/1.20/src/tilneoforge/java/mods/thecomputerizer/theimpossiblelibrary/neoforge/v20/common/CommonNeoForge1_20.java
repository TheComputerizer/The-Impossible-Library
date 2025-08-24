package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.NeoForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.Common1_20;

import java.util.function.Supplier;

public abstract class CommonNeoForge1_20 extends Common1_20 {
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return NeoForgeHandlesCommon::new;
    }
}