package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.*;

@SuppressWarnings("unused")
public interface CoreStateAccessor {
    
    boolean FABRIC = CoreAPI.isFabric();
    boolean FORGE = CoreAPI.isForge();
    boolean FORGE_OR_NEOFORGE = FORGE || CoreAPI.isNeoforge();
    boolean JAVA_8 = CoreAPI.isJava8();
    boolean JAVA_17 = CoreAPI.isJava17();
    boolean JAVA_21 = CoreAPI.isJava21();
    boolean JAVA_MODULES = !JAVA_8;
    boolean LEGACY = CoreAPI.isLegacy();
    boolean NAMED_ENV = CoreAPI.isNamedEnv();
    boolean NEOFORGE = CoreAPI.isNeoforge();
    boolean SRG_ENV = CoreAPI.isSrgEnv();
    boolean V12 = CoreAPI.isV12();
    boolean V16 = CoreAPI.isV16();
    boolean V16_OR_EARLIER = CoreAPI.isVersionAtMost(V16_5);
    boolean V16_OR_LATER = CoreAPI.isVersionAtLeast(V16_5);
    boolean V18 = CoreAPI.isV18();
    boolean V18_OR_EARLIER = CoreAPI.isVersionAtMost(V18_2);
    boolean V18_OR_LATER = CoreAPI.isVersionAtLeast(V18_2);
    boolean V19 = CoreAPI.isV19();
    boolean V19_OR_EARLIER = CoreAPI.isVersionAtMost(V19_4);
    boolean V19_OR_LATER = CoreAPI.isVersionAtLeast(V19_2);
    boolean V19_2_OR_EARLIER = CoreAPI.isVersionAtMost(V19_2);
    boolean V19_4_OR_LATER = CoreAPI.isVersionAtLeast(V19_4);
    boolean V20 = CoreAPI.isV20();
    boolean V20_OR_LATER = CoreAPI.isVersionAtLeast(V20_1);
    boolean V20_4_OR_EARLIER = CoreAPI.isVersionAtMost(V20_4);
    boolean V20_6_OR_LATER = CoreAPI.isVersionAtLeast(V20_6);
    boolean V21 = CoreAPI.isV21();
    boolean V21_OR_LATER = CoreAPI.isVersionAtLeast(V21_1);
    int JAVA_VERSION = CoreAPI.javaVersion(); //Returns 17 if it fails to parse
    GameVersion GAME_VERSION = CoreAPI.gameVersion();
    ModLoader MOD_LOADER = CoreAPI.getInstanceModLoader();
}