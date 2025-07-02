package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.LivingEntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;

public abstract class CommonPlayerEventType<E> extends CommonLivingEventType<E> {

    protected EventFieldWrapper<E,PlayerAPI<?,?>> player;

    protected CommonPlayerEventType(CommonType<?> type) {
        super(type);
    }
    
    public EntityAPI<?,?> getEntity() {
        return getPlayer();
    }

    public PlayerAPI<?,?> getPlayer() {
        return this.player.get(this.event);
    }

    @Override public void populate() {
        this.player = wrapPlayerField();
    }
    
    protected final EventFieldWrapper<E,LivingEntityAPI<?,?>> wrapLivingField() {
        return null;
    }

    protected abstract EventFieldWrapper<E,PlayerAPI<?,?>> wrapPlayerField();
}
