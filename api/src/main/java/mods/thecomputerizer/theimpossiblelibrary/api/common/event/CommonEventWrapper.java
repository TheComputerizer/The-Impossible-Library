package mods.thecomputerizer.theimpossiblelibrary.api.common.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockBreakEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.block.BlockPlaceEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.*;

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

    public static final class CommonType<E extends CommonEventWrapper<?>> extends EventType<E> {

        public static CommonType<AttachCapabilitiesEventWrapper<?>> ATTACH_CAPABILITIES = new CommonType<>(false,false);
        public static CommonType<BlockBreakEventWrapper<?,?,?,?,?>> BLOCK_BREAK = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_CREATE_FLUID = new CommonType<>(false,true);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_HARVEST = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_INTERACT = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_NOTIFY_NEIGHBOR = new CommonType<>(true,false);
        public static CommonType<BlockPlaceEventWrapper<?,?,?,?,?>> BLOCK_PLACE = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_PLACE_FLUID = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_PLACE_MULTI = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_SPAWN_PORTAL = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> BLOCK_TRAMPLE_FARMLAND = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> CROP_GROW_POST = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> CROP_GROW_PRE = new CommonType<>(false,true);
        public static CommonType<EntityEnteringChunkEventWrapper<?>> ENTITY_ENTERING_CHUNK = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> ENTITY_JOIN_WORLD = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> ENTITY_SMITTEN = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> EXPLOSION_DETONATE = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> EXPLOSION_START = new CommonType<>(true,false);
        public static CommonType<LivingAttackedEventWrapper<?>> LIVING_ATTACKED = new CommonType<>(true,false);
        public static CommonType<LivingDamageEventWrapper<?>> LIVING_DAMAGE = new CommonType<>(true,false);
        public static CommonType<LivingDeathEventWrapper<?>> LIVING_DEATH = new CommonType<>(true,false);
        public static CommonType<LivingFallEventWrapper<?>> LIVING_FALL = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_HURT = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_JUMP = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_KNOCKBACK = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_LOOTING_LEVEL = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_SET_TARGET = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_UPDATE = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_USE_FINISH = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_ITEM_USE_START = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_ITEM_USE_STOP = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> LIVING_ITEM_USE_TICK = new CommonType<>(true,false);
        public static CommonType<AdvancementEventWrapper<?,?>> PLAYER_ADVANCEMENT = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_BREAK_SPEED = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_CHANGE_GAMEMODE = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_CHANGED_DIMENSIONS = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_CHECK_HARVEST = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_CLONE = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_INTERACT_BLOCK = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_INTERACT_EMPTY = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_INTERACT_ENTITY = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_INTERACT_ENTITY_AT = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_INTERACT_ITEM = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_ITEM_CRAFTED = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_ITEM_PICKUP = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_ITEM_SMELTED = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_LOAD_FROM_FILE = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_LOGGED_IN = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_LOGGED_OUT = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_NAME_FORMAT = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_PUNCH_BLOCK = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_PUNCH_ENTITY = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_PUNCH_ITEM = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_RESPAWN = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_SAVE_TO_FILE = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_SLEEP_IN_BED = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_START_TRACKING = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_STOP_TRACKING = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_TAB_FORMAT = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_VISIBILITY = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_XP_CHANGE = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_XP_PICKUP = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> PLAYER_XP_LEVEL_CHANGE = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> REGISTER_GENERIC = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> TICK_PLAYER = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> TICK_WORLD = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> WORLD_CREATE_SPAWN_POS = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> WORLD_LOAD = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> WORLD_POTENTIAL_SPAWNS = new CommonType<>(true,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> WORLD_SAVE = new CommonType<>(false,false);
        public static CommonType<AttachCapabilitiesEventWrapper<?>> WORLD_UNLOAD = new CommonType<>(false,false);

        private CommonType(boolean cancelable, boolean hasResult) {
            super(cancelable,hasResult);
        }
    }
}