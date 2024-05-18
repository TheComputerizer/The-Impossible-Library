package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import lombok.Getter;
import lombok.Setter;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.RenderContext;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.VertexWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.VectorHelper;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.Random;

@SuppressWarnings("unused")
public class Convex3D {

    @Getter private final double radius;
    private final TriangleMapper[] triangles;
    private final float[] color = new float[]{1f,1f,1f,1f,0f,0f,0f,0f};
    private final float[] scale = new float[]{1f,1f,1f};
    private final double[] translationOffset = new double[]{0d,0d,0d};
    private final double[] rotationSpeed = new double[]{0d,0d,0d};
    private final float[] currentRotation = new float[]{0f,0f,0f};
    private boolean showOutlines = true;
    @Setter private boolean enableCull = false;
    @Setter private boolean pushMatrix = true;
    private Vector3d previousRenderPos;
    private Vector3d orbitVec;
    private Orbit orbit;

    public Convex3D(Vector3d ... relativeCoords) {
        if(Objects.isNull(relativeCoords) || relativeCoords.length<=3)
            throw new RuntimeException("Only convex polygons with more than 3 vertices are supported for Convex3D objects");
        this.radius = relativeCoords[0].distance(VectorHelper.zero3D());
        this.triangles = new TriangleMapper[relativeCoords.length];
        for(int i=0; i<relativeCoords.length; i++)
            this.triangles[i] = new TriangleMapper(relativeCoords[i],relativeCoords);
    }

    public Convex3D(Convex3D copy) {
        this.radius = copy.radius;
        this.triangles = copy.triangles;
        setColor(copy.color);
        setScale(copy.scale);
        setTranslationOffset(copy.translationOffset);
        setRotationSpeed(copy.rotationSpeed);
        this.currentRotation[0] = copy.currentRotation[0];
        this.currentRotation[1] = copy.currentRotation[1];
        this.currentRotation[2] = copy.currentRotation[2];
        this.showOutlines = copy.showOutlines;
        this.enableCull = copy.enableCull;
        this.pushMatrix = copy.pushMatrix;
        this.previousRenderPos = copy.previousRenderPos;
        this.orbitVec = copy.orbitVec;
        this.orbit = copy.orbit;
    }

    public double getScaledHeight() {
        return this.radius/this.scale[1];
    }

    public void setColor(float ... newColor) {
        for(int i=0; i<4; i++) {
            this.color[i] = newColor.length>i ? newColor[i] : 1f;
            int oi = i+4; //outline index
            this.color[oi] = newColor.length>oi ? newColor[oi] : (i<3 ? 1f-this.color[i] : this.color[i]);
        }
    }

    public void setScale(float ... newScale) {
        for(int i=0; i<this.scale.length; i++)
            this.scale[i] = newScale.length>i ? newScale[i] : 1f;
    }

    public void setRotationSpeed(double ... newSpeed) {
        for(int i=0; i<this.rotationSpeed.length; i++)
            this.rotationSpeed[i] = newSpeed.length>i ? newSpeed[i] : 0d;
    }

    public void setRandomRotations(Random random, double speedFactor) {
        setRotationSpeed(random.nextDouble()*speedFactor,random.nextDouble()*speedFactor,random.nextDouble()*speedFactor);
    }

    public void setTranslationOffset(double ... newOffset) {
        for(int i=0; i<this.translationOffset.length; i++)
            this.translationOffset[i] = newOffset.length>i ? newOffset[i] : 0d;
    }

    public void setOrbit(double radius, double speed, double angle) {
        this.orbit = new Orbit(radius,speed,angle);
    }

    public void setRandomTranslationOffset(Random random, double range) {
        setTranslationOffset(randomOffset(random,range),randomOffset(random,range),randomOffset(random,range));
    }

    private double randomOffset(Random random,double range) {
        return (-range/2d)+(random.nextDouble()*range);
    }

    public void setEnableOutline(boolean showOutlines) {
        this.showOutlines = showOutlines;
    }

    private void preRender(RenderAPI renderer) {
        if(this.pushMatrix) renderer.pushMatrix();
        renderer.enableBlend();
        renderer.disableTexture();
        renderer.defaultBlendFunc();
        if(!this.enableCull) renderer.depthMask(false);
        renderer.alphaFuncGreater(0.003921569f);
        renderer.disableCull();
        renderer.disableLighting();
    }

