package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.client;

import mods.thecomputerizer.theimpossiblelibrary.api.common.SharedHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.NeoForgeHandlesCommon;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.client.Client1_20;

import java.util.function.Supplier;

public abstract class ClientNeoForge1_20 extends Client1_20 {
    
    @Override public Supplier<SharedHandlesCommon> initSharedHandlesCommon() {
        return NeoForgeHandlesCommon::new;
    }
}