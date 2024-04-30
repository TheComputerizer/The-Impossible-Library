package mods.thecomputerizer.theimpossiblelibrary.api.server;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.EntityAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.common.entity.PlayerAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;
import mods.thecomputerizer.theimpossiblelibrary.api.world.BlockPosAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.world.PosHelper;
import org.joml.Vector3d;

public class CommandHelper {

    public static CommandHelperAPI getAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getCommandHelper);
    }

    public static BlockPosAPI<?> parseBlockPos(EntityAPI<?,?> reference, String unparsed) {
        return PosHelper.getPos(parsePos(reference,unparsed));
    }

    public static EntityAPI<?,?> parseEntity(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        return getAPI().parseEntity(server,sender,unparsed);
    }

    public static PlayerAPI<?,?> parsePlayer(MinecraftServerAPI<?> server, CommandSenderAPI<?> sender, String unparsed) throws Exception {
        return getAPI().parsePlayer(server,sender,unparsed);
    }

    public static Vector3d parsePos(EntityAPI<?,?> reference, String unparsed) {
        return getAPI().parsePosition(reference,unparsed);
    }
}