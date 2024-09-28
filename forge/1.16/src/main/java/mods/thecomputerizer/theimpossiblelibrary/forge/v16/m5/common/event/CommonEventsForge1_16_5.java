package mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result;
import mods.thecomputerizer.theimpossiblelibrary.api.util.CustomTick;
import mods.thecomputerizer.theimpossiblelibrary.forge.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.forge.util.CustomTickForge;
import mods.thecomputerizer.theimpossiblelibrary.forge.v16.m5.common.event.events.*;
import mods.thecomputerizer.theimpossiblelibrary.shared.v16.m5.common.event.CommonEvents1_16_5;
import net.minecraftforge.eventbus.api.Event;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.*;
import static net.minecraftforge.common.MinecraftForge.EVENT_BUS;

@SuppressWarnings("unused") public class CommonEventsForge1_16_5 extends CommonEvents1_16_5 {

    @Override public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventForge());
        BLOCK_BREAK.setConnector(new BlockBreakEventForge());
        BLOCK_CREATE_FLUID.setConnector(new FluidCreateSourceEventForge());
        BLOCK_HARVEST.setConnector(new HarvestBlockDropsEventForge());
        BLOCK_INTERACT.setConnector(new BlockInteractEventForge());
        BLOCK_NOTIFY_NEIGHBOR.setConnector(new BlockNotifyNeighborEventForge());
        BLOCK_PLACE.setConnector(new BlockPlaceEventForge());
        BLOCK_PLACE_FLUID.setConnector(new FluidPlaceEventForge());
        BLOCK_PLACE_MULTI.setConnector(new BlockPlaceMultiEventForge());
        BLOCK_SPAWN_PORTAL.setConnector(new SpawnPortalEventForge());
        BLOCK_TRAMPLE_FARMLAND.setConnector(new TrampleFarmlandEventForge());
        CROP_GROW_POST.setConnector(new CropGrowPostEventForge());
        CROP_GROW_PRE.setConnector(new CropGrowPreEventForge());
        CUSTOM_TICK.setConnector(new CustomTickEventForge());
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventForge());
        ENTITY_JOIN_WORLD.setConnector(new EntityJoinWorldEventForge());
        ENTITY_SMITTEN.setConnector(new EntityStruckByLightningEventForge());
        EXPLOSION_DETONATE.setConnector(new ExplosionDetonateEventForge());
        EXPLOSION_START.setConnector(new ExplosionStartEventForge());
        LIVING_ATTACKED.setConnector(new LivingAttackedEventForge1_16_5());
        LIVING_DAMAGE.setConnector(new LivingDamageEventForge1_16_5());
        LIVING_DEATH.setConnector(new LivingDeathEventForge1_16_5());
        LIVING_FALL.setConnector(new LivingFallEventForge());
        LIVING_HEAL.setConnector(new LivingHealEventForge());
        LIVING_HURT.setConnector(new LivingHurtEventForge1_16_5());
        LIVING_JUMP.setConnector(new LivingJumpEventForge());
        LIVING_KNOCKBACK.setConnector(new LivingKnockbackEventForge());
        LIVING_LOOTING_LEVEL.setConnector(new LootingLevelEventForge1_16_5());
        LIVING_SET_TARGET.setConnector(new SetAttackTargetEventForge());
        LIVING_UPDATE.setConnector(new LivingUpdateEventForge());
        LIVING_ITEM_USE_FINISH.setConnector(new LivingItemUseFinishEventForge());
        LIVING_ITEM_USE_START.setConnector(new LivingItemUseStartEventForge());
        LIVING_ITEM_USE_STOP.setConnector(new LivingItemUseStopEventForge());
        LIVING_ITEM_USE_TICK.setConnector(new LivingItemUseTickEventForge());
        PLAYER_ADVANCEMENT.setConnector(new PlayerAdvancementEventForge());
        PLAYER_BREAK_SPEED.setConnector(new PlayerBreakSpeedEventForge());
        PLAYER_CHANGE_GAMEMODE.setConnector(new PlayerChangeGamemodeEventForge());
        PLAYER_CHANGED_DIMENSIONS.setConnector(new PlayerChangedDimensionsEventForge());
        PLAYER_CHECK_HARVEST.setConnector(new PlayerCheckHarvestEventForge());
        PLAYER_CLONE.setConnector(new PlayerCloneEventForge());
        PLAYER_INTERACT_BLOCK.setConnector(new PlayerInteractBlockEventForge());
        PLAYER_INTERACT_EMPTY.setConnector(new PlayerInteractEmptyEventForge());
        PLAYER_INTERACT_ENTITY.setConnector(new PlayerInteractEntityEventForge());
        PLAYER_INTERACT_ENTITY_AT.setConnector(new PlayerInteractEntitySpecificEventForge());
        PLAYER_INTERACT_ITEM.setConnector(new PlayerInteractItemEventForge());
        PLAYER_ITEM_CRAFTED.setConnector(new PlayerCraftedItemEventForge());
        PLAYER_ITEM_PICKUP.setConnector(new PlayerPickupItemEventForge());
        PLAYER_ITEM_SMELTED.setConnector(new PlayerSmeltedItemEventForge());
        PLAYER_LOAD_FROM_FILE.setConnector(new PlayerLoadFromFileEventForge());
        PLAYER_LOGGED_IN.setConnector(new PlayerLoggedInEventForge());
        PLAYER_LOGGED_OUT.setConnector(new PlayerLoggedOutEventForge());
        PLAYER_NAME_FORMAT.setConnector(new PlayerNameFormatEventForge());
        PLAYER_PUNCH_BLOCK.setConnector(new PlayerPunchBlockEventForge());
        PLAYER_PUNCH_ENTITY.setConnector(new PlayerPunchEntityEventForge());
        PLAYER_PUNCH_ITEM.setConnector(new PlayerPunchItemEventForge());
        PLAYER_RESPAWN.setConnector(new PlayerRespawnEventForge());
        PLAYER_SAVE_TO_FILE.setConnector(new PlayerSaveToFileEventForge());
        PLAYER_SLEEP_IN_BED.setConnector(new PlayerSleepInBedEventForge());
        PLAYER_START_TRACKING.setConnector(new PlayerStartTrackingEventForge());
        PLAYER_STOP_TRACKING.setConnector(new PlayerStopTrackingEventForge());
        PLAYER_TAB_FORMAT.setConnector(new PlayerNameTabFormatEventForge());
        PLAYER_VISIBILITY.setConnector(new PlayerVisibilityEventForge());
        PLAYER_XP_CHANGE.setConnector(new PlayerChangeXPEventForge());
        PLAYER_XP_PICKUP.setConnector(new PlayerPickupXPEventForge());
        PLAYER_XP_LEVEL_CHANGE.setConnector(new PlayerLevelChangeEventForge());
        REGISTER_BLOCK_ENTITIES.setConnector(new RegisterBlockEntitiesEventForge1_16_5()); //TODO Deferred registers?
        REGISTER_BLOCKS.setConnector(new RegisterBlocksEventForge1_16_5());
        REGISTER_COMMANDS.setConnector(new RegisterCommandsEventForge1_16_5());
        REGISTER_ENTITIES.setConnector(new RegisterEntitiesEventForge1_16_5());
        REGISTER_ITEMS.setConnector(new RegisterItemsEventForge1_16_5());
        REGISTER_SOUNDS.setConnector(new RegisterSoundsEventForge1_16_5());
        TICK_PLAYER.setConnector(new PlayerTickEventForge());
        TICK_WORLD.setConnector(new WorldTickEventForge());
        WORLD_CREATE_SPAWN_POS.setConnector(new WorldCreateSpawnPosEventForge());
        WORLD_LOAD.setConnector(new WorldLoadEventForge());
        WORLD_POTENTIAL_SPAWNS.setConnector(new PotentialSpawnsEventForge());
        WORLD_SAVE.setConnector(new WorldSaveEventForge());
        WORLD_UNLOAD.setConnector(new WorldUnloadEventForge());
        super.defineEvents();
    }
    
    @Override public <R> Result getEventResult(R result) {
        return result==Event.Result.DEFAULT ? DEFAULT : (result==Event.Result.DENY ? DENY : ALLOW);
    }

    @Override public void postCustomTick(CustomTick ticker) {
        EVENT_BUS.post(new CustomTickForge(ticker));
    }

    @Override public <E extends EventWrapper<?>> void register(E wrapper) {
        EVENT_BUS.register(wrapper.getClass());
    }
    
    @SuppressWarnings("unchecked")
    @Override public Event.Result setEventResult(Result result) {
        return result==DEFAULT ? Event.Result.DEFAULT : (result==DENY ? Event.Result.DENY : Event.Result.ALLOW);
    }
}