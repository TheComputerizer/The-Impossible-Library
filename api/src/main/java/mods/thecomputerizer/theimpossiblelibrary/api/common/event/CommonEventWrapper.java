package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.*;

public abstract class CommonEventWrapper<E> extends EventWrapper<E> {

    protected CommonEventWrapper(CommonType<?> type) {
        super(type);
    }

    @Override
    public boolean isClient() {
        return false;
    }

    @Override
    public boolean isCommon() {
        return true;
    }

    @Override
    public boolean isServer() {
        return false;
    }

    public static final class CommonType<E extends CommonEventWrapper<?>> extends EventType<E> { //TODO Finish implementing these

        public static CommonType<AttachCapabilitiesEventWrapper<?>> ATTACH_CAPABILITIES = new CommonType<>(false,false);
        public static CommonType<BlockBreakEventWrapper<?>> BLOCK_BREAK = new CommonType<>(true,false);
        public static CommonType<FluidCreateSourceEventWrapper<?>> BLOCK_CREATE_FLUID = new CommonType<>(false,true);
        public static CommonType<HarvestBlockDropsEventWrapper<?>> BLOCK_HARVEST = new CommonType<>(false,false);
        public static CommonType<BlockInteractEventWrapper<?>> BLOCK_INTERACT = new CommonType<>(true,false);
        public static CommonType<BlockNotifyNeighborEventWrapper<?>> BLOCK_NOTIFY_NEIGHBOR = new CommonType<>(true,false);
        public static CommonType<BlockPlaceEventWrapper<?>> BLOCK_PLACE = new CommonType<>(true,false);
        public static CommonType<FluidPlaceEventWrapper<?>> BLOCK_PLACE_FLUID = new CommonType<>(true,false);
        public static CommonType<BlockPlaceMultiEventWrapper<?>> BLOCK_PLACE_MULTI = new CommonType<>(true,false);
        public static CommonType<SpawnPortalEventWrapper<?>> BLOCK_SPAWN_PORTAL = new CommonType<>(true,false);
        public static CommonType<TrampleFarmlandEventWrapper<?>> BLOCK_TRAMPLE_FARMLAND = new CommonType<>(true,false);
        public static CommonType<CropGrowPostEventWrapper<?>> CROP_GROW_POST = new CommonType<>(false,false);
        public static CommonType<CropGrowPreEventWrapper<?>> CROP_GROW_PRE = new CommonType<>(false,true);
        public static CommonType<CustomTickEventWrapper<?>> CUSTOM_TICK = new CommonType<>(false,false);
        public static CommonType<EntityEnteringChunkEventWrapper<?>> ENTITY_ENTERING_CHUNK = new CommonType<>(false,false);
        public static CommonType<EntityJoinWorldEventWrapper<?>> ENTITY_JOIN_WORLD = new CommonType<>(true,false);
        public static CommonType<EntityStruckByLightningEventWrapper<?>> ENTITY_SMITTEN = new CommonType<>(true,false);
        public static CommonType<ExplosionDetonateEventWrapper<?>> EXPLOSION_DETONATE = new CommonType<>(false,false);
        public static CommonType<ExplosionStartEventWrapper<?>> EXPLOSION_START = new CommonType<>(true,false);
        public static CommonType<LivingAttackedEventWrapper<?>> LIVING_ATTACKED = new CommonType<>(true,false);
        public static CommonType<LivingDamageEventWrapper<?>> LIVING_DAMAGE = new CommonType<>(true,false);
        public static CommonType<LivingDeathEventWrapper<?>> LIVING_DEATH = new CommonType<>(true,false);
        public static CommonType<LivingFallEventWrapper<?>> LIVING_FALL = new CommonType<>(true,false);
        public static CommonType<LivingHealEventWrapper<?>> LIVING_HEAL = new CommonType<>(true,false);
        public static CommonType<LivingHurtEventWrapper<?>> LIVING_HURT = new CommonType<>(true,false);
        public static CommonType<LivingJumpEventWrapper<?>> LIVING_JUMP = new CommonType<>(false,false);
        public static CommonType<LivingKnockbackEventWrapper<?>> LIVING_KNOCKBACK = new CommonType<>(true,false);
        public static CommonType<LootingLevelEventWrapper<?>> LIVING_LOOTING_LEVEL = new CommonType<>(false,false);
        public static CommonType<SetAttackTargetEventWrapper<?>> LIVING_SET_TARGET = new CommonType<>(false,false);
        public static CommonType<LivingUpdateEventWrapper<?>> LIVING_UPDATE = new CommonType<>(true,false);
        public static CommonType<LivingItemUseFinishEventWrapper<?>> LIVING_ITEM_USE_FINISH = new CommonType<>(false,false);
        public static CommonType<LivingItemUseStartEventWrapper<?>> LIVING_ITEM_USE_START = new CommonType<>(true,false);
        public static CommonType<LivingItemUseStopEventWrapper<?>> LIVING_ITEM_USE_STOP = new CommonType<>(true,false);
        public static CommonType<LivingItemUseTickEventWrapper<?>> LIVING_ITEM_USE_TICK = new CommonType<>(true,false);
        public static CommonType<PlayerAdvancementEventWrapper<?>> PLAYER_ADVANCEMENT = new CommonType<>(false,false);
        public static CommonType<PlayerBreakSpeedEventWrapper<?>> PLAYER_BREAK_SPEED = new CommonType<>(true,false);
        public static CommonType<PlayerChangeGamemodeEventWrapper<?>> PLAYER_CHANGE_GAMEMODE = new CommonType<>(true,false);
        public static CommonType<PlayerChangedDimensionsEventWrapper<?>> PLAYER_CHANGED_DIMENSIONS = new CommonType<>(false,false);
        public static CommonType<PlayerCheckHarvestEventWrapper<?>> PLAYER_CHECK_HARVEST = new CommonType<>(false,false);
        public static CommonType<PlayerCloneEventWrapper<?>> PLAYER_CLONE = new CommonType<>(false,false);
        public static CommonType<PlayerInteractBlockEventWrapper<?>> PLAYER_INTERACT_BLOCK = new CommonType<>(true,false);
        public static CommonType<PlayerInteractEmptyEventWrapper<?>> PLAYER_INTERACT_EMPTY = new CommonType<>(false,false);
        public static CommonType<PlayerInteractEntityEventWrapper<?>> PLAYER_INTERACT_ENTITY = new CommonType<>(true,false);
        public static CommonType<PlayerInteractEntitySpecificEventWrapper<?>> PLAYER_INTERACT_ENTITY_AT = new CommonType<>(true,false);
        public static CommonType<PlayerInteractItemEventWrapper<?>> PLAYER_INTERACT_ITEM = new CommonType<>(true,false);
        public static CommonType<PlayerCraftedItemEventWrapper<?>> PLAYER_ITEM_CRAFTED = new CommonType<>(false,false);
        public static CommonType<PlayerPickupItemEventWrapper<?>> PLAYER_ITEM_PICKUP = new CommonType<>(false,false);
        public static CommonType<PlayerSmeltedItemEventWrapper<?>> PLAYER_ITEM_SMELTED = new CommonType<>(false,false);
        public static CommonType<PlayerLoadFromFileEventWrapper<?>> PLAYER_LOAD_FROM_FILE = new CommonType<>(false,false);
        public static CommonType<PlayerLoggedInEventWrapper<?>> PLAYER_LOGGED_IN = new CommonType<>(false,false);
        public static CommonType<PlayerLoggedOutEventWrapper<?>> PLAYER_LOGGED_OUT = new CommonType<>(false,false);
        public static CommonType<PlayerNameFormatEventWrapper<?>> PLAYER_NAME_FORMAT = new CommonType<>(false,false);
        public static CommonType<PlayerPunchBlockEventWrapper<?>> PLAYER_PUNCH_BLOCK = new CommonType<>(true,false);
        public static CommonType<PlayerPunchEntityEventWrapper<?>> PLAYER_PUNCH_ENTITY = new CommonType<>(true,false);
        public static CommonType<PlayerPunchItemEventWrapper<?>> PLAYER_PUNCH_ITEM = new CommonType<>(true,false);
        public static CommonType<PlayerRespawnEventWrapper<?>> PLAYER_RESPAWN = new CommonType<>(false,false);
        public static CommonType<PlayerSaveToFileEventWrapper<?>> PLAYER_SAVE_TO_FILE = new CommonType<>(false,false);
        public static CommonType<PlayerSleepInBedEventWrapper<?>> PLAYER_SLEEP_IN_BED = new CommonType<>(false,false);
        public static CommonType<PlayerStartTrackingEventWrapper<?>> PLAYER_START_TRACKING = new CommonType<>(false,false);
        public static CommonType<PlayerStopTrackingEventWrapper<?>> PLAYER_STOP_TRACKING = new CommonType<>(false,false);
        public static CommonType<PlayerNameTabFormatEventWrapper<?>> PLAYER_TAB_FORMAT = new CommonType<>(false,false);
        public static CommonType<PlayerVisibilityEventWrapper<?>> PLAYER_VISIBILITY = new CommonType<>(false,false);
        public static CommonType<PlayerChangeXPEventWrapper<?>> PLAYER_XP_CHANGE = new CommonType<>(true,false);
        public static CommonType<PlayerPickupXPEventWrapper<?>> PLAYER_XP_PICKUP = new CommonType<>(true,false);
        public static CommonType<PlayerLevelUpEventWrapper<?>> PLAYER_XP_LEVEL_CHANGE = new CommonType<>(true,false);
        public static CommonType<RegistryEventWrapper<?>> REGISTER_GENERIC = new CommonType<>(false,false);
        public static CommonType<PlayerTickEventWrapper<?>> TICK_PLAYER = new CommonType<>(false,false);
        public static CommonType<WorldTickEventWrapper<?>> TICK_WORLD = new CommonType<>(false,false);
        public static CommonType<WorldCreateSpawnPosEventWrapper<?>> WORLD_CREATE_SPAWN_POS = new CommonType<>(true,false);
        public static CommonType<WorldLoadEventWrapper<?>> WORLD_LOAD = new CommonType<>(false,false);
        public static CommonType<PotentialSpawnsEventWrapper<?>> WORLD_POTENTIAL_SPAWNS = new CommonType<>(true,false);
        public static CommonType<WorldSaveEventWrapper<?>> WORLD_SAVE = new CommonType<>(false,false);
        public static CommonType<WorldUnloadEventWrapper<?>> WORLD_UNLOAD = new CommonType<>(false,false);

        private CommonType(boolean cancelable, boolean hasResult) {
            super(cancelable,hasResult);
        }

        @Override
        public boolean isClient() {
            return false;
        }

        @Override
        public boolean isCommon() {
            return true;
        }

        @Override
        public boolean isServer() {
            return false;
        }
    }
}