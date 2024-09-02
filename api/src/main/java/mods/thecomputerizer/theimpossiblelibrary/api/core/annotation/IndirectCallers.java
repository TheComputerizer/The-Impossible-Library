package mods.thecomputerizer.theimpossiblelibrary.api.core.annotation;

import java.lang.annotation.Retention;
import java.lang.annotation.Target;

import static java.lang.annotation.ElementType.FIELD;
import static java.lang.annotation.ElementType.METHOD;
import static java.lang.annotation.ElementType.TYPE;
import static java.lang.annotation.RetentionPolicy.CLASS;

/**
 * A simple annotation for silencing unused warnings in classes, methods, and fields that are only called via ASM,
 * reflection, or any other type of indirect invocation strategy
 */
@Retention(CLASS) @Target({FIELD,METHOD,TYPE})
public @interface IndirectCallers {}