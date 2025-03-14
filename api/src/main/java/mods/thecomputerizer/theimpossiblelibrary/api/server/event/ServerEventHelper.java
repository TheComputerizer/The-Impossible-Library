package mods.thecomputerizer.theimpossiblelibrary.api.server.event;

import mods.thecomputerizer.theimpossiblelibrary.api.common.CommonAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

@SuppressWarnings("unused") public class ServerEventHelper {
    
    public static ServerEventsAPI getEventsAPI() {
        return TILRef.getCommonSubAPI(CommonAPI::getServerEvents);
    }
    
    public static void initTILServerListeners(boolean test) {
    
    }
}
