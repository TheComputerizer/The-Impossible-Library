package mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.core.ReflectionHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.fabric.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.fabric.util.CustomTickFabric;
import mods.thecomputerizer.theimpossiblelibrary.fabric.v16.m5.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.event.CommonEvents1_16_5;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;

@SuppressWarnings("unused") public class CommonEventsFabric1_16_5 extends CommonEvents1_16_5 {

    @Override public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventFabric());
        BLOCK_BREAK.setConnector(new BlockBreakEventFabric());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventFabric());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventFabric());
        BLOCK_INTERACT.setConnector(new BlockInteractEventFabric());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventFabric());
        BLOCK_PLACE.setConnector(new BlockPlaceEventFabric());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventFabric());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventFabric());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventFabric());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventFabric());
        CROP_GROW_POST.setConnector(new CropGrowPostEventFabric());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventFabric());
        CUSTOM_TICK.setConnector(new CustomTickEventFabric());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventFabric());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventFabric());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventFabric());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventFabric());
        EXPLOSION_START.setConnector(new ExplosionStartEventFabric());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventFabric1_16_5());
        LIVING_DAMAGE.setConnector(new LivingDamageEventFabric1_16_5());
        LIVING_DEATH.setConnector(new LivingDeathEventFabric1_16_5());
        LIVING_FALL.setConnector(new LivingFallEventFabric());
        LIVING_HEAL.setConnector(new LivingHealEventFabric());
        LIVING_HURT.setConnector(new LivingHurtEventFabric1_16_5());
        LIVING_JUMP.setConnector(new LivingJumpEventFabric());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventFabric());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventFabric1_16_5());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventFabric());
        LIVING_UPDATE.setConnector(new LivingUpdateEventFabric());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventFabric());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventFabric());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventFabric());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventFabric());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventFabric());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventFabric());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventFabric());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventFabric());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventFabric());
        PLAYER_CLONE.setConnector(new PlayerCloneEventFabric());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventFabric());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventFabric());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventFabric());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventFabric());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventFabric());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventFabric());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventFabric());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventFabric());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventFabric());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventFabric());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventFabric());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventFabric());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventFabric());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventFabric());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventFabric());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventFabric());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventFabric());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventFabric());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventFabric());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventFabric());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventFabric());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventFabric());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventFabric());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventFabric());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventFabric());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventFabric1_16_5());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventFabric1_16_5());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventFabric1_16_5());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventFabric1_16_5());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventFabric1_16_5());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventFabric1_16_5());
        TICK_PLAYER.setConnector(new PlayerTickEventFabric());
        TICK_WORLD.setConnector(new WorldTickEventFabric());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventFabric());
        WORLD_LOAD.setConnector(new WorldLoadEventFabric());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventFabric());
        WORLD_SAVE.setConnector(new WorldSaveEventFabric());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventFabric());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return DEFAULT;
    }
    
    @Override public void postCustomTick(CustomTick ticker) {
        CustomTickFabric.CUSTOM_TICK.invoker().onTick(ticker);
    }
    
    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        Class<?> wrapperClass = wrapper.getClass();
        ReflectionHelper.invokeStaticMethod(wrapperClass,"register",new Class<?>[]{wrapperClass},wrapper);
    }
    
    @SuppressWarnings("unchecked")
    @Override public Object setEventResult(Result result) {
        return null;
    }
}