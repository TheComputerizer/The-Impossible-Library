package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CancellableEvent;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.EntityEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.world.WorldEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;

import javax.annotation.Nullable;
import java.util.Objects;

public abstract class BlockPlaceEventWrapper<B,S,E,W> extends CancellableEvent implements BlockEventAPI, EntityEventAPI, WorldEventAPI {

    private final EntityAPI<E> entity;
    private final BlockStateAPI<B,S> placed;
    private final BlockStateAPI<B,S> placedAgainst;
    private final WorldAPI<W> world;

    protected BlockPlaceEventWrapper(@Nullable EntityAPI<E> entity, BlockStateAPI<B,S> placed,
                                     BlockStateAPI<B,S> placedAgainst, WorldAPI<W> world) {
        this.entity = entity;
        this.placed = placed;
        this.placedAgainst = placedAgainst;
        this.world = world;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <BL> BL getBlock() {
        return (BL)this.placed.getBlock();
    }

    @Override
    public BlockAPI<B> getBlockAPI() {
        return this.placed.getBlockAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <EN> EN getEntity() {
        return Objects.nonNull(this.entity) ? (EN)this.entity.getEntity() : null;
    }

    @Override
    public EntityAPI<E> getEntityAPI() {
        return this.entity;
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegistryEntryAPI<B> getEntry() {
        return (RegistryEntryAPI<B>)this.placed.getBlockAPI().getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getEntryValue() {
        return (V)this.placed.getBlockAPI().getEntryAPI().getValue();
    }

    @SuppressWarnings("unchecked")
    public <ST> ST getPlaced() {
        return (ST)this.placed.getState();
    }

    @SuppressWarnings("unchecked")
    public <ST> ST getPlacedAgainst() {
        return (ST)this.placedAgainst.getState();
    }

    public BlockStateAPI<B,S> getPlacedAgainstAPI() {
        return this.placedAgainst;
    }

    public BlockStateAPI<B,S> getPlacedAPI() {
        return this.placed;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <WO> WO getWorld() {
        return (WO)this.world;
    }

    @Override
    public WorldAPI<W> getWorldAPI() {
        return this.world;
    }
}