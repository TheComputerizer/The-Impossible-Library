package mods.thecomputerizer.theimpossiblelibrary.legacy.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.legacy.registry.entity.EntityLegacy;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;
import net.minecraftforge.event.entity.player.AdvancementEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.ENTITY_ENTERING_CHUNK;
import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_ADVANCEMENT;

public class EntityEnteringChunkEventLegacy extends EntityEnteringChunkEventWrapper<EnteringChunk> {

    @SubscribeEvent
    public static void onEvent(EnteringChunk event) {
        ENTITY_ENTERING_CHUNK.invoke(event);
    }

    private EnteringChunk event;

    public EntityEnteringChunkEventLegacy() {}

    public void setEvent(EnteringChunk event) {
        this.event = event;
        setEntity(new EntityLegacy(event.getEntity()));
        this.newX = event.getNewChunkX();
        this.newZ = event.getNewChunkZ();
        this.oldX = event.getOldChunkX();
        this.oldZ = event.getOldChunkZ();
    }

    @Override
    public void setNewX(int x) {
        this.newX = x;
        this.event.setNewChunkX(x);
    }

    @Override
    public void setNewZ(int z) {
        this.newZ = z;
        this.event.setNewChunkZ(z);
    }

    @Override
    public void setOldX(int x) {
        this.oldX = x;
        this.event.setOldChunkX(x);
    }

    @Override
    public void setOldZ(int z) {
        this.oldZ = z;
        this.event.setOldChunkZ(z);
    }
}
