package mods.thecomputerizer.theimpossiblelibrary.api.core;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import org.apache.logging.log4j.Logger;

import java.lang.management.ManagementFactory;
import java.util.List;

import static mods.thecomputerizer.theimpossiblelibrary.api.core.TILRef.NAME;

/**
 * Java version and JVM flag helpers
 */
public class JVMHelper {
    
    /**
     * List of flags used for the current JVM runtime
     */
    static final List<String> JVM_FLAGS = ManagementFactory.getRuntimeMXBean().getInputArguments();
    /**
     * Is the JVM flag '-XX:+UseCompactObjectHeaders' in use (Java 24+)
     */
    static final boolean COMPACT_OBJECT_HEADERS = JVM_FLAGS.contains("UseCompactObjectHeaders");
    /**
     * Fallback version for when the Java version fails to parse
     */
    static final int FALLBACK_JAVA_VERSION = 17;
    /**
     * Java version property cache
     */
    static final String JAVA_VERSION = System.getProperty("java.version");
    /**
     * Maximum supported Java version by the library
     */
    static final int MAX_JAVA_VERSION = 25;
    static final Logger LOGGER = TILRef.createLogger("TIL JVMHelper");
    /**
     * Major Java version parsed from the Java version property
     */
    static int javaVersionCache;
    
    /**
     * Should native access be attempted within the current JVM runtime
     */
    public static boolean hasNativeAccess() {
        return isVersionLessThan(25) && !COMPACT_OBJECT_HEADERS;
    }
    
    /**
     * Returns true if the Java version is equal to 8.
     * Java 8 is used in versions up to 1.16.5 except cleanroom for 1.12.2.
     */
    public static boolean isJava8() {
        return isJava8(true);
    }
    
    /**
     * Returns true if the Java version is equal to or at least 8 depending on the strict flag.
     * Java 8 is used in versions up to 1.16.5 except cleanroom for 1.12.2.
     */
    public static boolean isJava8(boolean strict) {
        return strict ? isVersion(8) : isVersionAtLeast(8);
    }
    
    /**
     * Returns true if the Java version is at least 17
     * Java 17 is used from 1.18.2 to 1.20.4
     */
    public static boolean isJava17() {
        return isJava17(false);
    }
    
    /**
     * Returns true if the Java version is equal to or at least 17 depending on the strict flag.
     * Java 17 is used from 1.18.2 to 1.20.4
     */
    public static boolean isJava17(boolean strict) {
        return strict ? isVersion(17) : isVersionAtLeast(17);
    }
    
    /**
     * Returns true if the Java version is at least 21.
     * Java 21+ is used in 1.20.6+ as well as cleanroom for 1.12.2
     */
    public static boolean isJava21() {
        return isJava21(false);
    }
    
    /**
     * Returns true if the Java version is equal to or at least 21 depending on the strict flag.
     * Java 21+ is used in 1.20.6+ as well as cleanroom for 1.12.2
     */
    public static boolean isJava21(boolean strict) {
        return strict ? isVersion(21) : isVersionAtLeast(21);
    }
    
    /**
     * Returns true if the Java version is at least 25.
     * Java 25 restricts native access so some intialization stuff needs to be handled differently
     * Java 25+ can be used in cleanroom for 1.12.2
     */
    @IndirectCallers
    public static boolean isJava25() {
        return isJava25(false);
    }
    
    /**
     * Returns true if the Java version is equal to or at least 25 depending on the strict flag.
     * Java 25 restricts native access so some intialization stuff needs to be handled differently
     * Java 25+ can be used in cleanroom for 1.12.2
     */
    public static boolean isJava25(boolean strict) {
        return strict ? isVersion(25) : isVersionAtLeast(25);
    }
    
    /**
     * Returns true if the current Java version is equal to the input version
     */
    public static boolean isVersion(int version) {
        return version()==version;
    }
    
    /**
     * Returns true if the current Java version is at least the input version
     */
    public static boolean isVersionAtLeast(int version) {
        return version()>=version;
    }
    
    /**
     * Returns true if the current Java version is at most the input version
     */
    @IndirectCallers
    public static boolean isVersionAtMost(int version) {
        return version()<=version;
    }
    
    /**
     * Returns true if the current Java version is greater than the input version
     */
    @IndirectCallers
    public static boolean isVersionGreaterThan(int version) {
        return version()>version;
    }
    
    /**
     * Returns true if the current Java version is less than input version
     */
    public static boolean isVersionLessThan(int version) {
        return version()<version;
    }
    
    /**
     * Should return 8, 17, 21, 25, etc.
     * Java versions before 9 use 1.x numbering, but since nobody is going to use Java 7 or below it is assumed to be 8.
     * If the Java version fails to parse for whatever reason, FALLBACK_JAVA_VERSION will be returned.
     */
    public static int version() {
        if(javaVersionCache>0) return javaVersionCache;
        javaVersionCache = FALLBACK_JAVA_VERSION;
        LOGGER.info("Parsing Java version from {}",JAVA_VERSION);
        if(JAVA_VERSION.startsWith("1.")) javaVersionCache = 8;
        else {
            String majorVersion = JAVA_VERSION.split("\\.")[0].split("_")[0];
            try {
                javaVersionCache = Integer.parseInt(majorVersion);
            } catch(Exception ex) {
                LOGGER.error("Failed to parse Java version from {} (split={})",majorVersion,JAVA_VERSION,ex);
            }
        }
        if(javaVersionCache>MAX_JAVA_VERSION)
            throw new RuntimeException(NAME+" is not yet compatible with Java "+javaVersionCache+"! Please ensure "+
                                       "that you are using Java "+MAX_JAVA_VERSION+" or earlier.");
        return javaVersionCache;
    }
}