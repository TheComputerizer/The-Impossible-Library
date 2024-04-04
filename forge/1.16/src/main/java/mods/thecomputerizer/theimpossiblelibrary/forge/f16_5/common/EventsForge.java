package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.AttachCapabilitiesEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.AttachCapabilitiesEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.block.BlockBreakEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.block.BlockPlaceEventForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity.*;
import net.minecraftforge.event.AttachCapabilitiesEvent;
import net.minecraftforge.event.TickEvent.PlayerTickEvent;
import net.minecraftforge.event.TickEvent.ServerTickEvent;
import net.minecraftforge.event.TickEvent.WorldTickEvent;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.EntityJoinWorldEvent;
import net.minecraftforge.event.entity.EntityStruckByLightningEvent;
import net.minecraftforge.event.entity.living.*;
import net.minecraftforge.event.entity.living.LivingEntityUseItemEvent.Finish;
import net.minecraftforge.event.entity.living.LivingEvent.LivingJumpEvent;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.BreakSpeed;
import net.minecraftforge.event.entity.player.PlayerEvent.Clone;
import net.minecraftforge.event.entity.player.PlayerEvent.ItemPickupEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerChangedDimensionEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedInEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerLoggedOutEvent;
import net.minecraftforge.event.entity.player.PlayerEvent.PlayerRespawnEvent;
import net.minecraftforge.event.entity.player.PlayerInteractEvent;
import net.minecraftforge.event.entity.player.PlayerSleepInBedEvent;
import net.minecraftforge.event.entity.player.PlayerXpEvent.PickupXp;
import net.minecraftforge.event.world.BlockEvent.BreakEvent;
import net.minecraftforge.event.world.BlockEvent.EntityPlaceEvent;
import net.minecraftforge.event.world.ExplosionEvent.Detonate;
import net.minecraftforge.event.world.WorldEvent.PotentialSpawns;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;

import java.util.Set;

@EventBusSubscriber(modid = TILRef.MODID)
public class EventsForge implements EventsAPI {

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<?> event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onBlockPlace(EntityPlaceEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityEnteringChunk(EnteringChunk event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        EventHelper.invoke(event);
    }

    //TODO Implement events below this

    @SubscribeEvent
    public static void onEntityFinishUsingItem(Finish event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingJumpEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityKnockBack(LivingKnockBackEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityLootingLevel(LootingLevelEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntitySetTarget(LivingSetAttackTargetEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntitySmitten(EntityStruckByLightningEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onExplosionDetonate(Detonate event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(BreakSpeed event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerChangeDimensions(PlayerChangedDimensionEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerClone(Clone event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerConnected(PlayerLoggedInEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerConnected(PlayerLoggedOutEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerPickUpItem(ItemPickupEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerPickUpXP(PickupXp event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerTick(PlayerTickEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPotentialSpawns(PotentialSpawns event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        EventHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
        EventHelper.invoke(event);
    }

    public EventsForge() {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void defineEventClasses(Set<EventHelper.EventEntry<?,?,?>> entries) { //TODO finish implementing this
        entries.add(new EventHelper.EventEntry<>(AdvancementEvent.class,AdvancementEventWrapper.class,AdvancementEventForge.class));
        entries.add(new EventHelper.EventEntry<>(AttachCapabilitiesEvent.class,AttachCapabilitiesEventWrapper.class,AttachCapabilitiesEventForge.class));
        entries.add(new EventHelper.EventEntry<>(BreakEvent.class,BlockBreakEventWrapper.class,BlockBreakEventForge.class));
        entries.add(new EventHelper.EventEntry<>(EntityPlaceEvent.class,BlockPlaceEventWrapper.class,BlockPlaceEventForge.class));
        entries.add(new EventHelper.EventEntry<>(LivingAttackEvent.class,LivingAttackedEventWrapper.class,LivingAttackedEventForge.class));
        entries.add(new EventHelper.EventEntry<>(LivingDamageEvent.class,LivingDamageEventWrapper.class,LivingDamageEventForge.class));
        entries.add(new EventHelper.EventEntry<>(LivingDeathEvent.class,LivingDeathEventWrapper.class,LivingDeathEventForge.class));
        entries.add(new EventHelper.EventEntry<>(EnteringChunk.class,EntityEnteringChunkEventWrapper.class,EntityEnteringChunkEventForge.class));
        entries.add(new EventHelper.EventEntry<>(LivingFallEvent.class,LivingFallEventWrapper.class,LivingFallEventForge.class));
        /*
        entries.add(new EventEntry<>(Finish.class,null,null));
        entries.add(new EventEntry<>(LivingHurtEvent.class,null,null));
        entries.add(new EventEntry<>(EntityJoinWorldEvent.class,null,null));
        entries.add(new EventEntry<>(LivingJumpEvent.class,null,null));
        entries.add(new EventEntry<>(LivingKnockBackEvent.class,null,null));
        entries.add(new EventEntry<>(LootingLevelEvent.class,null,null));
        entries.add(new EventEntry<>(LivingSetAttackTargetEvent.class,null,null));
        entries.add(new EventEntry<>(EntityStruckByLightningEvent.class,null,null));
        entries.add(new EventEntry<>(Detonate.class,null));
        entries.add(new EventEntry<>(BreakSpeed.class,null));
        entries.add(new EventEntry<>(PlayerChangedDimensionEvent.class,null,null));
        entries.add(new EventEntry<>(Clone.class,null,null));
        entries.add(new EventEntry<>(PlayerLoggedInEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerLoggedOutEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerInteractEvent.class,null,null));
        entries.add(new EventEntry<>(ItemPickupEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerPickupXpEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerRespawnEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerSleepInBedEvent.class,null,null));
        entries.add(new EventEntry<>(PlayerTickEvent.class,null,null));
        entries.add(new EventEntry<>(PotentialSpawns.class,null,null));
        entries.add(new EventEntry<>(ServerTickEvent.class,null,null));
        entries.add(new EventEntry<>(WorldTickEvent.class,null,null));
         */
    }

    @Override
    public void initDefaultListeners() {

    }
}