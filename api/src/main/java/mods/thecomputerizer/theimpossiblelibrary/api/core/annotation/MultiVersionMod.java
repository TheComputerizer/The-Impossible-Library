package mods.thecomputerizer.theimpossiblelibrary.api.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.RUNTIME;

@Retention(RUNTIME) @Target(TYPE)
public @interface MultiVersionMod {

    /**
     * Can be loaded on clients
     */
    boolean client() default true;

    /**
     * Compatible with Fabric
     */
    boolean fabric() default true;

    /**
     * Compatible with Forge
     */
    boolean forge() default true;

    /**
     * Compatible with versions before 1.13
     */
    boolean legacy() default true;
    
    /**
     * A description of the mod
     */
    String modDescription() default "Multiversion mod loaded by The Impossible Library";
    
    /**
     * Lowercase ID of the mod
     */
    String modid();

    /**
     * Human-readable name of the mod
     */
    String modName() default "";

    /**
     * The current version of the mod ideally following semver semantics
     */
    String modVersion() default "";

    /**
     * Compatible with Neoforge
     */
    boolean neoforge() default true;

    /**
     * Can be loaded on dedicated servers
     */
    boolean server() default true;

    /**
     * Compatible with 1.12
     */
    boolean version12() default true;

    /**
     * Compatible with 1.16
     */
    boolean version16() default true;

    /**
     * Compatible with 1.18
     */
    boolean version18() default true;

    /**
     * Compatible with 1.19
     */
    boolean version19() default true;

    /**
     * Compatible with 1.20
     */
    boolean version20() default true;

    /**
     * Compatible with 1.21
     */
    boolean version21() default true;
}