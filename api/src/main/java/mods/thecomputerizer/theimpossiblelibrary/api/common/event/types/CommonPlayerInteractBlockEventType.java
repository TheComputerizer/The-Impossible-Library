package mods.thecomputerizer.theimpossiblelibrary.api.common.event.types;

import mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventFieldWrapper;
import org.joml.Vector3d;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.event.EventWrapper.Result.DENY;

public abstract class CommonPlayerInteractBlockEventType<E> extends CommonPlayerInteractEventType<E> {

    protected EventFieldWrapper<E,Result> blockResult;
    protected EventFieldWrapper<E, Vector3d> hitVec;
    protected EventFieldWrapper<E,Result> itemResult;

    protected CommonPlayerInteractBlockEventType(CommonType<?> type) {
        super(type);
    }

    public Result getBlockResult() {
        return this.blockResult.get(this.event);
    }

    public Vector3d getHitVec() {
        return this.hitVec.get(this.event);
    }

    public Result getItemResult() {
        return this.itemResult.get(this.event);
    }

    @Override
    public void populate() {
        super.populate();
        this.blockResult = wrapBlockResultField();
        this.hitVec = wrapHitVecField();
        this.itemResult = wrapItemResultField();
    }

    public void setBlockResult(Result result) {
        this.blockResult.set(this.event,isCanceled() ? DENY : result);
    }

    @Override
    public void setCanceled(boolean canceled) {
        super.setCanceled(canceled);
        if(isCanceled()) {
            setBlockResult(DENY);
            setItemResult(DENY);
        }
    }

    public void setItemResult(Result result) {
        this.itemResult.set(this.event,isCanceled() ? DENY : result);
    }

    protected abstract EventFieldWrapper<E,Result> wrapBlockResultField();
    protected abstract EventFieldWrapper<E,Vector3d> wrapHitVecField();
    protected abstract EventFieldWrapper<E,Result> wrapItemResultField();
}