    private void postRender(RenderAPI renderer) {
        renderer.enableTexture();
        renderer.disableBlend();
        renderer.enableLighting();
        renderer.enableCull();
        if(!this.enableCull) renderer.depthMask(true);
        renderer.alphaFuncGreater(0.1f);
        if(this.pushMatrix) renderer.popMatrix();
    }

    public void render(RenderContext ctx, double x, double y, double z) {
        render(ctx,new Vector3d(x,y,z));
    }

    public void render(RenderContext ctx, Vector3d pos) {
        RenderAPI renderer = ctx.getRenderer();
        preRender(renderer);
        renderer.setColor(this.color[0],this.color[1],this.color[2],this.color[3]);
        renderer.scale(this.scale[0],this.scale[1],this.scale[2]);
        setTranslation(renderer,new Vector3d(pos.x/this.scale[0],pos.y/this.scale[1],pos.z/this.scale[2]));
        for(int i = 0; i < this.currentRotation.length; i++)
            this.currentRotation[i] = rotateClampedAxis(i);
        renderer.rotate(this.currentRotation[0],1f,0f,0f);
        renderer.rotate(this.currentRotation[1],0f,1f,0f);
        renderer.rotate(this.currentRotation[2],0f,0f,1f);
        for(TriangleMapper triangle : this.triangles)
            renderTriangle(renderer,triangle);
        if(this.showOutlines) renderOutlines(ctx);
        postRender(renderer);
    }

    private void setTranslation(RenderAPI renderer, Vector3d initialPos) {
        if(Objects.isNull(this.previousRenderPos)) this.previousRenderPos = initialPos;
        if(Objects.isNull(this.orbit)) renderer.translate(initialPos.x+this.translationOffset[0],
                initialPos.y+this.translationOffset[1],initialPos.z+this.translationOffset[2]);
        else {
            if(Objects.isNull(this.orbitVec))
                this.orbitVec = new Vector3d(this.translationOffset[0],this.translationOffset[1],this.translationOffset[2]);
            else if(!initialPos.equals(this.previousRenderPos)) {
                double distance = initialPos.distance(this.previousRenderPos);
                this.orbitVec = this.orbitVec.add(initialPos.sub(this.previousRenderPos).normalize().mul(distance));
            }
            this.orbitVec = this.orbit.getNextVec(this.orbitVec,initialPos);
            renderer.translate(this.orbitVec.x,this.orbitVec.y,this.orbitVec.z);
        }
        this.previousRenderPos = initialPos;
    }

    private float rotateClampedAxis(int index) {
        float current = this.currentRotation[index];
        double adjustedSpeed = this.rotationSpeed[index];
        current = current+(float)adjustedSpeed;
        while(current>360f) current-=360f;
        return Math.max(current,0f);
    }

    public void renderTriangle(RenderAPI renderer, TriangleMapper triangle) {
        VertexWrapper buffer = renderer.getBufferBuilderPC(renderer.getGLAPI().triangleFan(),3);
        buffer.start();
        for(int i=0; i<triangle.length; i++) {
            bufferVertex(buffer,triangle.getOriginal());
            bufferVertex(buffer,triangle.getA(i));
            bufferVertex(buffer,triangle.getB(i));
        }
        buffer.finish();
    }

    public void renderOutlines(RenderContext ctx) {
        ctx.getRenderer().setColor(this.color[4],this.color[5],this.color[6],this.color[7]);
        for(TriangleMapper triangle : this.triangles)
            for(int i=0; i<triangle.length; i++)
                renderTriangleOutline(ctx,triangle,i);
    }

    public void renderTriangleOutline(RenderContext ctx, TriangleMapper triangle, int index) {
        Vector3d og = triangle.getOriginal();
        Vector3d a = triangle.getA(index);
        Vector3d b = triangle.getB(index);
        ctx.drawLine(og,a,1d);
        ctx.drawLine(og,b,1d);
        ctx.drawLine(a,b,1d);
    }

    private void bufferVertex(VertexWrapper buffer, Vector3d vec) {
        buffer.pos(vec.x,vec.y,vec.z).color(this.color[0],this.color[1],this.color[2],this.color[3]).endVertex();
    }
}