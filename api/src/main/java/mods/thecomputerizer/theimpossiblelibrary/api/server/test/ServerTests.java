package mods.thecomputerizer.theimpossiblelibrary.api.server.test;

import mods.thecomputerizer.theimpossiblelibrary.api.common.test.TestsAPI;
import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

public class ServerTests extends TestsAPI {
    
    public static void runTests(Object ... args) {
        TILRef.logInfo("Initializing server tests");
        new ServerTests().runAndLog(args);
    }
    
    private ServerTests() {
        super("Server");
    }
    
    @Override protected boolean run(Object... args) {
        return true;
    }
}
