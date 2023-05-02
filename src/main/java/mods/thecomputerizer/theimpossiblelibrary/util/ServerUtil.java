package mods.thecomputerizer.theimpossiblelibrary.util;

import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;

public class ServerUtil {

    public static String getWorldName() {
        Side effectiveSide = FMLCommonHandler.instance().getSide();
        FMLCommonHandler.instance().getMinecraftServerInstance();
        return "NYI";
    }
}
