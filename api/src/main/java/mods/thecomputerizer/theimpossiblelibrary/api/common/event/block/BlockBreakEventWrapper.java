package mods.thecomputerizer.theimpossiblelibrary.api.common.event.block;

import lombok.Getter;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.CancellableEvent;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.PlayerEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.world.WorldEventAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.block.BlockStateAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

public abstract class BlockBreakEventWrapper<B,S,P,W> extends CancellableEvent implements BlockEventAPI, PlayerEventAPI, WorldEventAPI {

    private final PlayerAPI<P> player;
    private final BlockStateAPI<B,S> state;
    private final WorldAPI<W> world;
    @Getter protected int xp;

    protected BlockBreakEventWrapper(PlayerAPI<P> player, BlockStateAPI<B,S> state, WorldAPI<W> world, int xp) {
        this.player = player;
        this.state = state;
        this.world = world;
        this.xp = xp;
    }

    @SuppressWarnings("unchecked")
    @Override
    public <BL> BL getBlock() {
        return (BL)this.state.getBlock();
    }

    @Override
    public BlockAPI<B> getBlockAPI() {
        return this.state.getBlockAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <E> E getEntity() {
        return (E)this.player.getPlayer();
    }

    @Override
    public EntityAPI<P> getEntityAPI() {
        return this.player.getEntityAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public RegistryEntryAPI<B> getEntry() {
        return (RegistryEntryAPI<B>)this.state.getBlockAPI().getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getEntryValue() {
        return (V)this.state.getBlockAPI().getEntryAPI().getValue();
    }

    @Override
    public LivingEntityAPI<P> getLivingAPI() {
        return this.player.getLivingAPI();
    }

    @Override
    public PlayerAPI<P> getPlayerAPI() {
        return this.player;
    }

    @SuppressWarnings("unchecked")
    public <ST> ST getState() {
        return (ST)this.state.getState();
    }

    public BlockStateAPI<B,S> getStateAPI() {
        return this.state;
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

    public abstract void setXP(int xp);
}