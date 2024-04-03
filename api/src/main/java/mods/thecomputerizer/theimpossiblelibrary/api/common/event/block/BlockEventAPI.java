package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryObjectEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;

public interface BlockEventAPI extends RegistryObjectEventAPI {

    <B> B getBlock();
    BlockAPI<?> getBlockAPI();
}