package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion;
import mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.ModLoader;
import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.core.asm.ASMRef;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.CoreAPI.GameVersion.*;

/**
 * Passthrough and helper fields for CoreAPI accessors pertaining to the current JVM and Minecraft environment state
 */
public interface CoreStateAccessor {
    
    /**
     * Why isn't there a default array like this anywhere?
     */
    boolean[] BOOLEAN_VALUES = new boolean[]{true,false};
    /**
     * Is this a Fabric environment?
     */
    @IndirectCallers boolean FABRIC = CoreAPI.isFabric();
    /**
     * Is this a modern Forge environment? (1.13+)
     */
    @IndirectCallers boolean FORGE = CoreAPI.isForge();
    /**
     * Is this a modern Forge environment? (1.13+)
     */
    @IndirectCallers boolean FORGE_OR_NEOFORGE = FORGE || CoreAPI.isNeoforge();
    /**
     * Is the environment running in Java 8?
     */
    @IndirectCallers boolean JAVA_8 = CoreAPI.isJava8();
    /**
     * Is the environment running in Java 17?
     */
    @IndirectCallers boolean JAVA_17 = CoreAPI.isJava17();
    /**
     * Is the environment running in Java 21?
     */
    @IndirectCallers boolean JAVA_21 = CoreAPI.isJava21();
    /**
     * Is the environment running in Java version with the Module system? (Java 9+)
     */
    @IndirectCallers boolean JAVA_MODULES = !JAVA_8;
    /**
     * Is this a legacy Forge environment? (pre 1.13)
     */
    @IndirectCallers boolean LEGACY = CoreAPI.isLegacy();
    /**
     * Does this environment use named classes and methods?
     */
    @IndirectCallers boolean NAMED_ENV = CoreAPI.isNamedEnv();
    /**
     * Is this a Neoforge environment
     */
    @IndirectCallers boolean NEOFORGE = CoreAPI.isNeoforge();
    /**
     * Does this environment use SRG mappings? (Forge/Legacy/Neoforge)
     */
    @IndirectCallers boolean SRG_ENV = CoreAPI.isSrgEnv();
    /**
     * Is this Minecraft 1.12.2?
     */
    @IndirectCallers boolean V12 = CoreAPI.isV12();
    /**
     * Is this Minecraft 1.16.5?
     */
    @IndirectCallers boolean V16 = CoreAPI.isV16();
    /**
     * Is the Minecraft version at most 1.16.5?
     */
    @IndirectCallers boolean V16_OR_EARLIER = CoreAPI.isVersionAtMost(V16_5);
    /**
     * Is the Minecraft version at least 1.16.5?
     */
    @IndirectCallers boolean V16_OR_LATER = CoreAPI.isVersionAtLeast(V16_5);
    /**
     * Is this Minecraft 1.18.2?
     */
    @IndirectCallers boolean V18 = CoreAPI.isV18();
    /**
     * Is the Minecraft version at most 1.18.2?
     */
    @IndirectCallers boolean V18_OR_EARLIER = CoreAPI.isVersionAtMost(V18_2);
    /**
     * Is the Minecraft version at least 1.18.2?
     */
    @IndirectCallers boolean V18_OR_LATER = CoreAPI.isVersionAtLeast(V18_2);
    /**
     * Is this Minecraft 1.19? (either 1.19.2 or 1.19.4)
     */
    @IndirectCallers boolean V19 = CoreAPI.isV19();
    /**
     * Is the Minecraft version at most 1.19.4?
     */
    @IndirectCallers boolean V19_OR_EARLIER = CoreAPI.isVersionAtMost(V19_4);
    /**
     * Is the Minecraft version at least 1.19.2?
     */
    @IndirectCallers boolean V19_OR_LATER = CoreAPI.isVersionAtLeast(V19_2);
    /**
     * Is the Minecraft version at most 1.19.2?
     */
    @IndirectCallers boolean V19_2_OR_EARLIER = CoreAPI.isVersionAtMost(V19_2);
    /**
     * Is the Minecraft version at least 1.19.4?
     */
    @IndirectCallers boolean V19_4_OR_LATER = CoreAPI.isVersionAtLeast(V19_4);
    /**
     * Is this Minecraft 1.20? (either 1.20.1, 1.20.4 or 1.20.6)
     */
    @IndirectCallers boolean V20 = CoreAPI.isV20();
    /**
     * Is the Minecraft version at most 1.20.6?
     */
    @IndirectCallers boolean V20_OR_EARLIER = CoreAPI.isVersionAtMost(V20_6);
    /**
     * Is the Minecraft version at least 1.20.1?
     */
    @IndirectCallers boolean V20_OR_LATER = CoreAPI.isVersionAtLeast(V20_1);
    /**
     * Is the Minecraft version at most 1.20.4?
     */
    @IndirectCallers boolean V20_4_OR_EARLIER = CoreAPI.isVersionAtMost(V20_4);
    /**
     * Is the Minecraft version at least 1.20.6?
     */
    @IndirectCallers boolean V20_6_OR_LATER = CoreAPI.isVersionAtLeast(V20_6);
    /**
     * Is this Minecraft 1.21? (specifically 1.21.1)
     */
    @IndirectCallers boolean V21 = CoreAPI.isV21();
    /**
     * Is the Minecraft version at least 1.21.1?
     */
    @IndirectCallers boolean V21_OR_LATER = CoreAPI.isVersionAtLeast(V21_1);
    /**
     * Java version int to use for writing classes via ASM
     */
    @IndirectCallers int JAVA_VERSION_ASM = JAVA_8 ? ASMRef.JAVA8 : (JAVA_21 ? ASMRef.JAVA21 : ASMRef.JAVA17);
    /**
     * Major Java version where 17 is returned if the CoreAPI fails to parse it
     */
    @IndirectCallers int JAVA_VERSION = CoreAPI.javaVersion();
    /**
     * The GameVersion enum representing the current Minecraft version
     */
    @IndirectCallers GameVersion GAME_VERSION = CoreAPI.gameVersion();
    /**
     * The ModLoader enum representing the current mod loader.
     * Current supported mod loaders are Fabric, Forge (forge for 1.13+), Legacy (forge pre 1.13), and Neoforge
     */
    @IndirectCallers ModLoader MOD_LOADER = CoreAPI.getInstanceModLoader();
}