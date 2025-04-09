package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;
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
    
    public static boolean matchesAll(String value, String checkThis, Matching ... matchers) {
        for(Matching matcher : matchers)
            if(!matcher.matches(value,checkThis)) return false;
        return true;
    }
    
    public static boolean matchesAny(String value, String checkThis, Matching ... matchers) {
        for(Matching matcher : matchers)
            if(matcher.matches(value,checkThis)) return true;
        return false;
    }
    
    @IndirectCallers
    public static Set<String> matchingValuesAll(Collection<String> values, String checkThis, Matching ... matchers) {
        Set<String> matches = new HashSet<>();
        for(String value : values)
            if(matchesAll(value,checkThis,matchers)) matches.add(value);
        return matches;
    }
    
    public static Set<String> matchingValuesAny(Collection<String> values, String checkThis, Matching ... matchers) {
        Set<String> matches = new HashSet<>();
        for(String value : values)
            if(matchesAny(value,checkThis,matchers)) matches.add(value);
        return matches;
    }
    
    final BiFunction<String,String,Boolean> matcher;
    
    Matching(BiFunction<String,String,Boolean> matcher) {
        this.matcher = matcher;
    }
    
    public boolean matches(String value, String checkThis) {
        return this.matcher.apply(value,checkThis);
    }
}