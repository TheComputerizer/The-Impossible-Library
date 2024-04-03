package mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.advancement.AdvancementAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.RegistryEntryAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.registry.entity.PlayerAPI;

public abstract class AdvancementEventWrapper<P,A> implements PlayerEventAPI {

    private final PlayerAPI<P> player;
    private final AdvancementAPI<A> advancement;

    protected AdvancementEventWrapper(PlayerAPI<P> player, AdvancementAPI<A> advancement) {
        this.player = player;
        this.advancement = advancement;
    }

    @SuppressWarnings("unchecked")
    public <AD> AD getAdvancement() {
        return (AD)this.advancement.getAdvancement();
    }

    public AdvancementAPI<A> getAdvancementAPI() {
        return this.advancement;
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
    public RegistryEntryAPI<P> getEntry() {
        return (RegistryEntryAPI<P>)this.player.getEntityAPI().getEntryAPI();
    }

    @SuppressWarnings("unchecked")
    @Override
    public <V> V getEntryValue() {
        return (V)this.player.getEntityAPI().getEntryAPI().getValue();
    }

    @Override
    public LivingEntityAPI<P> getLivingAPI() {
        return this.player.getLivingAPI();
    }

    @Override
    public PlayerAPI<P> getPlayerAPI() {
        return this.player;
    }
}