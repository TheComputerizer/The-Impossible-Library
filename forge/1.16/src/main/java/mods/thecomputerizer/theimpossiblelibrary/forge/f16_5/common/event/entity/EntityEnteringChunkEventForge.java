package mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.common.event.entity;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.entity.EntityEnteringChunkEventWrapper;
import mods.thecomputerizer.theimpossiblelibrary.forge.f16_5.registry.entity.EntityForge;
import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.EntityEvent.EnteringChunk;

public class EntityEnteringChunkEventForge extends EntityEnteringChunkEventWrapper<Entity> {

    private final EnteringChunk event;

    public EntityEnteringChunkEventForge(EnteringChunk event) {
        super(new EntityForge(event.getEntity()),event.getNewChunkX(),event.getNewChunkZ(),event.getOldChunkX(),
                event.getOldChunkZ());
        this.event = event;
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
