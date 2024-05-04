package mods.thecomputerizer.theimpossiblelibrary.api.util;

import java.util.function.BiFunction;

/**
 Potentially useful for passing into methods
 */
public enum Matching {
    
    CONTAINS(String::contains),
    ENDS_WITH(String::endsWith),
    EQUALS(String::equals),
    MATCHES(String::matches),
    STARTS_WITH(String::startsWith);
    
    final BiFunction<String,String,Boolean> matcher;
    
    Matching(BiFunction<String,String,Boolean> matcher) {
        this.matcher = matcher;
    }
    
    public boolean matches(String value, String checkThis) {
        return this.matcher.apply(value,checkThis);
    }
}