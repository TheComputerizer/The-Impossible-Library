package mods.thecomputerizer.theimpossiblelibrary.api.util;

import mods.thecomputerizer.theimpossiblelibrary.api.core.annotation.IndirectCallers;
import mods.thecomputerizer.theimpossiblelibrary.api.text.TextHelper;

import java.util.Arrays;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Patterns {

    public static final Pattern ARRAY_DEF = Pattern.compile("(\\[*]*)");
    public static final String BRACKETS_LITERAL = Pattern.quote("{}");
    
    @IndirectCallers
    public static boolean matchesLiterally(String original, String literal) {
        return original.matches(Pattern.quote(literal));
    }
    
    @IndirectCallers
    public static Matcher getMatcher(CharSequence str, String regex) {
        return getMatcher(str,regex,0);
    }

    @SuppressWarnings("MagicConstant")
    public static Matcher getMatcher(CharSequence str, String regex, int patternFlags) {
        return Pattern.compile(regex,patternFlags).matcher(str);
    }
    
    public static boolean isEncapsulatedBy(String str, char c) {
        return TextHelper.isNotEmpty(str) && str.charAt(0)==c && str.charAt(str.length()-1)==c;
    }
    
    @IndirectCallers
    public static boolean isEncapsulatedBy(String str, String cap) {
        return TextHelper.isNotEmpty(str) && str.startsWith(cap) && str.endsWith(cap);
    }
    
    @IndirectCallers
    public static boolean matchesAny(String original, String ... matches) {
        return matchesAny(original,false, Arrays.asList(matches));
    }

    public static boolean matchesAny(String original, Iterable<String> matches) {
        return matchesAny(original,false,matches);
    }
    
    @IndirectCallers
    public static boolean matchesAny(String original, boolean matchCase, String ... matches) {
        return matchesAny(original,matchCase,Arrays.asList(matches));
    }

    public static boolean matchesAny(String original, boolean matchCase, Iterable<String> matches) {
        if(TextHelper.isBlank(original)) return true;
        original = matchCase ? original : original.toLowerCase();
        for(String match : matches) {
            if(TextHelper.isBlank(match)) return true;
            match = matchCase ? match : match.toLowerCase();
            if(original.matches(match)) return true;
        }
        return false;
    }
}