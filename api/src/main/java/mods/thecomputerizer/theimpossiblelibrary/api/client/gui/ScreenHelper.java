package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier4D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;
import org.joml.Vector2d;
import org.joml.Vector3d;
import org.joml.Vector4d;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Supplier;

public class ScreenHelper {

    public static @Nullable ScreenHelperAPI getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getScreenHelper);
    }
    
    public static void open(ScreenAPI screen) {
        ScreenHelperAPI api = getAPI();
        if(Objects.nonNull(api)) api.open(screen);
    }
    
    public static VectorSupplier2D randomPointSupplier2D(Supplier<Vector2d> supplier, int minCount, int maxCount) {
        Vector2d[] vectors = new Vector2d[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get2D(vectors);
    }
    
    public static VectorSupplier3D randomPointSupplier3D(Supplier<Vector3d> supplier, int minCount, int maxCount) {
        Vector3d[] vectors = new Vector3d[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get3D(vectors);
    }
    
    public static VectorSupplier4D randomPointSupplier4D(Supplier<Vector4d> supplier, int minCount, int maxCount) {
        Vector4d[] vectors = new Vector4d[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get4D(vectors);
    }
}