package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.shapes.VectorHelper;
import org.joml.Vector3d;

import java.util.Objects;
import java.util.function.Function;

public abstract class CommandHelperAPI {

    public abstract EntityAPI<?,?> parseEntity(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception;
    public abstract PlayerAPI<?,?> parsePlayer(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception;

    public Vector3d parsePosition(EntityAPI<?,?> reference, String unparsed) {
        Vector3d pos = Objects.nonNull(reference) ? reference.getPosExact() : VectorHelper.zero3D();
        String[] split = unparsed.split(" ",2);
        double x = split.length>0 ? parseDoubleRef(split[0],pos,Vector3d::x) : 0d;
        double y = split.length>1 ? parseDoubleRef(split[1],pos,Vector3d::y) : 0d;
        double z = split.length>2 ? parseDoubleRef(split[2],pos,Vector3d::z) : 0d;
        return new Vector3d(x,y,z);
    }

    public double parseDoubleRef(String unparsed, Vector3d pos, Function<Vector3d,Double> posFunc) {
        boolean isRef = unparsed.contains("~");
        double parsed = Double.parseDouble(unparsed.replace('~','\0'));
        return parsed+(isRef ? posFunc.apply(pos) : 0);
    }
}
