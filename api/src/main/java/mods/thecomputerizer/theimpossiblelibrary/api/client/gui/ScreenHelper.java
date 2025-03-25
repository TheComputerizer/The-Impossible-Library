package mods.thecomputerizer.theimpossiblelibrary.api.client.gui;

import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientHelper;
import mods.thecomputerizer.theimpossiblelibrary.api.client.render.TextureWrapper;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.client.ClientAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector2;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector3;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.Vector4;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorStreams;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier2D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier3D;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.vectors.VectorSuppliers.VectorSupplier4D;
import mods.thecomputerizer.theimpossiblelibrary.api.util.RandomHelper;

import javax.annotation.Nullable;
import java.util.Objects;
import java.util.function.Function;
import java.util.function.Supplier;

import static mods.thecomputerizer.theimpossiblelibrary.api.client.render.ColorHelper.LIGHT_PURPLE;

@SuppressWarnings("unused")
public class ScreenHelper {

    public static @Nullable ScreenHelperAPI getAPI() {
        return TILRef.getClientSubAPI(ClientAPI::getScreenHelper);
    }
    
    public static TextureWrapper getVanillaButtonTexture(boolean hover, boolean disabled) {
        ScreenHelperAPI api = getAPI();
        return Objects.nonNull(api) ? api.getVanillaButtonTexture(hover,disabled) :
                new TextureWrapper().setMask(LIGHT_PURPLE);
    }
    
    public static void open(Function<Integer,ScreenAPI> withGuiScale) {
        final ScreenHelperAPI api = getAPI();
        if(Objects.isNull(api)) {
            TILRef.logError("Failed to open screen with GUI scale function since ScreenHelperAPI is null");
            return;
        }
        ClientHelper.scheduleRunnable(() -> {
            ScreenAPI screen = withGuiScale.apply(ClientHelper.getGuiScale());
            api.open(screen);
        });
    }
    
    public static void open(final ScreenAPI screen) {
        open(i -> screen);
    }
    
    public static void playVanillaClickSound() {
        ScreenHelperAPI api = getAPI();
        if(Objects.nonNull(api)) api.playVanillaClickSound();
    }
    
    public static VectorSupplier2D randomPointSupplier2D(Supplier<Vector2> supplier, int minCount, int maxCount) {
        Vector2[] vectors = new Vector2[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get2D(vectors);
    }
    
    public static VectorSupplier3D randomPointSupplier3D(Supplier<Vector3> supplier, int minCount, int maxCount) {
        Vector3[] vectors = new Vector3[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get3D(vectors);
    }
    
    public static VectorSupplier4D randomPointSupplier4D(Supplier<Vector4> supplier, int minCount, int maxCount) {
        Vector4[] vectors = new Vector4[RandomHelper.randomInt(minCount,maxCount)];
        for(int i=0;i<vectors.length;i++) vectors[i] = supplier.get();
        return VectorStreams.get4D(vectors);
    }
}