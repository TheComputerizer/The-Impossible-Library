package mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.util.CustomTickNeoForge;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events.LivingAttackedEventNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events.LivingDamageEventNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events.LivingDeathEventNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events.LivingHurtEventNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.neoforge.v20.common.event.events.LootingLevelEventNeoForge1_20;
import mods.thecomputerizer.theimpossiblelibrary.shared.v20.common.event.CommonEvents1_20;
import net.neoforged.bus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.ALLOW;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DEFAULT;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;
import static net.neoforged.neoforge.common.NeoForge.EVENT_BUS;

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
        LIVING_ATTACKED.setConnector(new LivingAttackedEventNeoForge1_20());
        LIVING_DAMAGE.setConnector(new LivingDamageEventNeoForge1_20());
        LIVING_DEATH.setConnector(new LivingDeathEventNeoForge1_20());
        LIVING_FALL.setConnector(new LivingFallEventNeoForge());
        LIVING_HEAL.setConnector(new LivingHealEventNeoForge());
        LIVING_HURT.setConnector(new LivingHurtEventNeoForge1_20());
        LIVING_JUMP.setConnector(new LivingJumpEventNeoForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventNeoForge());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventNeoForge1_20());
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
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventNeoForge()); //TODO Deferred registers?
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventNeoForge());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventNeoForge());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventNeoForge());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventNeoForge());
        TICK_PLAYER.setConnector(new PlayerTickEventNeoForge());
        TICK_WORLD.setConnector(new WorldTickEventNeoForge());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventNeoForge());
        WORLD_LOAD.setConnector(new WorldLoadEventNeoForge());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventNeoForge());
        WORLD_SAVE.setConnector(new WorldSaveEventNeoForge());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventNeoForge());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickNeoForge(ticker));
    }

    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}