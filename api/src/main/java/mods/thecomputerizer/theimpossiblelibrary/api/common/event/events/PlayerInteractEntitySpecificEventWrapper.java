package mods.thecomputerizer.theimpossiblelibrary.api.common.event.events;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.event.types.CommonPlayerInteractEntityEventType;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.CommonEventWrapper.CommonType.PLAYER_INTERACT_ENTITY_AT;

@SuppressWarnings("unused")
public abstract class PlayerInteractEntitySpecificEventWrapper<E> extends CommonPlayerInteractEntityEventType<E> {

    protected EventFieldWrapper<E,Vector3> localPos;

    protected PlayerInteractEntitySpecificEventWrapper() {
        super(PLAYER_INTERACT_ENTITY_AT);
    }

    public Vector3 getLocalPos() {
        return this.localPos.get(this.event);
    }

    @Override public void populate() {
        super.populate();
        this.localPos = wrapLocalPosField();
    }

    protected abstract EventFieldWrapper<E,Vector3> wrapLocalPosField();
}