package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.RegistryEntryEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;

@Getter
public abstract class BlockEventWrapper<E,B> extends RegistryEntryEventWrapper<E> {

    protected BlockAPI<B> blockAPI;

    protected BlockEventWrapper(CommonType<?> type) {
        super(type);
    }

    protected void setBlock(BlockAPI<B> api) {
        this.blockAPI = api;
        this.entryAPI = api.getEntryAPI();
    }
}