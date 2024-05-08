package mods.thecomputerizer.theimpossiblelibrary.api.util;

import org.apache.commons.lang3.StringUtils;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@SuppressWarnings("unused")
public class Patterns {

    public static final Pattern ARRAY_DEF = Pattern.compile("(\\[*]*)");
    public static final String BRACKETS_LITERAL = Pattern.quote("{}");
    
    public static boolean matchesLiterally(String original, String literal) {
        return original.matches(Pattern.quote(literal));
    }

    public static Matcher getMatcher(CharSequence str, String regex) {
        return getMatcher(str,regex,0);
    }

    public static Matcher getMatcher(CharSequence str, String regex, int patternFlags) {
        return Pattern.compile(regex,patternFlags).matcher(str);
    }
    
    public static boolean isEncapsulatedBy(String str, char c) {
        return StringUtils.isNotEmpty(str) && str.charAt(0)==c && str.charAt(str.length()-1)==c;
    }
    
    public static boolean isEncapsulatedBy(String str, String cap) {
        return StringUtils.isNotEmpty(str) && str.startsWith(cap) && str.endsWith(cap);
    }

    public static boolean matchesAny(String original, String ... matches) {
        return matchesAny(original,false, Arrays.asList(matches));
    }

    public static boolean matchesAny(String original, Iterable<String> matches) {
        return matchesAny(original,false,matches);
    }

    public static boolean matchesAny(String original, boolean matchCase, String ... matches) {
        return matchesAny(original,matchCase,Arrays.asList(matches));
    }

    public static boolean matchesAny(String original, boolean matchCase, Iterable<String> matches) {
        if(StringUtils.isBlank(original)) return true;
        original = matchCase ? original : original.toLowerCase();
        for(String match : matches) {
            if(StringUtils.isBlank(match)) return true;
            match = matchCase ? match : match.toLowerCase();
            if(original.matches(match)) return true;
        }
        return false;
    }
}