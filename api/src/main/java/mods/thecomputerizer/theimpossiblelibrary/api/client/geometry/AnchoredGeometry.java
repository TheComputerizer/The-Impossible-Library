package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import org.joml.Vector3d;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public abstract class AnchoredGeometry {

    private final List<ShapeHolder> freeShapeRenders;
    private final List<Column> columnRenders;
    @Setter private Vector3d renderVec;

    public AnchoredGeometry(Vector3d renderVec) {
        this.freeShapeRenders = new ArrayList<>();
        this.columnRenders = new ArrayList<>();
        this.renderVec = renderVec;
    }

    public void addColumn(Column column) {
        this.columnRenders.add(column);
    }

    public void addFreeShape(ShapeHolder holder) {
        this.freeShapeRenders.add(holder);
    }

    public void setRenderXZ(double x, double z) {
        this.renderVec = new Vector3d(x,this.renderVec.y,z);
    }

    public abstract boolean canRender();

    public abstract Vector3d getRenderPos(float partialTicks);

    public void render(RenderAPI renderer, float partialTicks) {
        if(canRender()) {
            Vector3d renderAt = this.renderVec.sub(getRenderPos(partialTicks));
            Iterator<Column> columnItr = this.columnRenders.iterator();
            while(columnItr.hasNext()) {
                Column column = columnItr.next();
                if(column instanceof ITickableGeometry && !((ITickableGeometry<?>)column).isInitialized())
                    columnItr.remove();
                else column.render(renderer,renderAt);
            }
            Iterator<ShapeHolder> holderItr = this.freeShapeRenders.iterator();
            while(holderItr.hasNext()) {
                ShapeHolder holder = holderItr.next();
                if(holder instanceof ITickableGeometry && !((ITickableGeometry<?>)holder).isInitialized())
                    holderItr.remove();
                else holder.render(renderer,renderAt);
            }
        }
    }

    public boolean isEmpty() {
        return this.columnRenders.isEmpty() && this.freeShapeRenders.isEmpty();
    }
}
