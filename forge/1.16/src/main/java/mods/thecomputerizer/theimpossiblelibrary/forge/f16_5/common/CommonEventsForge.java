package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common;

import mods.thecomputerizer.theimpossiblelibrary.api.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonEventsHelper;
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

import java.util.Collection;

@EventBusSubscriber(modid = TILRef.MODID)
public class CommonEventsForge implements CommonEventsAPI {

    @SubscribeEvent
    public static void onAdvancement(AdvancementEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onAttachCapabilities(AttachCapabilitiesEvent<?> event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onBlockBreak(BreakEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onBlockPlace(EntityPlaceEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityAttacked(LivingAttackEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityDamage(LivingDamageEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityDeath(LivingDeathEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityEnteringChunk(EnteringChunk event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityFall(LivingFallEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityFinishUsingItem(Finish event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityHurt(LivingHurtEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityJoinWorld(EntityJoinWorldEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityJump(LivingJumpEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityKnockBack(LivingKnockBackEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntityLootingLevel(LootingLevelEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntitySetTarget(LivingSetAttackTargetEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onEntitySmitten(EntityStruckByLightningEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onExplosionDetonate(Detonate event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerBreakSpeed(BreakSpeed event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerChangeDimensions(PlayerChangedDimensionEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerClone(Clone event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerConnected(PlayerLoggedInEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerConnected(PlayerLoggedOutEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerInteract(PlayerInteractEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerPickUpItem(ItemPickupEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerPickUpXP(PickupXp event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerRespawn(PlayerRespawnEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerSleepInBedEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPlayerSleep(PlayerTickEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onPotentialSpawns(PotentialSpawns event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent event) {
        CommonEventsHelper.invoke(event);
    }

    @SubscribeEvent
    public static void onWorldTick(WorldTickEvent event) {
        CommonEventsHelper.invoke(event);
    }

    public CommonEventsForge() {

    }

    @SuppressWarnings("deprecation")
    @Override
    public void defineEventClasses(Collection<Class<?>> classes) {
        classes.add(AdvancementEvent.class);
        classes.add(AttachCapabilitiesEvent.class);
        classes.add(BreakEvent.class);
        classes.add(EntityPlaceEvent.class);
        classes.add(LivingAttackEvent.class);
        classes.add(LivingDamageEvent.class);
        classes.add(LivingDeathEvent.class);
        classes.add(EnteringChunk.class);
        classes.add(LivingFallEvent.class);
        classes.add(Finish.class);
        classes.add(LivingHurtEvent.class);
        classes.add(EntityJoinWorldEvent.class);
        classes.add(LivingJumpEvent.class);
        classes.add(LivingKnockBackEvent.class);
        classes.add(LootingLevelEvent.class);
        classes.add(LivingSetAttackTargetEvent.class);
        classes.add(EntityStruckByLightningEvent.class);
        classes.add(Detonate.class);
        classes.add(BreakSpeed.class);
        classes.add(PlayerChangedDimensionEvent.class);
        classes.add(Clone.class);
        classes.add(PlayerLoggedInEvent.class);
        classes.add(PlayerLoggedOutEvent.class);
        classes.add(PlayerInteractEvent.class);
        classes.add(ItemPickupEvent.class);
        classes.add(PickupXp.class);
        classes.add(PlayerRespawnEvent.class);
        classes.add(PlayerSleepInBedEvent.class);
        classes.add(PlayerTickEvent.class);
        classes.add(PotentialSpawns.class);
        classes.add(ServerTickEvent.class);
        classes.add(WorldTickEvent.class);
    }

    @Override
    public void initDefaultListeners() {

    }
}