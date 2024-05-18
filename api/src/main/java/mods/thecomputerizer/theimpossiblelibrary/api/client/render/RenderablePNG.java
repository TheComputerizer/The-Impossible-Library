package mods.thecomputerizer.theimpossiblelibrary.api.client.render;

import mods.thecomputerizer.theimpossiblelibrary.api.client.gui.widget.ShapeWidget;
import mods.thecomputerizer.theimpossiblelibrary.api.resource.ResourceLocationAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.Plane;
import mods.thecomputerizer.theimpossiblelibrary.api.util.VectorHelper;

import java.io.IOException;
import java.util.Map;

import static mods.thecomputerizer.theimpossiblelibrary.api.common.block.Facing.Axis.Y;

public class RenderablePNG extends Renderable {

    protected final ShapeWidget texture;

    public RenderablePNG(ResourceLocationAPI<?> source, Map<String, Object> parameters) throws IOException {
        super(parameters);
        if(!source.getPath().endsWith(".png"))
            throw new IOException("Tried to initialize a non png file to a png " +
                "object! Make sure that you have the correct file extension on your resource location. ["+source+"]");
        this.texture = new ShapeWidget(new RenderShape(Plane.getFromAxis(Y,2d,2d)),0d,0d);
        this.texture.setTexture(new TextureWrapper().setTexture(source).setU(0f,1f).setV(0f,1f));
    }

    protected void preRender(RenderContext ctx, float alpha) {
        RenderAPI renderer = ctx.getRenderer();
        renderer.pushMatrix();
        renderer.enableBlend();
        renderer.defaultBlendFunc();
        renderer.disableAlpha();
        renderer.setColor(1f,1f,1f,alpha);
        pos(ctx);
        //scale(ctx);
    }
    
    @Override public void pos(RenderContext ctx) {
        this.texture.setX(getAllignmentX()+getParameterAs("x",0d));
        this.texture.setY(getAllignmentY()+getParameterAs("y",0d));
    }
    
    @Override
    void render(RenderContext ctx) {
        if(canRender()) {
            preRender(ctx,Math.max(0.1f,getOpacity()));
            this.texture.draw(ctx,VectorHelper.ZERO_3D,0d,0d);
            postRender(ctx.getRenderer());
        }
    }

    protected void postRender(RenderAPI renderer) {
        renderer.popMatrix();
    }
}
