package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ActionResult;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.Hand;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.WorldAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing;
import mods.thecomputerizer.theimpossiblelibrary.api.common.item.ItemStackAPI;

public abstract class CommonPlayerInteractEventType<E> extends CommonPlayerEventType<E> {

    protected EventFieldWrapper<E,ActionResult> cancelResult;
    protected EventFieldWrapper<E,Facing> facing;
    protected EventFieldWrapper<E,Hand> hand;
    protected EventFieldWrapper<E,BlockPosAPI<?>> pos;

    protected CommonPlayerInteractEventType(CommonType<?> type) {
        super(type);
    }

    public ActionResult getCancelResult() {
        return this.cancelResult.get(this.event);
    }

    public Facing getFacing() {
        return this.facing.get(this.event);
    }

    public Hand getHand() {
        return this.hand.get(this.event);
    }

    public BlockPosAPI<?> getPos() {
        return this.pos.get(this.event);
    }

    protected abstract ItemStackAPI<?> getStackInHand();
    protected abstract WorldAPI<?> getWorld();

    public boolean isFacing(Facing facing) {
        return getFacing()==facing;
    }

    public boolean isHand(Hand hand) {
        return getHand()==hand;
    }

    @Override public void populate() {
        super.populate();
        this.cancelResult = wrapCancelResultField();
        this.facing = wrapFacingField();
        this.hand = wrapHandField();
        this.pos = wrapPosField();
    }

    public void setCancelResult(ActionResult result) {
        this.cancelResult.set(this.event,result);
    }

    protected abstract EventFieldWrapper<E,ActionResult> wrapCancelResultField();
    protected abstract EventFieldWrapper<E,Facing> wrapFacingField();
    protected abstract EventFieldWrapper<E,Hand> wrapHandField();
    protected abstract EventFieldWrapper<E,BlockPosAPI<?>> wrapPosField();
    
}
