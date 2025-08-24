package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event;

import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.event.CommonEvents1_20;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public abstract class CommonEventsNeoForge1_20 extends CommonEvents1_20 {

    @Override public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventNeoForge());
        BLOCK_BREAK.setConnector(new BlockBreakEventNeoForge());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventNeoForge());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventNeoForge());
        BLOCK_INTERACT.setConnector(new BlockInteractEventNeoForge());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventNeoForge());
        BLOCK_PLACE.setConnector(new BlockPlaceEventNeoForge());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventNeoForge());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventNeoForge());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventNeoForge());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventNeoForge());
        CROP_GROW_POST.setConnector(new CropGrowPostEventNeoForge());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventNeoForge());
        CUSTOM_TICK.setConnector(new CustomTickEventNeoForge());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventNeoForge());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventNeoForge());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventNeoForge());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventNeoForge());
        EXPLOSION_START.setConnector(new ExplosionStartEventNeoForge());
        LIVING_FALL.setConnector(new LivingFallEventNeoForge());
        LIVING_HEAL.setConnector(new LivingHealEventNeoForge());
        LIVING_JUMP.setConnector(new LivingJumpEventNeoForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventNeoForge());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventNeoForge());
        LIVING_UPDATE.setConnector(new LivingUpdateEventNeoForge());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventNeoForge());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventNeoForge());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventNeoForge());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventNeoForge());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventNeoForge());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventNeoForge());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventNeoForge());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventNeoForge());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventNeoForge());
        PLAYER_CLONE.setConnector(new PlayerCloneEventNeoForge());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventNeoForge());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventNeoForge());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventNeoForge());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventNeoForge());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventNeoForge());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventNeoForge());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventNeoForge());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventNeoForge());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventNeoForge());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventNeoForge());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventNeoForge());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventNeoForge());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventNeoForge());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventNeoForge());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventNeoForge());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventNeoForge());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventNeoForge());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventNeoForge());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventNeoForge());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventNeoForge());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventNeoForge());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventNeoForge());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventNeoForge());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventNeoForge());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventNeoForge());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventNeoForge());
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventNeoForge());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventNeoForge());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventNeoForge());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventNeoForge());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventNeoForge());
        WORLD_LOAD.setConnector(new WorldLoadEventNeoForge());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventNeoForge());
        WORLD_SAVE.setConnector(new WorldSaveEventNeoForge());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventNeoForge());
        super.defineEvents();
    }
}