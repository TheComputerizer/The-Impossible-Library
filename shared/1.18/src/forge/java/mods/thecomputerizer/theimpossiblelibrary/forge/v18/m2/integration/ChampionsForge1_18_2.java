package mods.thecomputerizer.theimpossiblelibrary.forge.v18.m2.integration;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.integration.ChampionsAPI;
import net.minecraft.util.Tuple;
import net.minecraft.world.entity.Entity;
import top.theillusivec4.champions.api.IAffix;
import top.theillusivec4.champions.api.IChampion;
import top.theillusivec4.champions.api.IChampion.Client;
import top.theillusivec4.champions.api.IChampion.Server;
import top.theillusivec4.champions.common.capability.ChampionCapability;
import top.theillusivec4.champions.common.rank.Rank;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;
import java.util.Collection;
import java.util.Objects;
import java.util.stream.Collectors;

public class ChampionsForge1_18_2 extends ChampionsAPI {

    @SuppressWarnings("DataFlowIssue")
    public @Nullable IChampion getCapability(EntityAPI<?,?> api) {
        return ChampionCapability.getCapability((Entity)api.unwrap()).orElse(null);
    }
    
    Collection<String> getAffixNames(Collection<IAffix> affixes) {
        return affixes.stream().map(IAffix::getIdentifier).filter(Objects::nonNull).collect(Collectors.toSet());
    }

    @Override public @Nullable ChampionData getChampionData(EntityAPI<?,?> entity) {
        IChampion cap = getCapability(entity);
        return Objects.nonNull(cap) ? (entity.getWorld().isServer() ? getServer(cap) : getClient(cap)) : null;
    }

    private ChampionData getClient(@Nonnull IChampion cap) {
        Client client = cap.getClient();
        Collection<String> affixes = getAffixNames(client.getAffixes());
        return new ChampionData(null,affixes,client.getRank().map(Tuple::getA).orElse(-1));
    }

    private ChampionData getServer(@Nonnull IChampion cap) {
        Server server = cap.getServer();
        Collection<String> affixes = getAffixNames(server.getAffixes());
        return new ChampionData(null,affixes,server.getRank().map(Rank::getTier).orElse(-1));
    }
}