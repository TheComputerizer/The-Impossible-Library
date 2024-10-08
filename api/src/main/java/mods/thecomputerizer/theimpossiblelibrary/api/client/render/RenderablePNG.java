package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.ShapeHelper;

import java.io.IOException;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;
import static mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper.zero3D;

public class RenderablePNG extends Renderable {

    protected final ShapeWidget texture;

    public RenderablePNG(ResourceLocationAPI<?> source, Map<String, Object> parameters) throws IOException {
        super(parameters);
        if(!source.getPath().endsWith(".png"))
            throw new IOException("Tried to initialize a non png file to a png " +
                "object! Make sure that you have the correct file extension on your resource location. ["+source+"]");
        this.texture = ShapeWidget.from(ShapeHelper.plane(Y));
        this.texture.setTexture(new TextureWrapper().setTexture(source).setU(0f,1f).setV(0f,1f));
    }
    
    @Override public void pos(RenderContext ctx) {
        this.texture.setX(getAllignmentX()+getParameterAs("x",0d));
        this.texture.setY(getAllignmentY()+getParameterAs("y",0d));
    }
    
    protected void postRender(RenderAPI renderer) {
        renderer.popMatrix();
    }
    
    protected void preRender(RenderContext ctx, float alpha) {
        ctx.getRenderer().pushMatrix();
        this.texture.getWrapped().getTexture().setAlpha(alpha);
        pos(ctx);
        scale(ctx);
    }
    
    @Override void render(RenderContext ctx) {
        if(canRender()) {
            preRender(ctx,Math.max(0.1f,getOpacity()));
            this.texture.draw(ctx,zero3D(),0d,0d);
            postRender(ctx.getRenderer());
        }
    }
}