package mods.thecomputerizer.theimpossiblelibrary.legacy.common;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.*;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.AttachCapabilitiesEventLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.block.BlockBreakEventLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.block.BlockPlaceEventLegacy;
import mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity.*;
import net.minecraftforge.common.MinecraftForge;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.*;

public class EventsLegacy implements EventsAPI {

    private boolean defined;

    public EventsLegacy() {
        this.defined = false;
    }

    @Override
    public void defineEvents() {
        ATTACH_CAPABILITIES.setConnector(new AttachCapabilitiesEventLegacy()); //TODO what
        BLOCK_BREAK.setConnector(new BlockBreakEventLegacy());
        BLOCK_CREATE_FLUID.setConnector(null);
        BLOCK_HARVEST.setConnector(null);
        BLOCK_INTERACT.setConnector(null);
        BLOCK_NOTIFY_NEIGHBOR.setConnector(null);
        BLOCK_PLACE.setConnector(new BlockPlaceEventLegacy());
        BLOCK_PLACE_FLUID.setConnector(null);
        BLOCK_PLACE_MULTI.setConnector(null);
        BLOCK_SPAWN_PORTAL.setConnector(null);
        BLOCK_TRAMPLE_FARMLAND.setConnector(null);
        CROP_GROW_POST.setConnector(null);
        CROP_GROW_PRE.setConnector(null);
        ENTITY_ENTERING_CHUNK.setConnector(new EntityEnteringChunkEventLegacy());
        ENTITY_JOIN_WORLD.setConnector(null);
        ENTITY_SMITTEN.setConnector(null);
        EXPLOSION_DETONATE.setConnector(null);
        EXPLOSION_START.setConnector(null);
        LIVING_ATTACKED.setConnector(new LivingAttackedEventLegacy());
        LIVING_DAMAGE.setConnector(new LivingDamageEventLegacy());
        LIVING_DEATH.setConnector(new LivingDeathEventLegacy());
        LIVING_FALL.setConnector(new LivingFallEventLegacy());
        LIVING_HURT.setConnector(null);
        LIVING_JUMP.setConnector(null);
        LIVING_KNOCKBACK.setConnector(null);
        LIVING_LOOTING_LEVEL.setConnector(null);
        LIVING_SET_TARGET.setConnector(null);
        LIVING_UPDATE.setConnector(null);
        LIVING_USE_FINISH.setConnector(null);
        LIVING_ITEM_USE_START.setConnector(null);
        LIVING_ITEM_USE_STOP.setConnector(null);
        LIVING_ITEM_USE_TICK.setConnector(null);
        PLAYER_ADVANCEMENT.setConnector(new AdvancementEventLegacy());
        PLAYER_BREAK_SPEED.setConnector(null);
        PLAYER_CHANGE_GAMEMODE.setConnector(null);
        PLAYER_CHANGED_DIMENSIONS.setConnector(null);
        PLAYER_CHECK_HARVEST.setConnector(null);
        PLAYER_CLONE.setConnector(null);
        PLAYER_INTERACT_BLOCK.setConnector(null);
        PLAYER_INTERACT_EMPTY.setConnector(null);
        PLAYER_INTERACT_ENTITY.setConnector(null);
        PLAYER_INTERACT_ENTITY_AT.setConnector(null);
        PLAYER_INTERACT_ITEM.setConnector(null);
        PLAYER_ITEM_CRAFTED.setConnector(null);
        PLAYER_ITEM_PICKUP.setConnector(null);
        PLAYER_ITEM_SMELTED.setConnector(null);
        PLAYER_LOAD_FROM_FILE.setConnector(null);
        PLAYER_LOGGED_IN.setConnector(null);
        PLAYER_LOGGED_OUT.setConnector(null);
        PLAYER_NAME_FORMAT.setConnector(null);
        PLAYER_PUNCH_BLOCK.setConnector(null);
        PLAYER_PUNCH_ENTITY.setConnector(null);
        PLAYER_PUNCH_ITEM.setConnector(null);
        PLAYER_RESPAWN.setConnector(null);
        PLAYER_SAVE_TO_FILE.setConnector(null);
        PLAYER_SLEEP_IN_BED.setConnector(null);
        PLAYER_START_TRACKING.setConnector(null);
        PLAYER_STOP_TRACKING.setConnector(null);
        PLAYER_TAB_FORMAT.setConnector(null);
        PLAYER_VISIBILITY.setConnector(null);
        PLAYER_XP_CHANGE.setConnector(null);
        PLAYER_XP_PICKUP.setConnector(null);
        PLAYER_XP_LEVEL_CHANGE.setConnector(null);
        REGISTER_GENERIC.setConnector(null);
        TICK_PLAYER.setConnector(null);
        TICK_WORLD.setConnector(null);
        WORLD_CREATE_SPAWN_POS.setConnector(null);
        WORLD_LOAD.setConnector(null);
        WORLD_POTENTIAL_SPAWNS.setConnector(null);
        WORLD_SAVE.setConnector(null);
        WORLD_UNLOAD.setConnector(null);
        this.defined = true;
    }

    @Override
    public boolean isDefined() {
        return this.defined;
    }

    @Override
    public <E extends EventWrapper<?>> void register(E wrapper) {
        MinecraftForge.EVENT_BUS.register(wrapper.getClass());
    }
}