package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.events.EntityEnteringChunkEventWrapper;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

import java.util.function.Function;

public class EntityEnteringChunkEventForge extends EntityEnteringChunkEventWrapper<EnteringChunk> {

    @Override
    protected Function<EnteringChunk,Entity> getEntityFunc() {
        return EnteringChunk::getEntity;
    }

    @Override
    public void populate() {
        super.populate();
        this.newX = this.event.getNewChunkX();
        this.newZ = this.event.getNewChunkZ();
        this.oldX = this.event.getOldChunkX();
        this.oldZ = this.event.getOldChunkZ();
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
