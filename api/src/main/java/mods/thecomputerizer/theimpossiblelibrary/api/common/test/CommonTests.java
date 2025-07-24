package mods.thecomputerizer.theimpossiblelibrary.api.common.test;

import mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef;

public class CommonTests extends TestsAPI {
    
    public static void runTests(Object ... args) {
        TILRef.logInfo("Initializing common tests");
        new CommonTests().runAndLog(args);
    }
    
    private CommonTests() {
        super("Common");
    }
    
    @Override protected boolean run(Object... args) {
        return true;
    }
}