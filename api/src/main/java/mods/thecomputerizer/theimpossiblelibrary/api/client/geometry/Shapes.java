package mods.thecomputerizer.theimpossiblelibrary.api.client.geometry;

import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;

import java.util.function.Supplier;

public enum Shapes {

    BOX(() -> new Vector3[]{new Vector3(0.5d, -0.5d, -0.5d),new Vector3(0.5d, 0.5d, -0.5d),
            new Vector3(0.5d,-0.5d,0.5d),new Vector3(0.5d,0.5d,0.5d),
            new Vector3(-0.5d,-0.5d,-0.5d),new Vector3(-0.5d,0.5d,-0.5d),
            new Vector3(-0.5d,-0.5d,0.5d),new Vector3(-0.5d,0.5d,0.5d)});

    private final Supplier<Vector3[]> vectorSupplier;

    Shapes(Supplier<Vector3[]> vectors) {
        this.vectorSupplier = vectors;
    }

    public Convex3D makeInstance() {
        return new Convex3D(this.vectorSupplier.get());
    }
}
