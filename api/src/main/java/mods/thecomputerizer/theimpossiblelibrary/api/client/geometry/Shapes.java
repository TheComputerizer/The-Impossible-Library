package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import org.joml.Vector3d;

import java.util.function.Supplier;

public enum Shapes {

    BOX(() -> new Vector3d[]{new Vector3d(0.5d,-0.5d,-0.5d),new Vector3d(0.5d,0.5d,-0.5d),
            new Vector3d(0.5d,-0.5d,0.5d),new Vector3d(0.5d,0.5d,0.5d),
            new Vector3d(-0.5d,-0.5d,-0.5d),new Vector3d(-0.5d,0.5d,-0.5d),
            new Vector3d(-0.5d,-0.5d,0.5d),new Vector3d(-0.5d,0.5d,0.5d)});

    private final Supplier<Vector3d[]> vectorSupplier;

    Shapes(Supplier<Vector3d[]> vectors) {
        this.vectorSupplier = vectors;
    }

    public Convex3D makeInstance() {
        return new Convex3D(this.vectorSupplier.get());
    }
}
